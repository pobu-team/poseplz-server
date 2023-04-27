package com.poseplz.server.domain.tag

enum class TagType(
    val displayName: String,
) {
    /**
     * 몇 명 인지
     */
    NUMBER_OF_PEOPLE("인원"),
    /**
     * 모임 성격
     */
    NATURE_OF_GATHERING("모임 성격"),
    /**
     * 포즈의 분위기
     */
    ATMOSPHERE_OF_POSE("분위기"),
}
