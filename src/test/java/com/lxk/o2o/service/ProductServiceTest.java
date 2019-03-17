package com.lxk.o2o.service;

import com.lxk.o2o.BaseTest;
import com.lxk.o2o.entity.Product;
import com.lxk.o2o.entity.ProductCategory;
import com.lxk.o2o.entity.Shop;
import com.lxk.o2o.enums.ProductStateEnum;
import com.lxk.o2o.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct()throws ShopOperationException, FileNotFoundException{
        //创建shopId=1且productId=1的商品实例
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试1");
        product.setProductDesc("测试1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //创建缩略图文件流
        File thumbnailFile = new File("")
    }
}
