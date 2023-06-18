package com.poseplz.server.application.tag.group

import com.poseplz.server.application.tag.toTagResponse
import com.poseplz.server.domain.tag.group.TagGroup
import com.poseplz.server.ui.api.tag.group.TagGroupDetailResponse
import com.poseplz.server.ui.api.tag.group.TagGroupResponse

fun TagGroup.toTagGroupResponse() = TagGroupResponse(
    tagGroupId = tagGroupId.toString(),
    name = name,
    peopleCounts = peopleCounts,
)

fun TagGroup.toTagGroupDetailResponse() = TagGroupDetailResponse(
    tagGroupId = tagGroupId.toString(),
    name = name,
    peopleCounts = peopleCounts,
    tags = tagGroupTags.map { it.tag.toTagResponse() },
)
