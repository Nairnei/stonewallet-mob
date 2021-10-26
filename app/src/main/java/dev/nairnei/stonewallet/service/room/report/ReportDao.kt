package dev.nairnei.stonewallet.service.room.report

import dev.nairnei.stonewallet.model.ReportModel
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
 interface ReportDao {

    @Query("Select *from Report where user_id = :userId")
    fun list(userId: String): LiveData<List<ReportModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg report: ReportModel)


}