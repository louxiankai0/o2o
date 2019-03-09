package com.lxk.o2o.service;

import java.io.IOException;
import java.util.List;

import com.lxk.o2o.entity.ShopCategory;

public interface ShopCategoryService {

	/**
	 * 
	 * @param shopCategoryCondition
	 * @return
	 * @throws IOException
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}
