package com.lxk.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.lxk.o2o.dto.ImageHolder;
import com.lxk.o2o.dto.ProductExecution;
import com.lxk.o2o.entity.Product;
import com.lxk.o2o.exceptions.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public interface ProductService {

	/**
	 * 查询商品列表并分页，可输入条件有：商品名（模糊），商品状态，店铺id，商品类别
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/**
	 * 通过商品id查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);

	/**
	 * 添加商品信息以及图片处理
	 * @param product
	 * @param thumbnail  缩略图
	 * @param productImgHolderList  详情图
	 * @return
	 * @throws ProductOperationException
	 */
	/*ProductExecution addProduct(Product product, InputStream thumbnail, String thumbnailName, List<InputStream> productImgList, List<String> productImgNameList)
			throws ProductOperationException; 进行优化重构，编写一个imageHolder ，来精简传入的参数 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException;

	/**
	 * 修改商品信息以及图片
	 * @param product
	 * @param thumbnail
	 * @param productImgList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgList) throws ProductOperationException;
}
