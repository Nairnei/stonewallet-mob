package dev.nairnei.stonewallet.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import dev.nairnei.stonewallet.model.UserModel
import dev.nairnei.stonewallet.service.room.RoomService
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData


class DaoViewModel : ViewModel() {
    lateinit var database: RoomService
    private var userLiveData = MutableLiveData<UserModel?>()


    fun init(context: Context): RoomService {
        database = RoomService.getInstance(context)
        return database;
    }

    ///fixme: AsyncTask is deprecated
    fun createOrLogin(email: String) {
        val user = UserModel(email = email)
        AsyncTask.execute {
            database.userDao().login(email).let { existUser ->

                if (existUser != null) {
                    ///find a user
                    userLiveData.postValue(existUser)
                } else {
                    ///create user and verify success
                    database.userDao().create(user).let {
                        database.userDao().login(email).let {
                            userLiveData.postValue(it)
                        }

                    }
                }

            }

        }
    }

    fun currentUser(): MutableLiveData<UserModel?> {
        return userLiveData
    }

    ///fixme: AsyncTask is deprecated
    fun setCurrentUser(currentUser: String) {
        AsyncTask.execute {
            database.userDao().login(currentUser).let {
                userLiveData.postValue(it)
            }
        }
    }
}