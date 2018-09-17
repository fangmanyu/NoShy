package xin.stxkfzx.noshy.util;

public class PathUtil {
    private static final String DEFAULT_PATH_WIN = "D:/doc/intellij/Noshy/images";
    private static final String DEFAULT_PATH_OTHER = "/home/fmy/image/";
    private static String separator = System.getProperty("file.separator");

    private PathUtil() {
    }

    /**
     * 获取存放图片目录的绝对路径
     *
     * @return
     */
    public static String getImgBasePath() {
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

}
