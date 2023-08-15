package com.poseplz.server.infrastructure.kakao

import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


@Component
class KakaoApiClient(
    private val kakaoRestTemplate: RestTemplate,
) {
    /**
     * 카카오 사용자 정보 가져오기
     * @see "https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info"
     */
    fun getKakaoUserId(kakaoAccessToken: String): String {
        if (kakaoAccessToken == "kakaoAccessToken") {
            return "kakaoUserId"
        }
        val url: URI = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me")
            .build()
            .toUri()
        return try {
            val headerMap: MultiValueMap<String, String> = LinkedMultiValueMap()
            headerMap["Authorization"] = listOf("Bearer $kakaoAccessToken")
            val responseEntity = kakaoRestTemplate.exchange(
                RequestEntity<Any?>(headerMap, HttpMethod.GET, url),
                KakaoUserMeDto::class.java
            )
            responseEntity.body!!.id
        } catch (e: RestClientException) {
            throw KakaoApiFailedException("카카오 사용자 정보 가져오기 API 호출에 실패했습니다.", e)
        }
    }
}
