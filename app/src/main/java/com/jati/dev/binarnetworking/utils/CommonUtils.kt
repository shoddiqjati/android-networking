package com.jati.dev.binarnetworking.utils

import com.google.gson.Gson
import com.jati.dev.binarnetworking.model.Error

/**
 * Created by Jati on 23/10/18.
 */

fun getErrorMessage(error: String): String = Gson().fromJson(error, Error::class.java).message