package com.lxk.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.lxk.o2o.dto.ImageHolder;
import com.lxk.o2o.dto.ProductExecution;
import com.lxk.o2o.entity.Product;
import com.lxk.o2o.exceptions.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public interface ProductService {

	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

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

	ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
                                   List<CommonsMultipartFile> productImgs) throws RuntimeException;
}
