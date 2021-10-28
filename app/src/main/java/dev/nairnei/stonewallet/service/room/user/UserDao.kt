package dev.nairnei.stonewallet.service.room.user

import dev.nairnei.stonewallet.model.UserModel
import androidx.room.*


@Dao
 interface UserDao {

    /// user not exist ? create : login
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun create(vararg users: UserModel)

    @Query("Select *from users where email = :email")
    fun login(email: String) : UserModel?

   @Query("Select *from users where email = :email")
   fun getUser(email: String) : UserModel

    @Update
    fun update(vararg users: UserModel)



}