package com.dsl.network.vo

/**
 *  @author dsl-abben
 *  on 2020/02/14.
 */
class Resource<T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(message: String?, data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, message)
        }

        fun <T> error(message: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
