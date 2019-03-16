package com.lxk.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lxk.o2o.BaseTest;
import com.lxk.o2o.entity.Area;
import com.lxk.o2o.entity.PersonInfo;
import com.lxk.o2o.entity.Shop;
import com.lxk.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	
	@Autowired
	private ShopDao shopDao;

	@Test
	public void testQueryShopListAndCount(){
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 3);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表大小:"+shopList.size());
		System.out.println("店铺总数:"+count);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		shopList = shopDao.queryShopList(shopCondition, 0, 2);
		count = shopDao.queryShopCount(shopCondition);
		System.out.println(shopList.size());
		System.out.println(count);


	}

	@Test
	public void testQueryByShopId(){
		long shopId = 7;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("areaId:"+shop.getArea().getAreaId());
		System.out.println("areaName:"+shop.getArea().getAreaName());
	}
	
	@Test
	public void testinsertShop() {
		Shop shop = new Shop();
		PersonInfo personInfo = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		personInfo.setUserId(1L);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(personInfo);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("咖啡店铺");
		shop.setShopDesc("卖咖啡");
		shop.setShopAddr("成都大学");
		shop.setPhone("13900000000");
		shop.setShopImg("test");
		shop.setPriority(1);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int e = shopDao.insertShop(shop);
		assertEquals(1, e);
	}

	@Test
	public void testupdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("LXK卖咖啡");
		shop.setShopAddr("成都大学信工学院");
		shop.setLastEditTime(new Date());
		int e = shopDao.updateShop(shop);
		assertEquals(1, e);
	}

	
}
