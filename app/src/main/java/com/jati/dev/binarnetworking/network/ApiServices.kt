package com.jati.dev.binarnetworking.network

import com.jati.dev.binarnetworking.model.DataResponse
import com.jati.dev.binarnetworking.model.Student
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Jati on 23/10/18.
 */

interface ApiServices {
    @POST("student/")
    fun addStudent(@Body student: Student): Call<DataResponse<Student>>

    @GET("student/{id}")
    fun getStudent(@Path("id") id: Int): Call<DataResponse<Student>>

    @PUT("student/{id}")
    fun editStudent(@Path("id") id: Int, @Body student: Student): Call<DataResponse<Student>>

    @DELETE("student/{id}")
    fun deleteStudent(@Path("id") id: Int): Call<DataResponse<Any>>

    @GET("student/all")
    fun getAllStudent(): Call<DataResponse<List<Student>>>
}