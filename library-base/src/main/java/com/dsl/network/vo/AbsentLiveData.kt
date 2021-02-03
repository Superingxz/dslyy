package com.dsl.network.vo

import androidx.lifecycle.LiveData

/**
 * value为null的LiveData
 *
 * @author dsl-abben
 * on 2020/02/17.
 */
class AbsentLiveData<T : Any?> : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}
