package com.mrright.inkarestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrright.inkarestaurant.R;
import com.mrright.inkarestaurant.interfaces.InterfaceCart;
import com.mrright.inkarestaurant.interfaces.OnRefresh;
import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;
import com.mrright.inkarestaurant.room_repository.room_services.CartInsertAsyncTask;

import java.util.List;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolderProducts> {

    private Context context;
    private List<EntityProducts> entityProductsList;
    private List<EntityCart> entityCartList;
    private OnRefresh onRefresh;
    private boolean isCart;

    public AdapterProducts(Context context, List<EntityProducts> entityProductsList, List<EntityCart> entityCartList, OnRefresh onRefresh, boolean isCart) {
        this.context = context;
        this.entityProductsList = entityProductsList;
        this.entityCartList = entityCartList;
        this.onRefresh = onRefresh;
        this.isCart = isCart;
    }

    @Override
    public int getItemCount() {
        return entityProductsList.size();
    }

    @NonNull
    @Override
    public AdapterProducts.ViewHolderProducts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolderProducts(LayoutInflater.from(context).inflate(R.layout.listview_cart, parent, false));

    }

    static class ViewHolderProducts extends RecyclerView.ViewHolder {

        TextView imageViewM, textViewQuantity, textViewNight, textViewDay, textViewName, textViewDescription, textViewRupees, textViewMinus, textViewPlus;
        Button buttonAdd;
        LinearLayout linearCount;


        public ViewHolderProducts(@NonNull View itemView) {

            super(itemView);
            textViewNight = itemView.findViewById(R.id.textViewNight);
            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewRupees = itemView.findViewById(R.id.textViewRupees);
            textViewMinus = itemView.findViewById(R.id.textViewMinus);
            textViewPlus = itemView.findViewById(R.id.textViewPlus);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            buttonAdd = itemView.findViewById(R.id.buttonAdd);
            linearCount = itemView.findViewById(R.id.linearCount);
            imageViewM = itemView.findViewById(R.id.imageViewM);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterProducts.ViewHolderProducts holder, final int position) {

        if (isCart) {
            holder.buttonAdd.setVisibility(View.GONE);
            holder.imageViewM.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewM.setVisibility(View.GONE);
        }

        holder.textViewName.setText(entityProductsList.get(position).getProductName());
        holder.textViewDescription.setText(entityProductsList.get(position).getProductDescription());
        String rupees = context.getResources().getText(R.string.rupees_symbol) + String.valueOf(entityProductsList.get(position).getPrice());
        holder.textViewRupees.setText(rupees);

        if (entityProductsList.get(position).getNight() == 1) {
            holder.textViewNight.setVisibility(View.VISIBLE);
        } else {
            holder.textViewNight.setVisibility(View.INVISIBLE);
        }

        if (entityProductsList.get(position).getDay() == 1) {
            holder.textViewDay.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDay.setVisibility(View.INVISIBLE);
        }

        EntityCart entityCart = findCardItem(entityProductsList.get(position).getId());
        if (entityCart != null) {
            holder.buttonAdd.setVisibility(View.GONE);
            holder.linearCount.setVisibility(View.VISIBLE);
            holder.textViewQuantity.setText(String.valueOf(entityCart.getQuantity()));
        } else {
            holder.buttonAdd.setVisibility(View.VISIBLE);
            holder.linearCount.setVisibility(View.GONE);
        }

        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.buttonAdd.setVisibility(View.GONE);
                holder.linearCount.setVisibility(View.VISIBLE);

                EntityCart entityCart = new EntityCart(position, entityProductsList.get(position).getId(), 1);

                dataChange(1, entityCart);
                onRefresh.onCartRefresh();

            }
        });

        holder.textViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EntityCart entityCart = findCardItem(entityProductsList.get(position).getId());

                if (entityCart != null) {

                    if (entityCart.getQuantity() < 20) {

                        entityCart.setQuantity(entityCart.getQuantity() + 1);
                        dataChange(2, entityCart);
                        onRefresh.onCartRefresh();

                    } else if (entityCart.getQuantity() == 20) {

                        Toast.makeText(context, "No more Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.textViewMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EntityCart entityCart = findCardItem(entityProductsList.get(position).getId());

                if (entityCart != null) {

                    if (entityCart.getQuantity() > 1) {

                        entityCart.setQuantity(entityCart.getQuantity() - 1);
                        dataChange(2, entityCart);
                        onRefresh.onCartRefresh();

                    } else if (entityCart.getQuantity() == 1) {

                        dataChange(3, entityCart);
                        if (isCart) {

                            entityProductsList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, entityProductsList.size());
                        }
                        onRefresh.onCartRefresh();
                    }
                }
            }
        });
    }

    private EntityCart findCardItem(int productId) {
        for (int j = 0; j < entityCartList.size(); j++) {
            if (entityCartList.get(j).getProductID() == productId) {
                return entityCartList.get(j);
            }
        }
        return null;
    }

    private void dataChange(int type, EntityCart entityCart) {

        new CartInsertAsyncTask(type, context, entityCart, new InterfaceCart() {

            @Override
            public void setCart(List<EntityCart> entityCartListUpdated, List<EntityProducts> entityProductsList) {

                entityCartList = entityCartListUpdated;
                notifyDataSetChanged();
            }
        }).execute();
    }
}
