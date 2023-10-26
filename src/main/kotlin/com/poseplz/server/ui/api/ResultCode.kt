package com.poseplz.server.ui.api

enum class ResultCode(
    val defaultMessage: String,
) {
    SUCCESS("성공"),
    BAD_REQUEST("잘못된 요청입니다."),
    MAX_UPLOAD_SIZE_EXCEEDED("업로드 가능한 최대 크기를 초과했습니다."),
    UNAUTHORIZED("인증이 필요한 요청입니다."),
    FORBIDDEN("권한이 없는 요청입니다."),
    NOT_FOUND("요청한 리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
}
