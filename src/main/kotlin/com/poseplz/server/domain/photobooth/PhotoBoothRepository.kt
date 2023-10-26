package com.poseplz.server.domain.photobooth

import org.springframework.data.jpa.repository.JpaRepository

interface PhotoBoothRepository : JpaRepository<PhotoBooth, Long> {
}
