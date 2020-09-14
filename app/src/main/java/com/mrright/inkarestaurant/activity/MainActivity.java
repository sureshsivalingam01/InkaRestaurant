package com.mrright.inkarestaurant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainActivity extends AppCompatActivity {

    private LinearLayout buttonCart;
    private TextView tvTotalItem;
    private Button buttonBook;
    private ImageView imageViewBack, imageViewLogout, imageViewInfo;
    private FloatingTextButton action_button_menu;
    private RecyclerView listViewFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        onCLick();

        loadData();

        getCartCount();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        getCartCount();
    }

    private void onCLick() {

        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityCart.class));
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
            }
        });

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Booked a table", Toast.LENGTH_SHORT).show();
            }
        });

        action_button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Menu for the day", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {

        new ProductInsertAsyncTask(getApplicationContext(), new InterfaceProducts() {

            @Override
            public void setProductList(List<EntityProducts> entityProductsList, List<EntityCart> entityCartList) {

                listViewFoods.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                listViewFoods.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                listViewFoods.setAdapter(new AdapterProducts(getApplicationContext(), entityProductsList, entityCartList, new OnRefresh() {

                    @Override
                    public void onCartRefresh() {

                        new AsyncTaskCartTotal(getApplicationContext(), new InterfaceCart() {
                            @Override
                            public void setCart(List<EntityCart> entityCartList, List<EntityProducts> entityProductsList) {
                                String totalViewCart;
                                if (entityCartList.size() > 0) {
                                    totalViewCart = getResources().getString(R.string.viewCart) + " [" + entityCartList.size() + " ITEMS]";
                                } else {
                                    totalViewCart = getResources().getString(R.string.viewCartItems);
                                }
                                tvTotalItem.setText(totalViewCart);
                            }

                        }).execute();

                    }

                    @Override
                    public void getCartPosition(int cartPosition) {

                    }

                }, false));

            }

        }).execute();

    }

    private void init() {

        buttonCart = findViewById(R.id.buttonCart);

        tvTotalItem = findViewById(R.id.tvTotalItem);

        buttonBook = findViewById(R.id.buttonBook);

        imageViewBack = findViewById(R.id.imageViewBack);

        imageViewLogout = findViewById(R.id.imageViewLogout);

        imageViewInfo = findViewById(R.id.imageViewInfo);

        action_button_menu = findViewById(R.id.action_button_menu);

        listViewFoods = findViewById(R.id.listViewFoods);

    }

    private void getCartCount() {

        new AsyncTaskCartTotal(getApplicationContext(), new InterfaceCart() {
            @Override
            public void setCart(List<EntityCart> entityCartList, List<EntityProducts> entityProductsList) {
                String totalviewCart;
                if (entityCartList.size() > 0) {
                    totalviewCart = getResources().getString(R.string.viewCart) + " [" + entityCartList.size() + " ITEMS]";
                } else {
                    totalviewCart = getResources().getString(R.string.viewCartItems);
                }
                tvTotalItem.setText(totalviewCart);
                if (listViewFoods.getAdapter() != null) {
                    listViewFoods.getAdapter().notifyDataSetChanged();
                }
            }

        }).execute();

    }


}