package xin.stxkfzx.noshy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel(description = "视频标签")
public class VideoTag {

    @ApiModelProperty(hidden = true)
    private Integer tagId;

    @ApiModelProperty(value = "标签名", required = true)
    private String name;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String videoId;

    public VideoTag() {
    }

    public VideoTag(String name) {
        this.name = name;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    @Override
    public String toString() {
        return "VideoTag{" +
                "tagId=" + tagId +
                ", name='" + name + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }
}