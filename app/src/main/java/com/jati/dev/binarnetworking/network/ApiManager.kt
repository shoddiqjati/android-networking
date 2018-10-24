package com.jati.dev.binarnetworking.network

import com.jati.dev.binarnetworking.BuildConfig
import com.jati.dev.binarnetworking.model.DataResponse
import com.jati.dev.binarnetworking.model.Student
import com.jati.dev.binarnetworking.utils.getErrorMessage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Jati on 23/10/18.
 */

class ApiManager {
    private val BASE_URL = "https://kotlinspringcrud.herokuapp.com/api/v1/"
    var apiServices: ApiServices

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)
        val httpClient = builder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiServices = retrofit.create(ApiServices::class.java)
    }

    fun loadAllStudent(onSuccessful: (List<Student>) -> Unit, onError: (String) -> Unit) {
        apiServices.getAllStudent().enqueue(object : Callback<DataResponse<List<Student>>> {
            override fun onFailure(call: Call<DataResponse<List<Student>>>, t: Throwable) {
                onError(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<DataResponse<List<Student>>>,
                response: Response<DataResponse<List<Student>>>
            ) {
                response.let { dataResponse ->
                    if (dataResponse.isSuccessful) dataResponse.body()?.data?.let { onSuccessful(it) }
                    else dataResponse.errorBody()?.string()?.let { onError(getErrorMessage(it)) }
                }
            }

        })
    }

    fun loadStudent(studentId: Int, onSuccessful: (Student) -> Unit, onError: (String) -> Unit) {
        apiServices.getStudent(studentId).enqueue(object : Callback<DataResponse<Student>> {
            override fun onFailure(call: Call<DataResponse<Student>>, t: Throwable) {
                onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<DataResponse<Student>>, response: Response<DataResponse<Student>>) {
                response.let { dataResponse ->
                    if (dataResponse.isSuccessful) dataResponse.body()?.data?.let { onSuccessful(it) }
                    else dataResponse.errorBody()?.string()?.let { onError(getErrorMessage(it)) }
                }
            }
        })
    }

    fun addStudent(student: Student, onSuccessful: (Student) -> Unit, onError: (String) -> Unit) {
        apiServices.addStudent(student).enqueue(object : Callback<DataResponse<Student>> {
            override fun onFailure(call: Call<DataResponse<Student>>, t: Throwable) {
                onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<DataResponse<Student>>, response: Response<DataResponse<Student>>) {
                response.let { dataResponse ->
                    if (dataResponse.isSuccessful) dataResponse.body()?.data?.let { onSuccessful(it) }
                    else dataResponse.errorBody()?.string()?.let { onError(getErrorMessage(it)) }
                }
            }
        })
    }

    fun deleteStudent(studentId: Int, onSuccessful: (String) -> Unit, onError: (String) -> Unit) {
        apiServices.deleteStudent(studentId).enqueue(object : Callback<DataResponse<Any>> {
            override fun onFailure(call: Call<DataResponse<Any>>, t: Throwable) {
                onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<DataResponse<Any>>, response: Response<DataResponse<Any>>) {
                response.let { dataResponse ->
                    if (dataResponse.isSuccessful) onSuccessful("Student deleted")
                    else dataResponse.errorBody()?.string()?.let { onError(getErrorMessage(it)) }
                }
            }
        })
    }

    fun editStudent(student: Student, onSuccessful: (Student) -> Unit, onError: (String) -> Unit) {
        student.id?.let {
            apiServices.editStudent(student.id, student).enqueue(object : Callback<DataResponse<Student>> {
                override fun onFailure(call: Call<DataResponse<Student>>, t: Throwable) {
                    onError(t.localizedMessage)
                }

                override fun onResponse(call: Call<DataResponse<Student>>, response: Response<DataResponse<Student>>) {
                    response.let { dataResponse ->
                        if (dataResponse.isSuccessful) dataResponse.body()?.data?.let { onSuccessful(it) }
                        else dataResponse.errorBody()?.string()?.let { onError(getErrorMessage(it)) }
                    }
                }
            })
        }
    }
}