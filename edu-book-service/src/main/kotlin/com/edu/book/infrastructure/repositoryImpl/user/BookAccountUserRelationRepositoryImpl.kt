package com.edu.book.infrastructure.repositoryImpl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.book.domain.user.repository.BookAccountUserRelationRepository
import com.edu.book.infrastructure.po.user.BookAccountUserRelationPo
import com.edu.book.infrastructure.repositoryImpl.dao.user.BookAccountUserRelationDao
import org.springframework.stereotype.Repository;

/**
 * 账号用户关联表 服务实现类
 * @author 
 * @since 2024-03-13 22:23:55
 */

@Repository
class BookAccountUserRelationRepositoryImpl : ServiceImpl<BookAccountUserRelationDao, BookAccountUserRelationPo>(), BookAccountUserRelationRepository {

}
