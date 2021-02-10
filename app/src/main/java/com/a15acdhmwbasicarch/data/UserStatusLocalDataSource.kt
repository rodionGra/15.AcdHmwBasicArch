package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.domain.UserStatus

class UserStatusLocalDataSource() {

    private val statusSet : MutableSet<StatusUser> = mutableSetOf()

    private fun setupHardCode() {
        statusSet.add(StatusUser(3, UserStatus.WITH_WARNING))
        statusSet.add(StatusUser(4, UserStatus.WITH_WARNING))
        statusSet.add(StatusUser(7, UserStatus.BANNED))
    }

    fun addStatusUser(statusUser: StatusUser) = statusSet.add(statusUser)

    fun removeStatusUser(statusUser: StatusUser) = statusSet.remove(statusUser)

    fun getSetOfStatusUser(): Set<StatusUser> {
        return statusSet
    }

    init {
        setupHardCode()
    }
}