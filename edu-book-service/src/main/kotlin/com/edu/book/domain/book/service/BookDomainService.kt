package com.edu.book.domain.book.service

import com.alibaba.fastjson.JSON
import com.edu.book.domain.book.dto.BookDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageDto
import com.edu.book.domain.book.exception.BookDetailAlreadyExistException
import com.edu.book.domain.book.mapper.BookEntityMapper.buildBookDetailPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildScanBookCodeInsertBookPo
import com.edu.book.domain.book.mapper.BookEntityMapper.buildScanBookCodeUpdateBookPo
import com.edu.book.domain.book.repository.BookDetailRepository
import com.edu.book.domain.book.repository.BookRepository
import com.edu.book.domain.book.repository.BookSellRepository
import com.edu.book.domain.user.exception.ConcurrentCreateInteractRoomException
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.constants.RedisKeyConstant.SCAN_BOOK_CODE_KEY
import com.edu.book.infrastructure.po.book.BookPo
import com.edu.book.infrastructure.po.book.BookSellPo
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.UUIDUtil
import java.util.concurrent.TimeUnit
import javax.annotation.Resource
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/24 23:21
 * @Description:
 */

@Service
class BookDomainService {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var bookSellRepository: BookSellRepository

    @Autowired
    private lateinit var bookDetailRepository: BookDetailRepository

    @Resource
    private lateinit var redissonClient: RedissonClient

    @Autowired
    private lateinit var systemConfig: SystemConfig

    /**
     * 根据isbn查询书
     */
    fun findBookByIsbnCode(isbnCode: String): BookDto? {
        val po = bookRepository.findByIsbnCode(isbnCode) ?: return null
        return MapperUtil.map(BookDto::class.java, po)
    }

    /**
     * 图书扫码入库
     * 1.判断isbn是否正确
     * 2.新增书籍
     * 3.修改图书基础信息
     */
    fun scanBookCodeInStorage(dto: ScanBookCodeInStorageDto) {
        val lockKey = SCAN_BOOK_CODE_KEY + dto.bookUid
        val lock = redissonClient.getLock(lockKey)
        try {
            if (!lock.tryLock(systemConfig.distributedLockWaitTime, systemConfig.distributedLockReleaseTime, TimeUnit.MILLISECONDS)) {
                throw ConcurrentCreateInteractRoomException(dto.bookUid)
            }
            //查询图书是否已经存在
            val currentBookDetailPo = bookDetailRepository.findByBookUid(dto.bookUid)
            if (currentBookDetailPo != null) throw BookDetailAlreadyExistException()
            //根据isbn查询图书信息
            val bookPo = bookRepository.findByIsbnCode(dto.isbnCode)
            if (bookPo != null) {
                //修改属性
                val updateBookPo = buildScanBookCodeUpdateBookPo(dto, bookPo)
                bookRepository.updateByUid(updateBookPo)
            } else {
                //新增po
                val insertBookPo = buildScanBookCodeInsertBookPo(dto)
                bookRepository.save(insertBookPo)
            }
            //新增图书详情
            val bookDetailPo = buildBookDetailPo(dto)
            bookDetailRepository.save(bookDetailPo)
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    /**
     * 插入数据
     */
    fun saveIsbnBookInfo(dto: BookDto) {
        val bookPo = MapperUtil.map(BookPo::class.java, dto).apply {
            this.uid = UUIDUtil.createUUID()
            this.summary = JSON.toJSONString(dto.summary)
        }
        bookRepository.save(bookPo)
        val bookSellList = MapperUtil.mapToList(BookSellPo::class.java, dto.sellList).map {
            it.uid = UUIDUtil.createUUID()
            it.isbnCode = dto.isbnCode
            it
        }
        bookSellRepository.saveBatch(bookSellList)
    }

}