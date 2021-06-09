package tomuch.coffee.tokoonline.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import tomuch.coffee.tokoonline.model.Produk

@Dao
interface DaoCart {
    @Insert(onConflict = REPLACE)
    fun insert(data: Produk)

    @Delete
    fun delete(data: Produk)

    @Update
    fun update(data: Produk): Int

    @Query("SELECT * from cart ORDER BY id ASC")
    fun getAll(): List<Produk>

    @Query("SELECT * FROM cart WHERE id = :id LIMIT 1")
    fun getNote(id: Int): Produk

    @Query("DELETE FROM cart")
    fun deleteAll(): Int
}