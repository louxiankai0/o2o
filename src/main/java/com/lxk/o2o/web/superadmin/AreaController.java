package com.lxk.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lxk.o2o.entity.Area;
import com.lxk.o2o.service.AreaService;



@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;
	@RequestMapping(value="/listarea",method=RequestMethod.GET)
	@ResponseBody  //modelMap自动转换为json格式返回前端
	private Map<String, Object> listArea(){
		logger.info("====start====");
		long startTime = System.currentTimeMillis();//获取当前时间毫秒数
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> areaList = new ArrayList<Area>();
		areaList=areaService.getAreaList();
		modelMap.put("rows", areaList);
		modelMap.put("total", areaList.size());
		logger.error("test error!");
		long endTime = System.currentTimeMillis();//获取当前时间毫秒数
		logger.debug("costTime:[{}ms]",endTime-startTime);
		logger.info("====end====");
		return modelMap;
	}
	
}
