package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.domain.UserStatus

class UserStatusLocalDataSource {

    //todo
    /*init {
        setupHard()
    }
    */
    private val statusSet = mutableSetOf<StatusUser>(StatusUser(3, UserStatus.WITH_WARNING))

    /*private fun setupHard() {
        statusSet.add(StatusUser(3, UserStatus.WITH_WARNING))
        statusSet.add(StatusUser(4, UserStatus.WITH_WARNING))
        statusSet.add(StatusUser(7, UserStatus.BANNED))
    }
*/
    //fun addStatusUser(statusUser: StatusUser) = statusSet.add(statusUser)

    fun getSetOfStatusUser(): Set<StatusUser> {
        return statusSet
    }
}