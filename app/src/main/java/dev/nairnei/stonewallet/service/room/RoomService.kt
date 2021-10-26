package dev.nairnei.stonewallet.service.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

import androidx.room.Database
import dev.nairnei.stonewallet.model.ReportModel
import dev.nairnei.stonewallet.model.UserModel
import dev.nairnei.stonewallet.service.room.report.ReportDao
import dev.nairnei.stonewallet.service.room.user.UserDao


///RoomService like a Singleton, difamado anti pattern
@Database(entities = [UserModel::class, ReportModel::class], version = 1)
abstract class RoomService : RoomDatabase(){

    abstract fun userDao() : UserDao
    abstract fun reportDao() : ReportDao

    companion object {
        private var INSTANCE : RoomService? = null

        @Synchronized
        fun getInstance(context : Context) : RoomService {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RoomService::class.java,
                    "database.db").build()
            }
            return INSTANCE as RoomService
        }
    }
}