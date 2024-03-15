package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookRolePermissionRelationRepository
import com.edu.book.infrastructure.po.user.BookRolePermissionRelationPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookRolePermissionRelationDao
import org.springframework.stereotype.Repository;

/**
 * 角色权限关联表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookRolePermissionRelationRepositoryImpl : ServiceImpl<BookRolePermissionRelationDao, BookRolePermissionRelationPo>(), BookRolePermissionRelationRepository {

    /**
     * 获取权限列表
     */
    override fun findListByRoleUid(roleUid: String?): List<BookRolePermissionRelationPo> {
        if (roleUid.isNullOrBlank()) return emptyList()
        val wrapper = KtQueryWrapper(BookRolePermissionRelationPo::class.java)
            .eq(BookRolePermissionRelationPo::roleUid, roleUid)
        return list(wrapper) ?: emptyList()
    }

}