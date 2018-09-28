package xin.stxkfzx.noshy.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import xin.stxkfzx.noshy.vo.ImageHolder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class ImageUtil {

    private static final Logger log = LogManager.getLogger(ImageUtil.class);

    private static final Random RANDOM = new Random();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    private ImageUtil() {
    }


    /**
     * 生成缩略图,并返回生成缩略图在服务器的相对路径
     *
     * @param thumbnail  缩略图信息
     * @param targetAddr 缩略图所在目录的相对路径
     * @return 生成缩略图在服务器的相对路径
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = "缩略图" + getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

        try {
            Thumbnails.of(thumbnail.getImage())
                    .size(200, 200)
                    .outputQuality(1f)
                    .toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败 " + e.getMessage());
        }

        return relativeAddr;
    }

    /**
     * 创建目标目录
     *
     * @param targetAddr 目标文件相对路径
     */
    private static void makeDirPath(String targetAddr) {
        // 获取绝对路径
        String realFilePath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFilePath);

        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

    }

    /**
     * 获取输入文件的拓展名
     *
     * @return 文件拓展名
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名,当前年月日小时分钟秒数 + 五位随机值
     *
     * @return 文件名
     */
    public static String getRandomFileName() {

        int rannum = RANDOM.nextInt(99999) + 10000;
        String nowTimeStr = DATE_FORMAT.format(new Date());

        return nowTimeStr + rannum;
    }

    /**
     * 删除文件(如果是文件夹则删除该文件夹及文件夹下的所有文件)
     *
     * @param deleteFilePath 要删除文件的路径名(相对路径或绝对路径)
     */
    public static void deleteFile(String deleteFilePath) {
        if (deleteFilePath == null || "".equals(deleteFilePath.trim())) {
            return;
        }

        String path = deleteFilePath.contains(PathUtil.getImgBasePath()) ?
                deleteFilePath : PathUtil.getImgBasePath() + deleteFilePath;

        File file = new File(path);
        deleteFile(file);
        file.deleteOnExit();
    }

    private static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }

        if (file.isFile()) {
            log.debug("delete file: " + file.getName());
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File tempFile :
                        files) {
                    deleteFile(tempFile);
                }
            }
        }

    }

    /**
     * 处理详情图
     *
     * @param productImgList 详情图信息列表
     * @param targetAddr     详情图所在目录的相对路径
     * @return 生成图片的相对路径
     */
    public static List<String> generateNormalImgs(List<ImageHolder> productImgList, String targetAddr, int type) {
        int count = 0;
        List<String> relativeAddrList = new ArrayList<>();

        if (productImgList != null && productImgList.size() > 0) {
            makeDirPath(targetAddr);

            // String typeName = type == ShopImage.INFORMATION_IMAGE ? "详情图" : "轮播图";
            String typeName = "";
            for (ImageHolder productImg :
                    productImgList) {
                String realFileName = typeName + getRandomFileName();
                String fileExtension = getFileExtension(productImg.getImageName());
                String relativeAddr = targetAddr + realFileName + count + fileExtension;
                // 绝对路径
                File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

                count++;

                try {
                    Thumbnails.of(productImg.getImage()).size(600, 300).outputQuality(1f).toFile(dest);
                } catch (IOException e) {
                    throw new RuntimeException("创建" + typeName + "失败： " + e.getMessage());
                }

                relativeAddrList.add(relativeAddr);
            }
        }

        return relativeAddrList;
    }

    public static boolean multipartFileToImageHolder(List<MultipartFile> multipartImageList, List<ImageHolder> imageHolderList) {
        if (multipartImageList == null) {
            return false;
        }

        try {
            for (MultipartFile file :
                    multipartImageList) {
                ImageHolder holder = new ImageHolder(file.getOriginalFilename(), file.getInputStream());
                imageHolderList.add(holder);
            }
        } catch (IOException e) {
            return true;
        }

        return false;
    }
}
