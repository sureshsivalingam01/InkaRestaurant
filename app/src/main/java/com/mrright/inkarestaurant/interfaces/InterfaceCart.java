package com.mrright.inkarestaurant.interfaces;

import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;

import java.util.List;

public interface InterfaceCart {

    void setCart (List<EntityCart> entityCartList, List<EntityProducts> entityProductsList);


}
