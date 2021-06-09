package tomuch.coffee.tokoonline.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tomuch.coffee.tokoonline.model.Produk

@Database(entities = [Produk::class] /* List Model */ , version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun daoCart(): DaoCart //Dao Cart

    companion object{
        private var  INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (INSTANCE == null){
                synchronized(MyDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, "MyDatabasename1" //nama database
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}