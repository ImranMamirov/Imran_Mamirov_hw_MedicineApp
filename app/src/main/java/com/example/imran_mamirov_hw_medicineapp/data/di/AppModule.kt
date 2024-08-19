package com.example.imran_mamirov_hw_medicineapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.imran_mamirov_hw_medicineapp.data.dao.MedicineDao
import com.example.imran_mamirov_hw_medicineapp.data.db.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRoomDataBase(@ApplicationContext context: Context): DataBase =
        Room.databaseBuilder(context, DataBase::class.java, "DataBase")
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    fun provideDao(loveDataBase: DataBase): MedicineDao {
        return loveDataBase.medicineDao()
    }
}