package com.example.pixabayclean.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.pixabayclean.data.mapper.Mapper
import com.example.pixabayclean.data.mapper.RemoteImageMapper
import com.example.pixabayclean.data.network.api_service.ImageApiService
import com.example.pixabayclean.data.network.entity.NetworkImage
import com.example.pixabayclean.data.repository.ImagePagingSource
import com.example.pixabayclean.data.repository.ImageRepositoryImpl
import com.example.pixabayclean.domain.interactor.SearchImageUseCase
import com.example.pixabayclean.domain.model.Image
import com.example.pixabayclean.domain.repository.ImageRepository
import com.example.pixabayclean.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideImageService(client: OkHttpClient): ImageApiService {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ImageApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRemoteImageMapper(): Mapper<NetworkImage, Image> {
        return RemoteImageMapper()
    }

    @Singleton
    @Provides
    fun provideImageRepository(
        imagePagingSource: ImagePagingSource
    ): ImageRepository =
        ImageRepositoryImpl(imagePagingSource)

    @Singleton
    @Provides
    fun provideGetImagesUseCase(
        ImageRepository: ImageRepository,
    ): SearchImageUseCase =
        SearchImageUseCase(ImageRepository)

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
}