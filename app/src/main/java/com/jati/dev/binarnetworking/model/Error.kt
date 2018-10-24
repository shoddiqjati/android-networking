package com.jati.dev.binarnetworking.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Jati on 23/10/18.
 */

class Error(@SerializedName("code") val code: Int, @SerializedName("message") val message: String)