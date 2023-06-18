package com.poseplz.server.domain.tag.group

import org.springframework.data.jpa.repository.JpaRepository

@Suppress("FunctionName")
interface TagGroupTagRepository : JpaRepository<TagGroupTag, Long> {
    fun findByTagGroup_tagGroupIdAndTag_tagId(tagGroupId: Long, tagId: Long): TagGroupTag?
    fun deleteByTagGroup_tagGroupId(tagGroupId: Long)

}
