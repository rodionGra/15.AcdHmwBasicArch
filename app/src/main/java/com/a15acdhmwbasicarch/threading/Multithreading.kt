package com.a15acdhmwbasicarch.threading

import android.content.Context
import android.os.Handler

/**
Клас щоб виконати операцію на іншій треді, а результат отримати на головну треду
 */
class Multithreading(context: Context) {
    private val mainHandler = Handler(context.mainLooper)

    //функція щоб виконувати операції асинхронно
    fun <T> async(operation: () -> T): AsyncOperation<T> {
        return AsyncOperation(
            operation = operation,
            mainHandler = mainHandler,
            threadCreation = {Thread(it).apply(Thread::start)}
        )
    }
    //todo clean up
    //создає нову треду
    private fun createTread(runnable: Runnable): Thread {
        return Thread(runnable).apply(Thread::start)
    }
}