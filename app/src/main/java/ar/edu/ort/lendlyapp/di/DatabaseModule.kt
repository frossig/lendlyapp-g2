package ar.edu.ort.lendlyapp.di

import android.content.Context
import androidx.room.Room
import ar.edu.ort.lendlyapp.data.local.LendlyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LendlyDatabase =
        Room.databaseBuilder(
            context,
            LendlyDatabase::class.java,
            "lendly.db"
        ).build()
}
