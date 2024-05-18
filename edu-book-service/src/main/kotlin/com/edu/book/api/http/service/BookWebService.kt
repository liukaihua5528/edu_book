package com.edu.book.api.http.service

import com.edu.book.api.vo.book.BookAgeVo
import com.edu.book.api.vo.book.BookClassifyVo
import com.edu.book.api.vo.book.BookDetailVo
import com.edu.book.api.vo.book.BorrowBookVo
import com.edu.book.api.vo.book.PageQueryBookResultVo
import com.edu.book.api.vo.book.PageQueryBookVo
import com.edu.book.api.vo.book.ScanBookCodeInStorageVo
import com.edu.book.api.vo.book.ScanIsbnCodeBookVo
import com.edu.book.application.service.BookAppService
import com.edu.book.domain.book.dto.BorrowBookDto
import com.edu.book.domain.book.dto.PageQueryBookDto
import com.edu.book.domain.book.dto.ScanBookCodeInStorageDto
import com.edu.book.domain.book.enums.AgeGroupEnum
import com.edu.book.domain.book.enums.BookClassifyEnum
import com.edu.book.infrastructure.util.MapperUtil
import com.edu.book.infrastructure.util.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 10:29
 * @Description:
 */

@Service
class BookWebService {

    @Autowired
    private lateinit var bookAppService: BookAppService

    /**
     * 借书
     */
    fun borrowBook(vo: BorrowBookVo) {
        val dto = MapperUtil.map(BorrowBookDto::class.java, vo)
        bookAppService.borrowBook(dto)
    }

    /**
     * 查询年龄段
     */
    fun queryBookAgeGroup(): List<BookAgeVo> {
        return AgeGroupEnum.values().toList().map {
            BookAgeVo().apply {
                this.ageGroupCode = it.age
                this.ageGroupName = it.desc
            }
        }
    }

    /**
     * 查询分类
     */
    fun queryBookClassifyByGarden(): List<BookClassifyVo> {
        return BookClassifyEnum.values().toList().map {
            BookClassifyVo().apply {
                this.classifyName = it.desc
                this.classifyCode = it.code
            }
        }
    }

    /**
     * 图书扫码入库
     */
    fun scanBookCodeInStorage(vo: ScanBookCodeInStorageVo) {
        val dto = MapperUtil.map(ScanBookCodeInStorageDto::class.java, vo)
        bookAppService.scanBookCodeInStorage(dto)
    }

    /**
     * 查询图书详情
     */
    fun findBookDetail(bookUid: String): BookDetailVo {
        val dto = bookAppService.findBookDetail(bookUid)
        return MapperUtil.map(BookDetailVo::class.java, dto)
    }

    /**
     * 删除图书
     */
    fun deleteBookDetail(bookUid: String) {
        bookAppService.deleteBookDetail(bookUid)
    }

    /**
     * 扫码
     */
    fun scanIsbnCode(isbnCode: String): ScanIsbnCodeBookVo {
        val dto = bookAppService.scanIsbnCode(isbnCode)
        return MapperUtil.map(ScanIsbnCodeBookVo::class.java, dto).apply {
            this.`class` = dto.`class`
        }
    }

    /**
     * 分页查询
     */
    fun pageQueryBooks(vo: PageQueryBookVo): Page<PageQueryBookResultVo> {
        val paramDto = MapperUtil.map(PageQueryBookDto::class.java, vo)
        val pageResult = bookAppService.pageQueryBooks(paramDto)
        if (pageResult.result.isNullOrEmpty()) return Page()
        return Page(vo.page, vo.pageSize, pageResult.totalCount, MapperUtil.mapToList(PageQueryBookResultVo::class.java, pageResult.result!!))
    }

    /**
     * 查询isbn列表
     */
    fun getIsbnList(garden: String?, isbn: String?): List<String> {
        return bookAppService.getIsbnList(garden, isbn)
    }

}