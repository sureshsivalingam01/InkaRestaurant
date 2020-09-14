package com.mrright.inkarestaurant.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrright.inkarestaurant.R;
import com.mrright.inkarestaurant.adapter.AdapterProducts;
import com.mrright.inkarestaurant.interfaces.InterfaceCart;
import com.mrright.inkarestaurant.interfaces.InterfaceProducts;
import com.mrright.inkarestaurant.interfaces.OnRefresh;
import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;
import com.mrright.inkarestaurant.room_repository.room_services.AsyncTaskCartTotal;
import com.mrright.inkarestaurant.room_repository.room_services.ProductInsertAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class ActivityCart extends AppCompatActivity {

    private ImageButton imageButtonBack;
    private RecyclerView recyclerViewtems;
    private TextView textViewCost, textViewShowmore;
    private Button buttonPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);

        init();

        onCLick();

        loadData(1);

        getCartTotal();

    }

    private void init() {

        imageButtonBack = findViewById(R.id.imageButtonBack);
        recyclerViewtems = findViewById(R.id.recyclerViewtems);
        textViewCost = findViewById(R.id.textViewCost);
        textViewShowmore = findViewById(R.id.textViewShowmore);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

    }

    private void onCLick() {

        textViewShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(2);
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Place Order", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadData(final int cartListShow) {

        new ProductInsertAsyncTask(getApplicationContext(), new InterfaceProducts() {

            @Override
            public void setProductList(final List<EntityProducts> entityProductsList, List<EntityCart> entityCartListNew) {

                List<EntityCart> entityCartList = new ArrayList<>();

                if (cartListShow == 1) {
                    if (entityCartListNew.size() >= 2) {
                        for (int i = 0; i < 2; i++) {
                            entityCartList.add(entityCartListNew.get(i));
                        }
                    }
                } else if (cartListShow == 2) {
                    entityCartList = entityCartListNew;
                    textViewShowmore.setVisibility(View.GONE);

                }

                recyclerViewtems.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerViewtems.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                recyclerViewtems.setAdapter(new AdapterProducts(getApplicationContext(), getCartProductList(entityProductsList, entityCartList), entityCartList, new OnRefresh() {
                    @Override
                    public void onCartRefresh() {

                        new AsyncTaskCartTotal(getApplicationContext(), new InterfaceCart() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void setCart(List<EntityCart> entityCartList, List<EntityProducts> entityProductsList) {
                                double total = 0;
                                if (entityCartList.size() > 0) {
                                    for (int i = 0; i < entityCartList.size(); i++) {
                                        for (int j = 0; j < entityProductsList.size(); j++) {
                                            if (entityCartList.get(i).getProductID() == entityProductsList.get(j).getId()) {
                                                total = total + entityCartList.get(i).getQuantity() * entityProductsList.get(j).getPrice();
                                            }
                                        }

                                    }
                                    String getTotal = getResources().getString(R.string.rupees_symbol) + " " + total;
                                    textViewCost.setText(getTotal);

                                } else {

                                    textViewCost.setText(getResources().getString(R.string.rupees_symbol) + " 0.0");
                                }
                                if (recyclerViewtems.getAdapter() != null) {
                                    recyclerViewtems.getAdapter().notifyDataSetChanged();

                                }
                            }


                        }).execute();

                    }

                    @Override
                    public void getCartPosition(int cartPosition) {
                        if (recyclerViewtems.getAdapter() != null) {
                            recyclerViewtems.removeViewAt(cartPosition);
                        }

                    }

                }, true));

            }

        }).execute();

    }

    private void getCartTotal() {

        new AsyncTaskCartTotal(getApplicationContext(), new InterfaceCart() {

            @Override
            public void setCart(List<EntityCart> entityCartList, List<EntityProducts> entityProductsList) {
                if (entityCartList.size() > 0) {
                    double total = 0;
                    for (int i = 0; i < entityCartList.size(); i++) {
                        for (int j = 0; j < entityProductsList.size(); j++) {
                            if (entityCartList.get(i).getProductID() == entityProductsList.get(j).getId()) {
                                total = total + entityCartList.get(i).getQuantity() * entityProductsList.get(j).getPrice();
                            }
                        }

                    }
                    String getTotal = getResources().getString(R.string.rupees_symbol) + " " + total;
                    textViewCost.setText(getTotal);
                }
            }
        }).execute();

    }

    private List<EntityProducts> getCartProductList(List<EntityProducts> entityProductsList, List<EntityCart> entityCartList) {

        List<EntityProducts> tempProductList = new ArrayList<>();
        for (int i = 0; i < entityProductsList.size(); i++) {
            for (int j = 0; j < entityCartList.size(); j++) {
                if (entityProductsList.get(i).getId() == entityCartList.get(j).getProductID()) {
                    tempProductList.add(entityProductsList.get(i));
                }
            }
        }
        return tempProductList;
    }

}