package com.example.rocketreserver

import android.content.Context
import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


//val apolloClient = ApolloClient.builder()
//    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
//    .build()

private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {

    if (instance == null) {
        instance = ApolloClient.builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(AuthorizationInterceptor(context))
                    .build()
            )
            .build()
    }

    return instance!!
}

private class AuthorizationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", User.getToken(context) ?: "")
            .build()

        return chain.proceed(request)
    }
}