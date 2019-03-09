package com.lxk.o2o.service;

import com.lxk.o2o.dto.ShopExecution;
import com.lxk.o2o.entity.Shop;
import com.lxk.o2o.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;


public interface ShopService {


	/**
	 * 创建商铺
	 * @param shop
	 * @param shopImgInputStream
	 * @return
	 */
	//ShopExecution addShop(Shop shop, File shopImg);
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;

}
