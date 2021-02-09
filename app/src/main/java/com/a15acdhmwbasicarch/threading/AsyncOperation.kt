package com.a15acdhmwbasicarch.threading

import android.os.Handler
import java.lang.Exception

fun interface CancelableOperation {
    fun cancel()
}

/** Класс для виконання задач асинхронно*/
class AsyncOperation<T>(
    private val operation: () -> T,     //операція яка буде виконуватися асинхронно
    private val mainHandler: Handler,   //хендлер, для того щоб знати куди повернути результат
    private val threadCreation: (Runnable) -> Thread    //создає треду на якій буде виконуватися операція (Runnable)
) {
    fun postOnMainThread(callback: (T) -> Unit): CancelableOperation {
        val activeThread = threadCreation {
            //threadCreation - приймає лямбду, тому можемо одразу передати те, що ми хочемо виконати
            try {
                val result = operation()    //присвоюємо результат операціїї в змінну
                //якщо нашу треду ніхто не перервав
                if (!Thread.currentThread().isInterrupted) {
                    //через функцію post закидуємо результат на головно треду
                    mainHandler.post {
                        callback(result)    //передаємо в головний поток результат виконання операції
                    }
                }
            } catch (ignore: Exception) {
            }
        }

        return CancelableOperation {
            //повертаємо імплементацію інтерфейса CancelableOperation
            //якщо хтось викличе метод кенсел ми перервемо нашу операцію
            activeThread.interrupt()
        }
    }

    //функція щоб можно було пройти по результатам і змінити їх
    fun <R> map(transformation: (T) -> R): AsyncOperation<R> {
        return AsyncOperation(
            operation = { transformation(operation()) },
            mainHandler = mainHandler,
            threadCreation = threadCreation
        )
    }
}