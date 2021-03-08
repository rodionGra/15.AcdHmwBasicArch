package com.a15acdhmwbasicarch.presentation.createNewPostFragment

import com.a15acdhmwbasicarch.data.AndroidResourceRepository
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.di.IoDispatcher
import com.a15acdhmwbasicarch.domain.NewPostErrorType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.StringBuilder
import javax.inject.Inject

class MapInputErrorsToString @Inject constructor(
    private val androidResourceRepository: AndroidResourceRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(listInputErrors: Set<NewPostErrorType>): String {
        return withContext(ioDispatcher) {
            val errorString = StringBuilder()
            listInputErrors.forEach {
                errorString.append(
                    when (it) {
                        NewPostErrorType.BODY_LENGTH_MIN_ERROR -> androidResourceRepository.getString(
                            R.string.body_length_min_error
                        )
                        NewPostErrorType.BODY_LENGTH_MAX_ERROR -> androidResourceRepository.getString(
                            R.string.body_length_max_error
                        )
                        NewPostErrorType.TITLE_LENGTH_MIN_ERROR -> androidResourceRepository.getString(
                            R.string.title_length_min_error
                        )
                        NewPostErrorType.TITLE_LENGTH_MAX_ERROR -> androidResourceRepository.getString(
                            R.string.title_length_max_error
                        )
                        NewPostErrorType.FORBIDDEN_WORDS_ERROR -> androidResourceRepository.getString(
                            R.string.forbidden_word
                        )
                    }
                ).append("\n")
            }
            errorString.toString()
        }
    }

}