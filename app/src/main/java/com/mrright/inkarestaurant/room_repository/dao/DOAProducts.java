package com.mrright.inkarestaurant.room_repository.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mrright.inkarestaurant.model.EntityProducts;

import java.util.List;

@Dao
public interface DOAProducts {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProducts(EntityProducts entityProducts);

    @Query("SELECT * FROM product_table")
    List<EntityProducts> getProducts();

}
