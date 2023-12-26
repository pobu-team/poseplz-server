package com.poseplz.server.infrastructure.kakao

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class KakaoAdminApiClient(
    @Qualifier("kakaoAdminRestTemplate")
    private val kakaoAdminRestTemplate: RestTemplate,
) {
    fun getUserInfo(
        kakaoUserId: String,
    ): KakaoUserMeResponse {

        return try {
            val responseEntity = kakaoAdminRestTemplate.getForEntity(
                UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me")
                    .queryParam("target_id_type", "user_id")
                    .queryParam("target_id", kakaoUserId)
                    .build()
                    .toUri(),
                KakaoUserMeResponse::class.java,
            )
            responseEntity.body!!
        } catch (e: RestClientException) {
            throw KakaoApiFailedException("카카오 사용자 정보 가져오기 API 호출에 실패했습니다.", e)
        }
    }
}
