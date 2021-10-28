package dev.nairnei.stonewallet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Users", indices = [Index(value = ["email"], unique = true)])
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "amount_real")
    var amountReal: Double = 10000000.0,
    @ColumnInfo(name = "amount_brita")
    var amountBrita: Double = 0.0,
    @ColumnInfo(name = "amount_bitcoin")
    var amountBitcoin: Double = 0.0
)