package com.edu.book.domain.hair.service

import com.edu.book.domain.hair.dto.SaveHairClassifyDto
import com.edu.book.domain.hair.repository.HairClassifyFileRepository
import com.edu.book.domain.hair.repository.HairClassifyRepository
import com.edu.book.infrastructure.po.hair.HairClassifyFilePo
import com.edu.book.infrastructure.po.hair.HairClassifyPo
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.QiNiuUtil
import com.edu.book.infrastructure.util.UUIDUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Auther: liukaihua
 * @Date: 2024/4/13 16:50
 * @Description:
 */

@Service
class HairDomainService {

    private val logger = LoggerFactory.getLogger(HairDomainService::class.java)

    @Autowired
    private lateinit var hairClassifyFileRepository: HairClassifyFileRepository

    @Autowired
    private lateinit var hairClassifyRepository: HairClassifyRepository

    @Autowired
    private lateinit var qiNiuUtil: QiNiuUtil

    /**
     * 删除分类
     * 1.删除分类
     * 2.删除分类图片
     * 3.删除七牛
     */
    @Transactional(rollbackFor = [Exception::class])
    fun deleteClassify(classifyUid: String) {
        hairClassifyRepository.queryByUid(classifyUid) ?: return
        hairClassifyRepository.deleteByUid(classifyUid)
        //获取所有fileKey
        val filePos = hairClassifyFileRepository.getByClassifyUid(classifyUid)
        val fileKeys = filePos.mapNotNull { it.fileKey }
        hairClassifyFileRepository.deleteByClassifyUid(classifyUid)
        CoroutineScope(Dispatchers.IO).launch {
            //删除七牛
            qiNiuUtil.delete(fileKeys)
        }
    }

    /**
     * 添加分类
     */
    @Transactional(rollbackFor = [Exception::class])
    fun saveHairClassify(dto: SaveHairClassifyDto): String {
        //添加分类表
        val classifyUid = UUIDUtil.createUUID()
        val hairClassifyPo = MapperUtil.map(HairClassifyPo::class.java, dto).apply {
            this.uid = classifyUid
        }
        hairClassifyRepository.save(hairClassifyPo)
        //添加分类文件
        val filePos = dto.files.map {
            MapperUtil.map(HairClassifyFilePo::class.java, it).apply {
                this.uid = UUIDUtil.createUUID()
                this.classifyUid = classifyUid
            }
        }
        hairClassifyFileRepository.saveBatch(filePos)
        return classifyUid
    }

}