package com.a15acdhmwbasicarch.data

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import javax.inject.Inject

class AndroidResourceRepository @Inject constructor(private val context: Context) {
    fun getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(context, colorRes)

    fun getString(@StringRes stringRes: Int) = context.resources.getString(stringRes)

    fun getStringArray(@ArrayRes stringArrRes: Int): Array<String> = context.resources.getStringArray(stringArrRes)
}
