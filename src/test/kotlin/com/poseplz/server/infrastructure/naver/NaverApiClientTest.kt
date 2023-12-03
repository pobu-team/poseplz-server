package com.poseplz.server.infrastructure.naver

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils

class NaverApiClientTest {
    lateinit var naverApiClient: NaverApiClient

    @BeforeEach
    fun setUp() {
        val naverApiConfig = NaverApiConfig()
        ReflectionTestUtils.setField(
            naverApiConfig,
            "naverApiClientId",
            "u6qn65ajy4"
        )
        ReflectionTestUtils.setField(
            naverApiConfig,
            "naverApiClientSecret",
            "yyOaH00Yp71gSfuaWpu1IZENHsOnxgsxFIGTnO3c"
        )
        naverApiClient = NaverApiClient(
            naverApiConfig.naverRestTemplate()
        )
    }

    @Test
    fun name() {
        val reverseGeoCode = naverApiClient.reverseGeoCode(
            "서울특별시 마포구 와우산로 94"
        )
        println(reverseGeoCode)
    }
}
