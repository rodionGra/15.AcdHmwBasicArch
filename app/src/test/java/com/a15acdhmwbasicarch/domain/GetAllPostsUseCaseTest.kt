package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.CoroutineTestRule
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class GetAllPostsUseCaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val rule = CoroutineTestRule()

    @Test
    operator fun invoke() {

    }
}