package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.api.PostsReposApi
import com.a15acdhmwbasicarch.datasource.db.PostsDao
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.IOException

@ExperimentalCoroutinesApi
internal class PostsInfoRepositoryTest() {

    @Test
    fun `success update local storage`() {
        val userId = 1
        val postId = 1
        val title = "title"
        val body = "body"
        val addedFrom = AddedFrom.SERVER

        val listFromApi = listOf(
            UserPostResponse(userId = userId, id = postId, title = title, body = body)
        )

        val listFromMap = listOf(
            UserPostData(
                userId = userId,
                id = postId,
                title = title,
                body = body,
                addedFrom = addedFrom
            )
        )

        val mockKInfoApiService = mockk<PostsReposApi> {
            coEvery { getPostsList() } returns listFromApi
        }
        val mockKPostsCacheDataSource = mockk<PostsDao>(relaxUnitFun = true)
        val mockKPostResponseToPostDbEntityMapper = mockk<PostResponseToPostDbEntityMapper> {
            coEvery { map(any()) } returns listFromMap
        }
        val mockKDomainUserPostMapper = mockk<DomainUserPostMapper>()
        val mockKNewPostToDataPostMapper = mockk<NewPostToDataPostMapper>()

        val postsInfoRepository = PostsInfoRepository(
            mockKInfoApiService,
            mockKPostsCacheDataSource,
            mockKPostResponseToPostDbEntityMapper,
            mockKDomainUserPostMapper,
            mockKNewPostToDataPostMapper,
            TestCoroutineDispatcher()
        )

        runBlockingTest {
            assertEquals(Unit, postsInfoRepository.updateLocalStorage())
        }

        coVerify {
            mockKPostResponseToPostDbEntityMapper.map(listFromApi)
        }

        coVerify {
            mockKPostsCacheDataSource.insertListPosts(listFromMap)
        }

        confirmVerified(mockKPostsCacheDataSource)
    }


    @Test
    fun `local storage should not be update after fail response from api `() {

        val mockKInfoApiService = mockk<PostsReposApi> {
            coEvery { getPostsList() } throws IOException()
        }
        val mockKPostsCacheDataSource = mockk<PostsDao>(relaxUnitFun = true)
        val mockKPostResponseToPostDbEntityMapper = mockk<PostResponseToPostDbEntityMapper>()
        val mockKDomainUserPostMapper = mockk<DomainUserPostMapper>()
        val mockKNewPostToDataPostMapper = mockk<NewPostToDataPostMapper>()

        val postsInfoRepository = PostsInfoRepository(
            mockKInfoApiService,
            mockKPostsCacheDataSource,
            mockKPostResponseToPostDbEntityMapper,
            mockKDomainUserPostMapper,
            mockKNewPostToDataPostMapper,
            TestCoroutineDispatcher()
        )


        assertThrows(
            IOException::class.java
        ) {
            runBlockingTest {
                postsInfoRepository.updateLocalStorage()
            }
        }

        coVerify(exactly = 0) {
            mockKPostResponseToPostDbEntityMapper.map(any())
        }

        coVerify(exactly = 0) {
            mockKPostsCacheDataSource.insertListPosts(any())
        }

    }

    @Test
    fun `success save new post to dataBase`() {
        val newPostForSave = NewPostModel("title", "body")
        val newPostForSaveDbModel = UserPostData(2, 2, "title", "body", AddedFrom.USER)

        val mockKInfoApiService = mockk<PostsReposApi>()
        val mockKPostsCacheDataSource = mockk<PostsDao> {
            coEvery { insertPost(any()) } returns Unit
            coEvery { getMaxPostId() } returns 1
        }
        val mockKPostResponseToPostDbEntityMapper = mockk<PostResponseToPostDbEntityMapper>()
        val mockKDomainUserPostMapper = mockk<DomainUserPostMapper>()
        val mockKNewPostToDataPostMapper = mockk<NewPostToDataPostMapper>{
            coEvery { map(newPostForSave, 2) } returns newPostForSaveDbModel
        }

        val postsInfoRepository = PostsInfoRepository(
            mockKInfoApiService,
            mockKPostsCacheDataSource,
            mockKPostResponseToPostDbEntityMapper,
            mockKDomainUserPostMapper,
            mockKNewPostToDataPostMapper,
            TestCoroutineDispatcher()
        )

        runBlockingTest {
            assertEquals(Unit, postsInfoRepository.saveNewPostFromUser(newPostForSave))
        }

        coVerify() {
            mockKPostsCacheDataSource.insertPost(any())
        }
    }
}