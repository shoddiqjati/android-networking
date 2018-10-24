package com.jati.dev.binarnetworking.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Jati on 23/10/18.
 */

class DataResponse<T>(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: T?,
    @SerializedName("error")
    val error: Error?
)