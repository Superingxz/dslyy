package com.dsl.network.vo

/**
 * @author dsl-abben
 * on 2020/02/14.
 */
data class RealResponseBody<T>(
val status: Int,
val data: T,
val message: String
)