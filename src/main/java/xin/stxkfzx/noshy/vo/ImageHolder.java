package xin.stxkfzx.noshy.vo;

import java.io.File;
import java.io.InputStream;

/**
 * 封装处理图片的信息
 */
public class ImageHolder {
    private String imageName;
    private InputStream image;
    private File imageFile;

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ImageHolder{" +
                "imageName='" + imageName + '\'' +
                ", image=" + image +
                '}';
    }
}
