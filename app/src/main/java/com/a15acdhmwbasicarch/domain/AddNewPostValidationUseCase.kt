package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import javax.inject.Inject

class AddNewPostValidationUseCase @Inject constructor(
    private val postsInfoRepository: PostsInfoRepository,
    private val newPostValidation: NewPostVerification
) {

    suspend operator fun invoke(postForSaving: NewPostModel): VerificationStatus<Set<NewPostErrorType>> {
        val verificationStatus = newPostValidation.verify(postForSaving)

        if (verificationStatus == VerificationStatus.Normal) {
            postsInfoRepository.saveNewPostFromUser(postForSaving)
        }

        return verificationStatus
    }
}
