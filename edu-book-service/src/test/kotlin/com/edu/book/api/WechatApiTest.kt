package com.edu.book.api

import com.alibaba.fastjson.JSON
import com.edu.book.EduBoolServiceApplication
import com.edu.book.application.client.WechatApi
import com.edu.book.domain.user.service.UserDomainService
import com.edu.book.infrastructure.config.SystemConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @Auther: liukaihua
 * @Date: 2024/3/13 10:11
 * @Description:
 */

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [EduBoolServiceApplication::class])
class WechatApiTest {

    @Autowired
    private lateinit var wechatApi: WechatApi

    @Autowired
    private lateinit var systemConfig: SystemConfig

    @Autowired
    private lateinit var userDomainService: UserDomainService

    @Test
    fun `注册`() {
        val result = userDomainService.registerUser("0f3eR6100GlyIR15Pk200IDoN72eR61v")
        println(JSON.toJSONString(result))
    }

    @Test
    fun `获取accessToken`() {
        val token = wechatApi.getAccessToken(systemConfig.wechatAppId, systemConfig.wechatAppSecret)
        println(token)
        println(wechatApi.getPhone(token, "adwqeqwe123we"))
    }

}