package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;
import java.util.Date;

/**
 * <p>
 * 图书借阅流水表
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
@TableName("t_book_borrow_flow")
class BookBorrowFlowPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * isbn
     */
    @TableField("c_isbn_code")
    var isbnCode: String? = null

    /**
     * 图书自编码
     */
    @TableField("c_book_uid")
    var bookUid: String? = null

    /**
     * 借阅人
     */
    @TableField("c_borrow_user_uid")
    var borrowUserUid: String? = null

    /**
     * 借阅时间
     */
    @TableField("c_borrow_time")
    var borrowTime: Date? = null

}