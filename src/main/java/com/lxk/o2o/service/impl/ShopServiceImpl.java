package com.lxk.o2o.service.impl;


import com.lxk.o2o.dao.ShopDao;
import com.lxk.o2o.dto.ShopExecution;
import com.lxk.o2o.entity.Shop;
import com.lxk.o2o.enums.ShopStateEnum;
import com.lxk.o2o.exceptions.ShopOperationException;
import com.lxk.o2o.service.ShopService;
import com.lxk.o2o.util.ImageUtil;
import com.lxk.o2o.util.PageCalculator;
import com.lxk.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if (shopList!=null){
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
		//1.判断是否需要处理图片，需要创建一个util，一旦有新的图片就要吧旧的删除
		if (shop == null || shop.getShopId() == null){
			return new ShopExecution(ShopStateEnum.NULL_SHOPID);
		}else {
			try {
			//判断是否有图片
			if (shopImgInputStream!=null &&fileName!=null&&!"".equals(fileName)){
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if (tempShop.getShopImg()!=null){
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				//为什么不是传入tempshop而是shop，是因为addShopImg里面会对shop进行操作附上一个新的图片地址，进行更新
				addShopImg(shop,shopImgInputStream,fileName);
			}
			//2.更新店铺信息
			shop.setLastEditTime(new Date());
			//接回update返回值
			int effectedNum = shopDao.updateShop(shop);
			if (effectedNum<=0){
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}else {
				shop = shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}}catch (Exception e){
				throw new ShopOperationException("modifyShop eooro:"+e.getMessage());
			}
		}

	}

	@Override
	@Transactional
	/**
	 * 使用注解控制事务方法的优点： 1.开发团队达成一致约定，明确标注事务方法的编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
	 */
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException{
		//检查传入参数是否合法
		if (shop==null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}
		try{
			//初始化商铺
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(shopImgInputStream !=null){
					//存储图片
					try {
						if (shopImgInputStream != null) {
							addShopImg(shop, shopImgInputStream,fileName);
						}
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error: "
								+ e.getMessage());
					}
					//更新店铺图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("创建图片地址失败");
					}

				}
			}


		}catch (Exception e){
			throw  new ShopOperationException("addShop error:"+e.getMessage());
		}


		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
		//获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		//调用方法去存储图片
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName,dest);
		shop.setShopImg(shopImgAddr);

	}
}
