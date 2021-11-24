package com.holland.myapp.entity

import android.content.Context
import androidx.room.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String
) {

    fun insert() {
        action.findByName(firstName, lastName)
            ?: action.insertAll(this)
    }

    fun delete() = action.delete(this)

    companion object {
        @Database(entities = [User::class], version = 1)
        abstract class UserDatabase : RoomDatabase() {
            abstract fun userDao(): UserDao
        }

        @Dao
        interface UserDao {
            @Query("SELECT * FROM user")
            fun getAll(): List<User>

            @Query("SELECT * FROM user WHERE uid IN (:userIds)")
            fun loadAllByIds(userIds: IntArray): List<User>

            @Query(
                "SELECT * FROM user WHERE first_name LIKE :first AND " +
                        "last_name LIKE :last LIMIT 1"
            )
            fun findByName(first: String, last: String): User?

            @Insert
            fun insertAll(vararg users: User)

            @Delete
            fun delete(user: User)
        }

        @JvmStatic
        @JvmName("UserDao")
        fun UserDao.delete(uid: Int) = delete(User(uid, "", ""))

        lateinit var action: UserDao;

        fun initDb(context: Context) {
            val db: UserDatabase = Room.databaseBuilder(
                context,
                UserDatabase::class.java, "user"
            ).build()
            action = db.userDao()
        }
    }
}