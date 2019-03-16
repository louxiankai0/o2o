package com.lxk.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin", method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping(value = "/shopoperation")
    //转发
    public String shopOperation() {
        return "shop/shopoperation";//在spring-web.xml里面设置了后缀
    }

    @RequestMapping(value = "/shoplist")
    //转发
    public String shopList() {
        return "shop/shoplist";//在spring-web.xml里面设置了后缀
    }

    @RequestMapping(value = "/shopmanagement")
    //转发
    public String shopManagement() {
        return "shop/shopmanagement";//在spring-web.xml里面设置了后缀
    }

    @RequestMapping(value = "/productcategorymanage",method = RequestMethod.GET)
    //转发
    public String productCategoryManage() {
        return "shop/productcategorymanage";//在spring-web.xml里面设置了后缀
    }

}
