package pl.dev.qcta_2_blazejkwiatkowski.network

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class FixerAPIController {

    private val API_KEY = "5UEVByj8QaXTckdq4vE7WN2z8h9jA1IH"
    private val BASE_URL = "https://mocki.io"

    private fun getHttpLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun getCallFactory(httpLoggingInterceptor: HttpLoggingInterceptor): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .callFactory(getCallFactory(getHttpLoggerInterceptor()))
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }


    fun getFixerService(): FixerAPI = getRetrofit().create(FixerAPI::class.java)

}