package dev.nairnei.stonewallet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Report")
data class ReportModel(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "to")
    val to: String,

    @ColumnInfo(name = "from")
    val from: String,

    @ColumnInfo(name = "amount_to")
    val amoutTo: String,

    @ColumnInfo(name = "amount_from")
    val amountFrom: String,

    @ColumnInfo(name = "quotation_to")
    val quotationTo: String,

    @ColumnInfo(name = "quotation_from")
    val quotationFrom: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String,
)