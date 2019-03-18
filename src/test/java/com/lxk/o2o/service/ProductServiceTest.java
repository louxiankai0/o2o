package com.lxk.o2o.service;

import com.lxk.o2o.BaseTest;
import com.lxk.o2o.dto.ImageHolder;
import com.lxk.o2o.dto.ProductExecution;
import com.lxk.o2o.entity.Product;
import com.lxk.o2o.entity.ProductCategory;
import com.lxk.o2o.entity.Shop;
import com.lxk.o2o.enums.ProductStateEnum;
import com.lxk.o2o.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        File thumbnailFile = new File("/Volumes/LXK/SSM_Shop/o2o/src/main/resources/xiaohuangren.jpeg");
        InputStream inputStream = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),inputStream);
        //创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1 = new File("/Volumes/LXK/SSM_Shop/o2o/src/main/resources/xiaohuangren.jpeg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("/Volumes/LXK/SSM_Shop/o2o/src/main/resources/dabai.jpeg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));
        //添加商品并验证
        ProductExecution pe = productService.addProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());

    }
}
