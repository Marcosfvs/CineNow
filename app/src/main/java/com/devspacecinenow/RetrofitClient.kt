package com.devspacecinenow

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "https://api.themoviedb.org/3/movie/"

object RetrofitClient {
    // O Retrofitbuilder vai ser capaz de criar o service

    //OKHttpClient - Intercepta a chamada, toda vez que faz a chamada da API, vai passar o token para ter acesso a API
    private val httpClient: OkHttpClient
        //Garante que httpclient seja iniciado apenas uma vez
        get() {
            val clientBuilder = OkHttpClient.Builder()
            val token = BuildConfig.API_KEY
            //Request é a chamada da API
            clientBuilder.addInterceptor { chain ->
                val original: Request = chain.request()
                //Request original sem o header, precisa adicionar o header (autorização do token).
                //Depois que adiciona faz uma nova request e build
                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("Authorization", "Bearer ${token}")
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            return clientBuilder.build()
        }

    val retrofitInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}