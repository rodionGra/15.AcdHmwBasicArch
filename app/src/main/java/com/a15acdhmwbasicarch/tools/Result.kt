package com.a15acdhmwbasicarch.tools
/**Приватний конструктор, щоб можна було створити або з норм результатом або з помилкою*/

class Result<T, E> private constructor(
    private val success: ValueWrapper<T>? = null,
    private val error: ValueWrapper<E>? = null
) {
    private class ValueWrapper<T>(val value: T)


/**щоб дізнатися чи в об'єкті помилка чи результат*/

    var isError = error != null

    val successResult: T
        get() = requireNotNull(success) {
            "Result was without success"
        }.value

    val errorResult: E
        get() = requireNotNull(error) {
            "Result was without error"
        }.value


/**повертає новий результат до якого приміняється операція*/

    fun <R> mapSuccess(transformation: (T) -> (R)): Result<R, E> {
        return Result(
            success = success?.let { ValueWrapper(transformation(it.value)) },
            error = error
        )
    }


/**повертає нову помилку до якого приміняється операція*/

    fun <R> mapError(transformation: (E) -> R): Result<T, R> {
        return Result(
            success = success,
            error = error?.let { ValueWrapper(transformation(it.value)) }
        )
    }

    companion object {
        fun <T, E> success(entity: T): Result<T, E> {
            return Result(ValueWrapper(entity), null)
        }

        fun <T, E> error(error: E): Result<T, E> {
            return Result(null, ValueWrapper(error))
        }
    }
}
