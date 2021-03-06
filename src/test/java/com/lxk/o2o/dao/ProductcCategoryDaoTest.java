package com.lxk.o2o.dao;

import com.lxk.o2o.BaseTest;
import com.lxk.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProductcCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testQueryByShopId() throws Exception{
        long shopId = 1L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("该店铺自定义类别数为:"+productCategoryList.size());

    }

    @Test
    public void testAInsertProductCategory() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setProductCategoryDesc("测试商品类别");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setLastEditTime(new Date());
        productCategory.setShopId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setProductCategoryDesc("测试商品类别2");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setLastEditTime(new Date());
        productCategory2.setShopId(1L);
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
    }


    @Test
    public void testCDeleteProductCategory() throws Exception {
        long shopId = 1;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory pc:productCategoryList){
            if ("12".equals(pc.getProductCategoryName())||"11".equals(pc.getProductCategoryName())){
                int i = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
                assertEquals(1,i);
            }
        }
    }
}
