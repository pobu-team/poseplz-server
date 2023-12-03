package com.poseplz.server.infrastructure.naver

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
class NaverApiClient(
    @Qualifier("naverRestTemplate")
    private val naverRestTemplate: RestTemplate,
){
    /**
     * https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
     */
    fun reverseGeoCode(address: String): NaverGeocodeResponse {
        val uri = UriComponentsBuilder.fromHttpUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode")
            .queryParam("query", URLEncoder.encode(address, StandardCharsets.UTF_8))
            .build(true)
            .toUri()
        try {
            val responseEntity = naverRestTemplate.getForEntity(uri, NaverGeocodeResponse::class.java)
            return responseEntity.body!!
        } catch (e: Exception) {
            throw NaverApiException(null, e)
        }
    }
}
