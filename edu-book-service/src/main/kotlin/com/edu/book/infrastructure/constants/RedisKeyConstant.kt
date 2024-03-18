package com.edu.book.infrastructure.constants

object RedisKeyConstant {

    /**
     * 微信accesstoken
     */
    const val WECHAT_ACCESS_TOKEN_KEY = "WECHAT:ACCESS:TOKEN:"

    /**
     * 注册用户所
     */
    const val REGISTER_USER_LOCK_KEY = "REGISTER:USER:LOCK:"

    /**
     * token
     */
    const val USER_TOKEN_KEY = "USER:TOKEN:"

    /**
     * 绑定解绑
     */
    const val BIND_UNBIND_USER_ACCOUNT_LOCK_KEY = "BIND:UNBIND:USER:ACCOUNT:LOCK:"

}