package com.edu.book.api.http.service

import com.alibaba.fastjson.JSON
import com.edu.book.api.vo.wechat.WechatLoginRespVo
import com.edu.book.application.client.OkHttpClientManager
import com.edu.book.application.client.WechatApi
import com.edu.book.application.service.WechatAppService
import com.edu.book.infrastructure.config.SystemConfig
import com.edu.book.infrastructure.enums.ErrorCodeConfig
import com.edu.book.infrastructure.exception.WebAppException
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Auther: liukaihua
 * @Date: 2024/3/11 19:04
 * @Description:
 */

@Service
class WechatWebService {

    private val logger = LoggerFactory.getLogger(WechatWebService::class.java)

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var wechatAppService: WechatAppService

    @Autowired
    private lateinit var wechatApi: WechatApi

    /**
     * 微信登录
     */
    fun wechatLogin(code: String): WechatLoginRespVo {
        val httpResult = wechatApi.wechatLogin(code)
        logger.info("调用微信登录http接口 返回 httpResult:${JSON.toJSONString(httpResult)}")
        if (httpResult == null || ObjectUtils.notEqual(httpResult.errcode, NumberUtils.INTEGER_ZERO)) throw WebAppException(ErrorCodeConfig.WECHAT_LOGIN_FAIL)
        return WechatLoginRespVo().apply {
            this.sessionKey = httpResult.session_key ?: ""
            this.openId = httpResult.openid ?: ""
            this.unionId = httpResult.unionid ?: ""
        }
    }

    /**
     * 获取微信token
     * 先获取缓存，如果缓存获取不到，则进行http获取
     */
    fun getWechatAccessToken(): String {
        val cacheToken = wechatAppService.getWechatAccessToken(systemConfig.wechatAppId)
        val finalAccessToken = if (cacheToken.isNullOrBlank()) {
            val accessToken = wechatApi.getAccessToken(systemConfig.wechatAppId, systemConfig.wechatAppSecret)
            wechatAppService.setWechatAccessTokenCache(systemConfig.wechatAppId, accessToken)
            accessToken
        } else {
            cacheToken
        }
        return finalAccessToken
    }

}