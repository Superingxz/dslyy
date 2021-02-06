package com.dsl.util.compress.listener

import java.io.File

/**
 * @author dsl-abben
 * on 2020/02/24.
 */
interface OnCompressListener {
    /**
     * Fired when the compression is started, override to handle in your own code
     */
    fun onStart()

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    fun onSuccess(file: File)

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    fun onError(e: Throwable)
}
