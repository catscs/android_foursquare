package es.webandroid.foursquare.framework.remote


import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkServiceProvider {
    companion object {
        private const val CONNECT_TIMEOUT: Long = 15
        private const val READ_TIMEOUT: Long = 15
        private const val PARAM_CLIENT_ID = "client_id"
        private const val PARAM_CLIENT_SECRET = "client_secret"
        private const val PARAM_VERSION = "v"
        private const val VERSION = "20201119"
        private const val BASE_URL_FOURSQUARE = "https://api.foursquare.com/v2/"
        private const val FOURSQUARE_CLIENT_ID = ""
        private const val FOURSQUARE_CLIENT_SECRET = ""
    }

    private fun client(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                val url = request
                    .url
                    .newBuilder()
                    .addQueryParameter(
                        PARAM_CLIENT_ID,
                        FOURSQUARE_CLIENT_ID
                    )
                    .addQueryParameter(
                        PARAM_CLIENT_SECRET,
                        FOURSQUARE_CLIENT_SECRET
                    )
                    .addQueryParameter(
                        PARAM_VERSION,
                        VERSION
                    )
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_FOURSQUARE)
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .client(client())
            .build()
    }

    private fun moshi() = Moshi.Builder().build()

    fun networkApi(): NetworkApi = retrofit().create(NetworkApi::class.java)
}
