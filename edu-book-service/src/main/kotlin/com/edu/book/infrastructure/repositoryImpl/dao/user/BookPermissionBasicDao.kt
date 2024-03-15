package com.edu.book.infrastructure.repositoryImpl.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.book.infrastructure.po.user.BookPermissionBasicPo
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 权限基础表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-13 22:23:55
 */
@Mapper
@Repository
interface BookPermissionBasicDao : BaseMapper<BookPermissionBasicPo> {

}