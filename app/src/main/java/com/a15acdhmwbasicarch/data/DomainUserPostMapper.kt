package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.*
import com.a15acdhmwbasicarch.domain.UserPostDomainModel
import com.a15acdhmwbasicarch.domain.UserStatus

/*class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (posts[position]) {
            is PostUiModel.StandardPostUiModel -> 234
            is PostUiModel.BannedUserPostUiModel -> 7686
        }
    }
}*/

class DomainUserPostMapper {
    fun map(postResponse: Result<List<UserPostResponse>, UserPostError>, statusList: Set<StatusUser>): Result<List<UserPostDomainModel>, UserPostError> {
        return postResponse.mapSuccess { listOfUserPostResponse ->
            listOfUserPostResponse.map { userPostResponse ->
                if (statusList.any { it.idUser == userPostResponse.userId }) {
                    val status = statusList.findLast { it.idUser == userPostResponse.userId }!!.status
                    UserPostDomainModel(
                            userId = userPostResponse.userId,
                            id = userPostResponse.id,
                            title = userPostResponse.title,
                            body = userPostResponse.body,
                            status = status
                    )
                } else {
                    UserPostDomainModel(
                            userId = userPostResponse.userId,
                            id = userPostResponse.id,
                            title = userPostResponse.title,
                            body = userPostResponse.body,
                            status = UserStatus.STANDARD
                    )
                }
            }
        }
    }
}