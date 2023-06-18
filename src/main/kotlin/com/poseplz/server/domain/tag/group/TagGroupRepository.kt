package com.poseplz.server.domain.tag.group

import org.springframework.data.jpa.repository.JpaRepository

interface TagGroupRepository : JpaRepository<TagGroup, Long> {
    fun findByPeopleCountsContains(peopleCount: Int): List<TagGroup>
}
