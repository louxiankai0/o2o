package com.lxk.o2o.service;

import com.lxk.o2o.dto.ImageHolder;
import com.lxk.o2o.dto.ShopExecution;
import com.lxk.o2o.entity.Shop;
import com.lxk.o2o.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;


public interface ShopService {
	/**
	 * 根据shopCondition分页返回相应店铺列表
	 * @param shopCondition
	 * @param pageIndex 前端只认页数，Dao层只认行数，所以编写一个工具类
	 * @param pageSize
	 * @return
	 */
	//用ShopExecution是因为想把ShopExecution内的shopList和count整合在一起
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

	/**
	 * 通过店铺id获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);

	/**
	 * 更新店铺信息，包括对图片的处理
	 * @param shop
	 * @param thumbnail
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;


	/**
	 * 创建商铺
	 * @param shop
	 * @param thumbnail
	 * @return
	 */
	//ShopExecution addShop(Shop shop, File shopImg);
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

}
