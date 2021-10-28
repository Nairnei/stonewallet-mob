package dev.nairnei.stonewallet.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import dev.nairnei.stonewallet.model.UserModel
import dev.nairnei.stonewallet.service.room.RoomService
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import dev.nairnei.stonewallet.model.ReportModel


class DaoViewModel : ViewModel() {
    lateinit var database: RoomService
    private var userLiveData = MutableLiveData<UserModel?>()
    private var listReportLiveData = MutableLiveData<List<ReportModel>>()


    fun init(context: Context): RoomService {
        database = RoomService.getInstance(context)
        return database;
    }

    fun trade(
        userId: String, amountFrom: Double, amountTo: Double, createdAt: String,
        from: String, quoatationFrom: String, quotationTo: String, to: String
    ) {

        AsyncTask.execute {
            val report = ReportModel(
                userId = userId,
                amountFrom = amountFrom.toString(),
                amoutTo = amountTo.toString(),
                createdAt = createdAt,
                quotationFrom = quoatationFrom,
                quotationTo = quotationTo,
                to = to,
                from = from
            )

            database.userDao().getUser(userId).let {
                when (from) {
                    "Real" -> {

                        when (to) {
                            "Bitcoin" -> {
                                if (amountFrom <= it.amountReal) {
                                    it.amountReal = (it.amountReal - amountFrom).toLong()
                                    it.amountBitcoin = (it.amountBitcoin + amountTo).toLong()
                                    database.reportDao().insert(report)
                                    database.userDao().update(it)
                                }
                            }
                            "Brita" -> {
                                if (amountFrom <= it.amountReal) {
                                    it.amountReal = (it.amountReal - amountFrom).toLong()
                                    it.amountBrita = (it.amountBrita + amountTo).toLong()
                                    database.reportDao().insert(report)
                                    database.userDao().update(it)
                                }
                            }
                        }
                    }

                    "Brita" -> {

                        when (to) {
                            "Real" -> {
                                if (amountFrom <= it.amountBrita) {

                                    it.amountBrita = (it.amountBrita - amountFrom).toLong()
                                    it.amountReal = (it.amountReal + amountFrom * quoatationFrom.toDouble()).toLong()

                                    database.reportDao().insert(report)
                                    database.userDao().update(it)
                                }

                            }
                            "Bitcoin" -> {

                                if (amountFrom <= it.amountBrita) {
                                    it.amountBrita = (it.amountBrita - amountFrom).toLong()
                                    it.amountBitcoin = (it.amountBitcoin + amountFrom * quoatationFrom.toDouble()).toLong()
                                    database.reportDao().insert(report)
                                    database.userDao().update(it)
                                }
                            }
                        }
                    }

                    "Bitcoin" -> {

                        when (to) {
                            "Real" -> {

                                if (amountFrom <= it.amountBitcoin) {
                                    it.amountBitcoin = (it.amountBitcoin - amountFrom).toLong()
                                    it.amountReal = (it.amountReal + amountFrom * quoatationFrom.toDouble()).toLong()
                                    database.reportDao().insert(report)
                                    database.userDao().update(it)
                                }

                            }
                            "Brita" -> {

                                if (amountFrom <= it.amountBitcoin) {
                                    it.amountBrita = (it.amountBrita - amountFrom).toLong()
                                    it.amountReal = (it.amountReal + amountFrom * quoatationFrom.toDouble()).toLong()
                                    database.reportDao().insert(report)
                                    database.userDao().update(it)
                                }

                            }
                        }
                    }
                }
            }


        }

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

    fun listReport() : MutableLiveData<List<ReportModel>>
    {
        return listReportLiveData
    }

    fun listReports(currentUser: String){
        AsyncTask.execute {
            database.reportDao().list(currentUser).let {
                listReportLiveData.postValue(it)
            }
        }
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