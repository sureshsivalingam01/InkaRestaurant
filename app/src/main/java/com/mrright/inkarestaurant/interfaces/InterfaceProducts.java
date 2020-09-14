package com.mrright.inkarestaurant.interfaces;

import com.mrright.inkarestaurant.model.EntityCart;
import com.mrright.inkarestaurant.model.EntityProducts;

import java.util.List;

public interface InterfaceProducts {

    void setProductList(List<EntityProducts> entityProductsList,List<EntityCart> entityCartList);

}
