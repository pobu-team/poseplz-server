package com.poseplz.server.domain.pose

import org.springframework.data.jpa.repository.JpaRepository

interface PoseRepository : JpaRepository<Pose, Long> {
}
