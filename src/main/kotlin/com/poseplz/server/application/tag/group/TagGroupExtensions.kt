package com.poseplz.server.application.tag.group

import com.poseplz.server.domain.tag.group.TagGroup
import com.poseplz.server.ui.api.tag.group.TagGroupResponse

fun TagGroup.toTagGroupResponse() = TagGroupResponse(
    tagGroupId = tagGroupId.toString(),
    name = name,
)
