package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.AndroidResourceRepository
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import javax.inject.Inject

const val TITLE_MIN_LENGTH = 2
const val TITLE_MAX_LENGTH = 51
const val BODY_MIN_LENGTH = 4
const val BODY_MAX_LENGTH = 501

class AddNewPostValidationUseCase @Inject constructor(
    private val resource: AndroidResourceRepository,
    private val savePostUseCase: SavePostUseCase
) {
    fun execute(postForSaving: NewPostModel): Set<NewPostErrorType> {
        val setOfError: MutableSet<NewPostErrorType> = mutableSetOf()

        if (postForSaving.title.length !in TITLE_MAX_LENGTH downTo TITLE_MIN_LENGTH) {
            setOfError.add(NewPostErrorType.TITLE_LENGTH_ERROR)
        }

        if (postForSaving.body.length !in BODY_MAX_LENGTH downTo BODY_MIN_LENGTH) {
            setOfError.add(NewPostErrorType.BODY_LENGTH_ERROR)
        }

        if (checkForbiddenWords(postForSaving.title)) {
            setOfError.add(NewPostErrorType.FORBIDDEN_WORDS_ERROR)
        }

        if (setOfError.isEmpty()){
            savePostUseCase.savePost(postForSaving)
        }

        return setOfError
    }

    private fun checkForbiddenWords(title: String): Boolean{
        for (word in resource.getStringArray(R.array.forbidden_words)){
            if (title.contains(word, true)){
                return true
            }
        }
        return false
    }
}