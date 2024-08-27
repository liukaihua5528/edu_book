package com.edu.book.infrastructure.repositoryImpl.book;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.book.repository.BookMenuRepository
import com.edu.book.infrastructure.po.book.BookMenuPo
import com.edu.book.infrastructure.repositoryImpl.dao.book.BookMenuDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.stereotype.Repository;

/**
 * 书单表 服务实现类
 * @author 
 * @since 2024-07-02 23:32:18
 */

@Repository
class BookMenuRepositoryImpl : ServiceImpl<BookMenuDao, BookMenuPo>(), BookMenuRepository {

    /**
     * 查询uid
     */
    override fun getByUid(bookMenuUid: String): BookMenuPo? {
        val wrapper = KtQueryWrapper(BookMenuPo::class.java)
            .eq(BookMenuPo::uid, bookMenuUid)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 获取书单列表
     */
    override fun getByGardenUid(gardenUid: String?, kindergartenUid: String?): List<BookMenuPo>? {
        val wrapper = KtQueryWrapper(BookMenuPo::class.java)
            .eq(!gardenUid.isNullOrBlank(), BookMenuPo::gardenUid, gardenUid)
            .eq(!kindergartenUid.isNullOrBlank(), BookMenuPo::kindergartenUid, kindergartenUid)
        return list(wrapper)
    }

    /**
     * 删除
     */
    override fun removeByUid(bookMenuUid: String) {
        val wrapper = KtUpdateWrapper(BookMenuPo::class.java)
            .eq(BookMenuPo::uid, bookMenuUid)
        remove(wrapper)
    }

    /**
     * 更新
     */
    override fun modifyByUid(po: BookMenuPo) {
        val wrapper = KtQueryWrapper(BookMenuPo::class.java)
            .eq(BookMenuPo::uid, po.uid)
        update(po, wrapper)
    }

}