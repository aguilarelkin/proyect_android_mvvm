package com.kto.employenexa.data.network

import android.content.Context
import com.kto.employenexa.data.RepositoryImpl
import com.kto.employenexa.data.RepositoryLoginImpl
import com.kto.employenexa.data.RepositoryServicemImpl
import com.kto.employenexa.data.core.datastore.DataInfo
import com.kto.employenexa.data.core.interceptors.AuthInterceptor
import com.kto.employenexa.domain.Repository
import com.kto.employenexa.domain.RepositoryLogin
import com.kto.employenexa.domain.RepositoryServicem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module//se usa para injectar retrofit o iinterfaces
@InstallIn(SingletonComponent::class)//alcance
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("http://192.168.43.54:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    //interceptor
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(authInterceptor)
            .build()
    }

    //Employe
    @Provides
    fun provideEmployeApiService(retrofit: Retrofit): EmployeApiService {
        return retrofit.create(EmployeApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: EmployeApiService): Repository {
        return RepositoryImpl(apiService)
    }

    //Services
    @Provides
    fun provideServicemApiService(retrofit: Retrofit): ServicemApiService {
        return retrofit.create(ServicemApiService::class.java)
    }

    @Provides
    fun provideRepositoryServicem(apiService: ServicemApiService): RepositoryServicem {
        return RepositoryServicemImpl(apiService)
    }

    //Login
    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Provides
    fun provideRepositoryLogin(apiService: LoginApiService, dataInfo: DataInfo): RepositoryLogin {
        return RepositoryLoginImpl(apiService, dataInfo)
    }
    //DataStore
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataInfo {
        return DataInfo(context)
    }

}