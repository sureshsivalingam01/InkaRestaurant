package com.mrright.inkarestaurant.room_repository.room_services;

import android.content.Context;
import android.os.AsyncTask;

import com.mrright.inkarestaurant.interfaces.InterfaceProducts;
import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;
import com.mrright.inkarestaurant.room_repository.DataBaseClient;

import java.util.ArrayList;
import java.util.List;

public class ProductInsertAsyncTask extends AsyncTask<Void, String, Void> {

    private Context context;
    private InterfaceProducts interfaceProducts;
    private List<EntityCart> entityCartList;
    private List<EntityProducts> entityProductsList;

    public ProductInsertAsyncTask(Context context, InterfaceProducts interfaceProducts) {
        this.context = context;
        this.interfaceProducts = interfaceProducts;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            List<EntityProducts> entityProductsArrayList = getProductList();
            for (int i = 0; i < entityProductsArrayList.size(); i++) {

                DataBaseClient.getInstance(context)
                        .getDataBaseInka().doaProducts().addProducts(entityProductsArrayList.get(i));
            }

            entityCartList = DataBaseClient.getInstance(context)
                    .getDataBaseInka().daoCart().getCart();

            entityProductsList =  DataBaseClient.getInstance(context)
                    .getDataBaseInka().doaProducts().getProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        interfaceProducts.setProductList(entityProductsList,entityCartList);
    }

    private List<EntityProducts> getProductList() {
        List<EntityProducts> entityProductsArrayList = new ArrayList<>();

        entityProductsArrayList.add(new EntityProducts(1, "Dosa", "A dosa is a rice pancake, made from a fermented batter.", 100, 4, 1, 1));
        entityProductsArrayList.add(new EntityProducts(2, "Idly", "Idly is a type of savoury rice cake", 50, 31, 1, 0));
        entityProductsArrayList.add(new EntityProducts(3, "Poori", "It is made by mixing water and salt with wheat flour, rubbing them thinly in a circular shape.", 20, 4, 1, 0));
        entityProductsArrayList.add(new EntityProducts(4, "Melagu Pongal", "It is a spicy variant of the same dish made with pepper, rice and moong daal.", 70, 3, 1, 0));
        entityProductsArrayList.add(new EntityProducts(5, "Upma", "It cooked as a thick porridge from dry-roasted semolina or coarse rice flour.", 60, 4, 1, 1));
        entityProductsArrayList.add(new EntityProducts(6, "Briyani", "It can be compared to mixing a curry, later combining it with semi-cooked rice separately.", 50, 3, 1, 0));

        return entityProductsArrayList;

    }

}
