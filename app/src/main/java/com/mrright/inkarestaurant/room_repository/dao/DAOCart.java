package com.mrright.inkarestaurant.room_repository.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;

import java.util.List;

@Dao
public interface DAOCart {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToCart(EntityCart entityCart);

    @Query("SELECT * FROM cart_table")
    List<EntityCart> getCart();

    @Query("UPDATE cart_table SET quantity = :quantity WHERE id =:id")
    void updateCart(int id,int quantity);

    @Delete
    void deleteCart(EntityCart entityCart);


}
