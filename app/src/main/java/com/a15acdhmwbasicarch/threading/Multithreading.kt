package com.a15acdhmwbasicarch.threading

import android.content.Context
import android.os.Handler

class Multithreading(context: Context) {
    private val mainHandler = Handler(context.mainLooper)

    fun <T> async(operation: () -> T): AsyncOperation<T> {
        return AsyncOperation(
            operation = operation,
            mainHandler = mainHandler,
            threadCreation = ::createTread
        )
    }

    private fun createTread(runnable: Runnable): Thread {
        return Thread(runnable).apply(Thread::start)
    }
}