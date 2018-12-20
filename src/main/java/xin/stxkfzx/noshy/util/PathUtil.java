package xin.stxkfzx.noshy.util;

import org.apache.commons.lang3.StringUtils;

public class PathUtil {
    private static final String DEFAULT_PATH_WIN = "D:/doc/java/intellij/NoShy/images";
    private static final String DEFAULT_PATH_OTHER = "/usr/local/spring-boot-product/noshy/images";
    public static final String DOMAIN = "119.23.208.165:8080";
    private static String separator = System.getProperty("file.separator");

    private PathUtil() {
    }

    /**
     * 获取存放图片目录的绝对路径
     *
     * @return
     */
    public static String getImageAbsolutePath() {
        // 获取系统名称
        String os = System.getProperty("os.name");
        String basePath;

        if (os.toLowerCase().startsWith("win")) {
            basePath = DEFAULT_PATH_WIN;
        } else {
            basePath = DEFAULT_PATH_OTHER;
        }

        basePath = basePath.replace("/", separator);

        return basePath;
    }

    /**
     * 获取图片在服务器的相对路径
     *
     * @param id
     * @param type
     * @return
     */
    public static String getImageBasePath(String  id, int type) {
        String imagePath = "/upload/" + type + "/" + id + "/";
        return imagePath.replace("/", separator);
    }

    /**
     * 获取视频封面的URL
     * @param videoId
     * @return
     */
    public static String getImageUrl(String videoId) {
        if (StringUtils.isEmpty(videoId)) {

        return null;
        }

        return DOMAIN + getImageBasePath(videoId, 1);
    }

}
