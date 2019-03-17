package com.lxk.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxk.o2o.dto.ImageHolder;
import com.lxk.o2o.dto.ShopExecution;
import com.lxk.o2o.entity.Area;
import com.lxk.o2o.entity.PersonInfo;
import com.lxk.o2o.entity.Shop;
import com.lxk.o2o.entity.ShopCategory;
import com.lxk.o2o.enums.ShopStateEnum;
import com.lxk.o2o.service.AreaService;
import com.lxk.o2o.service.ShopCategoryService;
import com.lxk.o2o.service.ShopService;
import com.lxk.o2o.util.CodeUtil;
import com.lxk.o2o.util.HttpServletRequestUtil;
import com.lxk.o2o.util.ImageUtil;
import com.lxk.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	/**
	 * 店铺管理相关页面
	 */
	@RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request,"shopId");
		if (shopId<=0){
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null){
				modelMap.put("redirect",true);
				modelMap.put("url","/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect",false);
				modelMap.put("shopId",currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop",currentShop);
			modelMap.put("redirect",false);
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopList(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1l);
		user.setName("LXK");
		request.getSession().setAttribute("user",user);
		user = (PersonInfo) request.getSession().getAttribute("user");
		try{
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList",se.getShopList());
			modelMap.put("user",user);
			modelMap.put("success",true);
		}catch (Exception e){
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopById(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request,"shopId");
		if (shopId>-1){
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop",shop);
				modelMap.put("areaList",areaList);
				modelMap.put("success",true);
			}catch (Exception e){
				modelMap.put("success",false);
				modelMap.put("errMsg",e.toString());
			}

		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","empty shopId");
		}
		return modelMap;

	}

	@RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopInitInfo(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try{
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList",shopCategoryList);
			modelMap.put("areaList",areaList);
			modelMap.put("success",true);
		}catch (Exception e){
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)){
		    modelMap.put("success",false);
		    modelMap.put("errMsg","错误的验证码");
		    return modelMap;
        }
		/**
		 * 1.接收并转化相应的参数，包括店铺信息以及图片信息
		 */
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		//jackson-databind
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//接收图片
		CommonsMultipartFile shopImg = null;
		//文件上传解析器 CommonsMultipartResolver
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//判断request里面是否有上传的文件流
		if (commonsMultipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		/**
		 * 2.注册店铺
		 */
		//判断shop shopImg是否为空
		if(shop != null && shopImg != null){
			//PersonInfo owner = new PersonInfo();
			//通过session获取登录后的信息
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			//owner.setUserId(1L);
			shop.setOwner(owner);
			/*File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
			try {
				shopImgFile.createNewFile();
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			try {
				inputStreamToFile(shopImg.getInputStream(),shopImgFile);
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}*/
			ShopExecution se;
			try {
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				se = shopService.addShop(shop,imageHolder);
				if (se.getState() == ShopStateEnum.CHECK.getState()){
					modelMap.put("success",true);
					//用户和店铺是一对多，在session保存一个店铺列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList==null||shopList.size()==0){
						shopList = new ArrayList<Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList",shopList);
				}else{
					modelMap.put("success",false);
					modelMap.put("errMsg",se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}


			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}

	}

	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success",false);
			modelMap.put("errMsg","错误的验证码");
			return modelMap;
		}
		/**
		 * 1.接收并转化相应的参数，包括店铺信息以及图片信息
		 */
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		//jackson-databind
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//接收图片
		CommonsMultipartFile shopImg = null;
		//文件上传解析器 CommonsMultipartResolver
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//判断request里面是否有上传的文件流
		if (commonsMultipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		/**
		 * 2.修改店铺信息
		 */
		//判断shop shopImg是否为空
		if(shop != null && shop.getShopId()!=null){
			ShopExecution se;
			ShopExecution shopExecution = null;
			try {
				if (shopImg==null){
					se=shopService.modifyShop(shop,null);
				}else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
					se = shopService.modifyShop(shop,imageHolder);
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()){
					modelMap.put("success",true);
				}else{
					modelMap.put("success",false);
					modelMap.put("errMsg",se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}

	}

	/*private static void inputStreamToFile(InputStream inputStream, File file){
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			int bytesRead = 0;
			//读inputstream里面内容
			byte[] buffer = new byte[1024];
			while ((bytesRead=inputStream.read(buffer))!=-1){
				outputStream.write(buffer,0,bytesRead);
			}
		}catch (Exception e){
			throw new RuntimeException("调用inputStreamToFIle产生异常"+e.getMessage());
		}finally {
			try{
				if (outputStream!=null){
					outputStream.close();
				}
				if (inputStream!=null){
					inputStream.close();
				}
			}catch (IOException e){
				throw new RuntimeException("关闭io异常"+e.getMessage());
			}
		}
	}*/
}
