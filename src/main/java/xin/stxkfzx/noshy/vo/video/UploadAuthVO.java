package xin.stxkfzx.noshy.vo.video;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * createUpload 参数
 *
 * @author fmy
 * @date 2018-09-29 20:31
 */
public class UploadAuthVO {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotNull
    private Long videoCategory;
    @NotEmpty
    private String name;

    @Override
    public String toString() {
        return "UploadAuthVO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", videoCategory='" + videoCategory + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(Long videoCategory) {
        this.videoCategory = videoCategory;
    }
}
