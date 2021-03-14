package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import javax.inject.Inject

class NewPostToDataPostMapper @Inject constructor() {

    fun map(postForSaving: NewPostModel, id: Int): UserPostData {
        return UserPostData(1, id, postForSaving.title, postForSaving.body, AddedFrom.USER)
    }

}
