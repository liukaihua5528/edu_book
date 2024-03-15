package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookUserRepository
import com.edu.book.infrastructure.po.user.BookUserPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookUserDao
import com.edu.book.infrastructure.util.limitOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository;

/**
 * 用户表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookUserRepositoryImpl : ServiceImpl<BookUserDao, BookUserPo>(), BookUserRepository {

    @Autowired
    private lateinit var bookUserDao: BookUserDao

    /**
     * 获取用户
     */
    override fun findUserByOpenId(openId: String): BookUserPo? {
        val wrapper = KtQueryWrapper(BookUserPo::class.java)
            .eq(BookUserPo::wechatUid, openId)
            .limitOne()
        return getOne(wrapper)
    }

    /**
     * 修改用户信息
     */
    override fun updateUserPoByUid(po: BookUserPo) {
        val updateWrapper = KtUpdateWrapper(BookUserPo::class.java)
            .eq(BookUserPo::uid, po.uid)
        update(po, updateWrapper)
    }

}