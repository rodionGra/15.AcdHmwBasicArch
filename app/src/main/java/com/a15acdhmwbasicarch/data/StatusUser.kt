package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.domain.UserStatus

data class StatusUser(
        val idUser: Int,
        // 0 - normal, 1 - warning, 2 - blocked
        val status: UserStatus
)