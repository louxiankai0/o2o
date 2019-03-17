package com.lxk.o2o.util;

import com.lxk.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil{
    //通过线程获取绝对路径
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //设置时间格式
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //随机数对象
    private static final Random r = new Random();


    //处理用户传送过来的文件 CommonsMultipartFile：Spring自带的文件处理对象
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        //系统随机生成的不重名的文件名
        String realFileName = getRandomFileName();
        //获取文件名后缀
        String extension = getFileExtension(thumbnail.getImageName());
        //创建路径目录
        makeDirPath(targetAddr);
        //获取相对路径
        String relativeAddr = targetAddr+realFileName+extension;
        //生成文件路径
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        //创建缩略图
        try{
            //解决绝对路径里面的中文乱码
            String basePathCH = URLDecoder.decode(basePath,"UTF-8");
            Thumbnails.of(thumbnail.getImage()).size(200,200)
            .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePathCH+"/watermark.jpg")),0.25f)
            .outputQuality(0.8f).toFile(dest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 处理详情图，并返回新生成图片的相对路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr){
        //系统随机生成的不重名的文件名
        String realFileName = getRandomFileName();
        //获取文件名后缀
        String extension = getFileExtension(thumbnail.getImageName());
        //创建路径目录
        makeDirPath(targetAddr);
        //获取相对路径
        String relativeAddr = targetAddr+realFileName+extension;
        //生成文件路径
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        //创建缩略图
        try{
            //解决绝对路径里面的中文乱码
            String basePathCH = URLDecoder.decode(basePath,"UTF-8");
            Thumbnails.of(thumbnail.getImage()).size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePathCH+"/watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/Users/o2o/image/xxx.jpg
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdir();
        }
    }

    /**
     * 获取输入文件名的扩展名(获取.后面的字符
     * @param fileName
     * @return
     */
    private static String getFileExtension(String  fileName) {
        //.之后的字符通过substring获取
        return fileName.substring(fileName.indexOf("."));
    }

    /**
     * 生成随机名，当前时间年月日时分秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        //获取随机五位数 +10000保证是五位数
        int rannum = r.nextInt(8999)+10000;
        //当前时间
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr+rannum;
    }

    public static void main(String[] args) throws Exception{
        //通过线程获取绝对路径
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //解决绝对路径里面的中文乱码
        String basePathCH = URLDecoder.decode(basePath,"UTF-8");
        System.out.println(basePathCH);
        //详细查看thumbnailator API文档，https://github.com/coobird/thumbnailator/wiki/Examples
        Thumbnails.of(new File(basePathCH+"/xiaohuangren.jpeg"))
                .size(160,160).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("/Volumes/资料/SSM校园商铺/o2o/src/main/resources/watermark.jpg")),0.25f).outputQuality(0.8f)
                .toFile("/Volumes/资料/SSM校园商铺/o2o/src/main/resources/xiaohuangrenw.jpeg");
    }

    /**
     * storePath是文件路径还是目录路径，
     * 如果是文件路径则删除该文件，
     * 如果是目录路径则删除该文件目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        //如果存在
        if (fileOrPath.exists()){
            //如果是目录
            if (fileOrPath.isDirectory()){
                //将下面文件list出来递归删除
                File files[] = fileOrPath.listFiles();
                for (int i = 0;i<files.length;i++){
                    files[i].delete();
                }
            }
            //如果是文件则直接删除
            fileOrPath.delete();
        }
    }
}
