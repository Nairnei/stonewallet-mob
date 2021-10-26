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
    var amountReal: Long = 100000,
    @ColumnInfo(name = "amount_brita")
    var amountBrita: Long = 0,
    @ColumnInfo(name = "amount_bitcoin")
    var amountBitcoin: Long = 0
)