package com.example.littleelephant.apiRest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun registerUser(request: RegisterRequest, callback: (Result<RegisterResponse>) -> Unit) {
        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    callback(Result.success(response.body()!!))
                } else {
                    callback(Result.failure(Exception("Error: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }
}



