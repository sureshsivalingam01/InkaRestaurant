package com.mrright.inkarestaurant.room_repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;
import com.mrright.inkarestaurant.room_repository.dao.DAOCart;
import com.mrright.inkarestaurant.room_repository.dao.DOAProducts;

@Database(entities = {EntityProducts.class, EntityCart.class}, version = 1, exportSchema = false)
public abstract class DataBaseInka extends RoomDatabase {

    public abstract DOAProducts doaProducts();

    public abstract DAOCart daoCart();

}
