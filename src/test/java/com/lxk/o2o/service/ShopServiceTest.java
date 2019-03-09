package com.lxk.o2o.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.lxk.o2o.BaseTest;
import com.lxk.o2o.dto.ShopExecution;
import com.lxk.o2o.entity.Area;
import com.lxk.o2o.entity.PersonInfo;
import com.lxk.o2o.entity.Shop;
import com.lxk.o2o.entity.ShopCategory;
import com.lxk.o2o.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import static org.junit.Assert.assertEquals;


public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	public void testAddShop() throws Exception {
		Shop shop = new Shop();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		Area area = new Area();
		area.setAreaId(1);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shop.setOwner(personInfo);
		shop.setShopName("综合教学楼A");
		shop.setShopDesc("数据线");
		shop.setShopAddr("六食堂前");
		shop.setPhone("13810544444");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		shop.setArea(area);
		shop.setShopCategory(sc);
		File shopImg = new File("/Volumes/LXK/SSM_Shop/o2o/src/main/resources/xiaohuangren.jpeg");
		InputStream inputStream = new FileInputStream(shopImg);
		ShopExecution se = shopService.addShop(shop,inputStream,shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}


	/*@Test
	public void testGetByEmployeeId() throws Exception {
		long employeeId = 1;
		ShopExecution shopExecution = shopService.getByEmployeeId(employeeId);
		List<Shop> shopList = shopExecution.getShopList();
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}

	@Ignore
	@Test
	public void testGetByShopId() throws Exception {
		long shopId = 1;
		Shop shop = shopService.getByShopId(shopId);
		System.out.println(shop);
	}*/

}
