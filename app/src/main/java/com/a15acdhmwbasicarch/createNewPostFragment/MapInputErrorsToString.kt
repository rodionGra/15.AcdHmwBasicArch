package com.a15acdhmwbasicarch.createNewPostFragment

import com.a15acdhmwbasicarch.AndroidResourceRepository
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.domain.NewPostErrorType
import java.lang.StringBuilder
import javax.inject.Inject

class MapInputErrorsToString @Inject constructor(
    private val androidResourceRepository: AndroidResourceRepository
) {

    fun map(listInputErrors: Set<NewPostErrorType>): String {
        val errorString = StringBuilder()
        listInputErrors.forEach {
            errorString.append(
                when (it) {
                    NewPostErrorType.BODY_LENGTH_ERROR -> androidResourceRepository.getString(R.string.body_length_error)
                    NewPostErrorType.TITLE_LENGTH_ERROR -> androidResourceRepository.getString(R.string.title_length_error)
                    NewPostErrorType.FORBIDDEN_WORDS_ERROR -> androidResourceRepository.getString(R.string.forbidden_word)
                    NewPostErrorType.ERROR_SAVE_POSTS -> androidResourceRepository.getString(R.string.save_error)
                }
            ).append("\n")
        }
        return errorString.toString()
    }

}