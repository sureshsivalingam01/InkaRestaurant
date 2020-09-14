package com.mrright.inkarestaurant.room_repository.room_services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.mrright.inkarestaurant.interfaces.InterfaceCart;
import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;
import com.mrright.inkarestaurant.room_repository.DataBaseClient;

import java.util.List;

public class AsyncTaskCartTotal extends AsyncTask<Void, Void, List<EntityCart>> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private InterfaceCart interfaceCart;
    private List<EntityProducts> entityProductsList;

    public AsyncTaskCartTotal(Context context, InterfaceCart interfaceCart) {
        this.context = context;
        this.interfaceCart = interfaceCart;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<EntityCart> doInBackground(Void... voids) {
        entityProductsList = DataBaseClient.getInstance(context).getDataBaseInka().doaProducts().getProducts();
        return DataBaseClient.getInstance(context).getDataBaseInka().daoCart().getCart();
    }

    @Override
    protected void onPostExecute(List<EntityCart> entityCarts) {
        super.onPostExecute(entityCarts);
        interfaceCart.setCart(entityCarts, entityProductsList);
    }

}
