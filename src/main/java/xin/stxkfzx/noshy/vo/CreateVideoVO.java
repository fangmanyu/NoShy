package xin.stxkfzx.noshy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 上传视频参数封装
 *
 * @author fmy
 * @date 2018-09-18 14:19
 */
@ApiModel(description = "上传视频参数")
public class CreateVideoVO {
    @ApiModelProperty(name = "title", value = "视频标题", required = true)
    @NotEmpty
    private String title;
    @NotNull
    @Min(0)
    @ApiModelProperty(name = "videoCategory", value = "视频分类", required = true)
    private Long videoCategory;
    // @NotNull
    // @ApiModelProperty(value = "视频文件流对象", required = true)
    // private MultipartFile videoFile;
    @NotEmpty
    @ApiModelProperty(name = "description", value = "视频描述", required = true)
    private String description;
    @NotEmpty
    @ApiModelProperty(name = "tags", value = "视频标签。最多16个标签，每个标签不能超过5个字，标签之间以英文状态下的逗号(,)隔开")
    private String tags;
    // @NotNull
    // @ApiModelProperty(value = "视频封面图片", required = true)
    // private MultipartFile videoImage;

    @Override
    public String toString() {
        return "CreateVideoVO{" +
                "title='" + title + '\'' +
                ", videoCategory=" + videoCategory +
                // ", videoFile=" + videoFile +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                // ", videoImage=" + videoImage +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(Long videoCategory) {
        this.videoCategory = videoCategory;
    }

    // public MultipartFile getVideoFile() {
    //     return videoFile;
    // }
    //
    // public void setVideoFile(MultipartFile videoFile) {
    //     this.videoFile = videoFile;
    // }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    // public MultipartFile getVideoImage() {
    //     return videoImage;
    // }
    //
    // public void setVideoImage(MultipartFile videoImage) {
    //     this.videoImage = videoImage;
    // }
}
