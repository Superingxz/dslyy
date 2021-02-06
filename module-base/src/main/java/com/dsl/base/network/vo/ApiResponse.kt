package com.dsl.base.network.vo

import retrofit2.Response

/**
 * @author dsl-abben
 * on 2020/02/14.
 */
class ApiResponse<T>(
    val code: Int,
    val body: T?,
    val errorMessage: String?
) {

    constructor(error: Throwable) : this(601, null, error.message)

    constructor(response: Response<T>) : this(
        response.code(),
        if (response.isSuccessful) response.body() else null,
        if (response.isSuccessful) null else response.errorBody()?.string() ?: "unknown error"
    )
}
