package com.lxk.o2o.service;

import com.lxk.o2o.dto.ProductCategoryExecution;
import com.lxk.o2o.entity.ProductCategory;
import com.lxk.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 查询指定某个店铺下的所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    ProductCategoryExecution batchAddProductCategory(
            List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;


    /**
     * 将此类别下的商品里面的类别id置为空，再删除该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;
}
