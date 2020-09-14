package com.mrright.inkarestaurant.room_repository.room_services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.mrright.inkarestaurant.interfaces.InterfaceCart;
import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;
import com.mrright.inkarestaurant.room_repository.DataBaseClient;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class CartInsertAsyncTask extends AsyncTask<Void, Void, List<EntityCart>> {

    private int type;
    private Context context;
    private InterfaceCart interfaceCart;
    private EntityCart entityCart;
    private List<EntityProducts> entityProductsList;

    public CartInsertAsyncTask(int type, Context context, EntityCart entityCart, InterfaceCart interfaceCart) {
        this.context = context;
        this.interfaceCart = interfaceCart;
        this.entityCart = entityCart;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<EntityCart> doInBackground(Void... voids) {

        switch (type) {
            case 1:
                DataBaseClient.getInstance(context)
                        .getDataBaseInka()
                        .daoCart()
                        .addToCart(entityCart);
                break;
            case 2:
                DataBaseClient.getInstance(context)
                        .getDataBaseInka()
                        .daoCart()
                        .updateCart(entityCart.getId(), entityCart.getQuantity());
                break;
            case 3:
                DataBaseClient.getInstance(context)
                        .getDataBaseInka()
                        .daoCart()
                        .deleteCart(entityCart);
                break;

        }
        entityProductsList = DataBaseClient.getInstance(context).getDataBaseInka().doaProducts().getProducts();
        return DataBaseClient.getInstance(context).getDataBaseInka().daoCart().getCart();
    }

    @Override
    protected void onPostExecute(List<EntityCart> entityCartList) {
        super.onPostExecute(entityCartList);
        interfaceCart.setCart(entityCartList, entityProductsList);
    }


}




