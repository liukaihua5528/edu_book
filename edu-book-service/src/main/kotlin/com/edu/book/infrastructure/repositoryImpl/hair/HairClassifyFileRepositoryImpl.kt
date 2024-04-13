package com.edu.book.infrastructure.repositoryImpl.hair;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.hair.repository.HairClassifyFileRepository
import com.edu.book.infrastructure.po.hair.HairClassifyFilePo
import com.edu.book.infrastructure.repositoryImpl.dao.hair.HairClassifyFileDao
import org.springframework.stereotype.Repository;

/**
 * 美发分类文件表 服务实现类
 * @author 
 * @since 2024-04-13 16:28:10
 */

@Repository
class HairClassifyFileRepositoryImpl : ServiceImpl<HairClassifyFileDao, HairClassifyFilePo>(), HairClassifyFileRepository {

    /**
     * 删除
     */
    override fun deleteByClassifyUid(classifyUid: String) {
        val wrapper = KtUpdateWrapper(HairClassifyFilePo::class.java)
            .eq(HairClassifyFilePo::classifyUid, classifyUid)
        remove(wrapper)
    }

    /**
     * 查询
     */
    override fun getByClassifyUid(classifyUid: String): List<HairClassifyFilePo> {
        val wrapper = KtQueryWrapper(HairClassifyFilePo::class.java)
            .eq(HairClassifyFilePo::classifyUid, classifyUid)
        return list(wrapper)
    }

}
