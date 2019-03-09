package com.lxk.o2o.util;

import com.lxk.o2o.entity.Shop;

/**
 * 1.通过执行环境不同提供不同的根路径(项目图片所需要存放的路径)
 */
public class PathUtil {
    //file.sepatator获取文件分隔符
    private static String separator = System.getProperty("file.separator");
    public static String getImgBasePath(){
        //不同的操作环境选择不同的根目录
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "C:/o2o/image/";
        }else{
            basePath = "/Users/o2o/image/";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }

    //不同业务需求返回项目图片相对子路径
    public static String getShopImagePath(long shopId){
        String imagePath = "upload/item/shop/"+shopId+"/";
        return imagePath.replace("/",separator);
    }

}
