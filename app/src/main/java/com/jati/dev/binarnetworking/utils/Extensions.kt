package com.jati.dev.binarnetworking.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Jati on 23/10/18.
 */

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}