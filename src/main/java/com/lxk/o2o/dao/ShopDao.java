package com.lxk.o2o.dao;

import com.lxk.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 通过shopid查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(Long shopId);

	//新增店铺。插入shop信息，返回1则成功
	int insertShop(Shop shop);

	/**
	 * 更新店铺信息
	 */
	int updateShop(Shop shop);
}
