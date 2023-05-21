package com.example.shoppingapp.data.di


import com.example.shoppingapp.BuildConfig
import com.example.shoppingapp.data.api.ApiService
import com.example.shoppingapp.data.datasource.remote.recipes.add.DefaultRemoteAddRecipeDatasource
import com.example.shoppingapp.data.datasource.remote.recipes.add.RemoteAddRecipeDatasource
import com.example.shoppingapp.data.datasource.remote.recipes.search.DefaultRemoteSearchRecipeDatasource
import com.example.shoppingapp.data.datasource.remote.recipes.search.RemoteSearchRecipeDatasource
import com.example.shoppingapp.data.repository.recipe.add.AddRecipeRepository
import com.example.shoppingapp.data.repository.recipe.add.DefaultAddRecipeRepository
import com.example.shoppingapp.data.repository.recipe.search.DefaultSearchRecipeRepository
import com.example.shoppingapp.data.repository.recipe.search.SearchRecipeRepository
import com.example.shoppingapp.utils.string.DefaultStringResolver
import com.example.shoppingapp.utils.string.StringResolver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .followRedirects(true).followSslRedirects(true)
        return if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor).build()
        } else {
            builder.build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
//        appContext, AppDataBase::class.java, "item_database"
//    )
//        .fallbackToDestructiveMigration()
//        .build()
//
//    @Provides
//    @Singleton
//    fun provideRecipeDao(db: AppDataBase) = db.RecipeDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun provideSearchRecipeDatasource(defaultSearchRecipeDatasource: DefaultRemoteSearchRecipeDatasource): RemoteSearchRecipeDatasource

    @Binds
    abstract fun provideRemoteAddRecipeDatasource(defaultRemoteAddRecipeDatasource: DefaultRemoteAddRecipeDatasource): RemoteAddRecipeDatasource

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideSearchRecipeRepository(defaultSearchRecipeRepository: DefaultSearchRecipeRepository): SearchRecipeRepository

    @Binds
    abstract fun provideAddRecipeRepository(defaultAddRecipeRepository: DefaultAddRecipeRepository): AddRecipeRepository

}

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {
    @Binds
    abstract fun provideStringResolver(defaultStringResolver: DefaultStringResolver): StringResolver

}

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesDispatchersModule {

    @DefaultDispatcher
    @Provides
    @Singleton
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    @Singleton
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    @Singleton
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    @Singleton
    fun providesMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}

//Qualifiers
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher

