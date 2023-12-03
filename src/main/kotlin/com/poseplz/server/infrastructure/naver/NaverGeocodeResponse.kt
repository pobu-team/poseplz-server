package com.poseplz.server.infrastructure.naver

// {status=OK, meta={totalCount=1, page=1, count=1}, addresses=[{roadAddress=서울특별시 마포구 와우산로 94 홍익대학교, jibunAddress=서울특별시 마포구 상수동 72-1 홍익대학교, englishAddress=94, Wausan-ro, Mapo-gu, Seoul, Republic of Korea, addressElements=[{types=[SIDO], longName=서울특별시, shortName=서울특별시, code=}, {types=[SIGUGUN], longName=마포구, shortName=마포구, code=}, {types=[DONGMYUN], longName=상수동, shortName=상수동, code=}, {types=[RI], longName=, shortName=, code=}, {types=[ROAD_NAME], longName=와우산로, shortName=와우산로, code=}, {types=[BUILDING_NUMBER], longName=94, shortName=94, code=}, {types=[BUILDING_NAME], longName=홍익대학교, shortName=홍익대학교, code=}, {types=[LAND_NUMBER], longName=72-1, shortName=72-1, code=}, {types=[POSTAL_CODE], longName=04066, shortName=04066, code=}], x=126.9255396, y=37.5512242, distance=0.0}], errorMessage=}
data class NaverGeocodeResponse(
    val status: String,
    val meta: Map<String, Any>,
    val addresses: List<NaverGeocodeAddress>,
    val errorMessage: String,
)

// addresses=[{roadAddress=서울특별시 마포구 와우산로 94 홍익대학교, jibunAddress=서울특별시 마포구 상수동 72-1 홍익대학교, englishAddress=94, Wausan-ro, Mapo-gu, Seoul, Republic of Korea, addressElements=[{types=[SIDO], longName=서울특별시, shortName=서울특별시, code=}, {types=[SIGUGUN], longName=마포구, shortName=마포구, code=}, {types=[DONGMYUN], longName=상수동, shortName=상수동, code=}, {types=[RI], longName=, shortName=, code=}, {types=[ROAD_NAME], longName=와우산로, shortName=와우산로, code=}, {types=[BUILDING_NUMBER], longName=94, shortName=94, code=}, {types=[BUILDING_NAME], longName=홍익대학교, shortName=홍익대학교, code=}, {types=[LAND_NUMBER], longName=72-1, shortName=72-1, code=}, {types=[POSTAL_CODE], longName=04066, shortName=04066, code=}], x=126.9255396, y=37.5512242, distance=0.0}], errorMessage=}
data class NaverGeocodeAddress(
    val roadAddress: String,
    val jibunAddress: String,
    val englishAddress: String,
    val addressElements: List<Map<String, Any>>,
    val x: Double,
    val y: Double,
)
