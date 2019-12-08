package fr.epita.android.common

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient<W>(url: String, service: Class<W>) {
    private val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(jsonConverter)
        .build()
    private val service = retrofit.create(service)

    fun getService(): W {
        return this.service
    }
}