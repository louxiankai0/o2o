package com.lxk.o2o.dao;

import com.lxk.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

	/**
	 * 分页查询店铺，可输入条件:店铺名(模糊)，店铺状态，店铺类别，区域id，owner
	 * @param shopCondition
	 * @param rowIndex 第几行开始取数据
	 * @param pageSize 返回多少行数据 (例:rowIndex=1,pageSize=5第一行开始取取五行)
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
							 @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);

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
