package com.poseplz.server.domain.tag

import com.poseplz.server.domain.pose.PoseTagRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface TagService {
    fun create(tagCreateVo: TagCreateVo): Tag
    fun update(tagId: Long, tagUpdateVo: TagUpdateVo): Tag
    fun findAll(pageable: Pageable): Page<Tag>
    fun findByTagType(tagType: TagType, pageable: Pageable): Page<Tag>
    fun findByTagIds(tagIds: List<Long>): List<Tag>
    fun getById(tagId: Long): Tag
    fun merge(sourceTagId: Long, targetTagId: Long): Tag
}

@Service
@Transactional(readOnly = true)
class TagServiceImpl(
    private val tagRepository: TagRepository,
    private val poseTagRepository: PoseTagRepository,
) : TagService {
    @Transactional
    override fun create(tagCreateVo: TagCreateVo): Tag {
        return tagRepository.save(Tag.from(tagCreateVo))
    }

    @Transactional
    override fun update(tagId: Long, tagUpdateVo: TagUpdateVo): Tag {
        val tag = tagRepository.findByIdOrNull(tagId)
            ?: throw TagNotFoundException("태그가 존재하지 않습니다. tagId: $tagId")
        tag.update(tagUpdateVo)
        return tag
    }

    override fun findAll(pageable: Pageable): Page<Tag> {
        return tagRepository.findAll(pageable)
    }

    override fun findByTagType(tagType: TagType, pageable: Pageable): Page<Tag> {
        return tagRepository.findByType(tagType, pageable)
    }

    override fun findByTagIds(tagIds: List<Long>): List<Tag> {
        return tagRepository.findByTagIdIn(tagIds)
    }

    override fun getById(tagId: Long): Tag {
        return tagRepository.findByIdOrNull(tagId)
            ?: throw TagNotFoundException("태그가 존재하지 않습니다. tagId: $tagId")
    }

    /**
     * sourceTag 를 targetTag 로 합침.
     */
    @Transactional
    override fun merge(sourceTagId: Long, targetTagId: Long): Tag {
        val sourceTag = tagRepository.findByIdOrNull(sourceTagId) ?: throw TagNotFoundException()
        val targetTag = tagRepository.findByIdOrNull(targetTagId) ?: return sourceTag
        // source, target 모두 가진 매핑 조회
        val poseIdPoseTagMap = poseTagRepository.findByTag(targetTag)
            .filter { it.tag == sourceTag || it.tag == targetTag }
            .groupBy { it.pose.poseId }
        // 둘 다 가진 poseTag 에 대해서 targetTag 삭제
        poseIdPoseTagMap.filter { it.value.size > 1 }
            .forEach { (poseId, poseTags) ->
                run {
                    poseTags.filter { it.tag == targetTag }
                        .forEach { poseTagRepository.delete(it) }
                }
            }
        // targetTag 만 가진 poseTag 를 sourceTag 로 업데이트
        poseIdPoseTagMap.filter { it.value.size == 1 }
            .forEach { it.value.first().updateTag(sourceTag) }
        // targetTag 삭제
        tagRepository.delete(targetTag)
        return sourceTag
    }
}
