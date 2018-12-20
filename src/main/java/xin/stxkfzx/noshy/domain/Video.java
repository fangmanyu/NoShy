package xin.stxkfzx.noshy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApiModel
public class Video {
    /** Uploading（上传中） */
    public static final String UPLOADING = "Uploading";
    /** UploadSucc（上传完成） */
    public static final String UPLOADSUCC = "UploadSucc";
    /** Transcoding（转码中） */
    public static final String TRANSCODING = "Transcoding";
    /** TranscodeFail（转码失败） */
    public static final String TRANSCODEFAIL = "TranscodeFail";
    /** Checking（审核中） */
    public static final String CHECKING = "Checking";
    /** Blocked（屏蔽） */
    public static final String BLOCKED = "Blocked";
    /** NORMAL（正常） */
    public static final String NORMAL = "Normal";

    @ApiModelProperty(hidden = true)
    private String videoId;

    @ApiModelProperty(value = "视频标题", example = "视频标题")
    private String title;

    @JsonIgnore
    @ApiModelProperty(value = "视频名称,包括后缀名", hidden = true)
    private String name;

    @JsonIgnore
    @ApiModelProperty(value = "视频上传流", hidden = true)
    private transient InputStream videoInputStream;

    @ApiModelProperty(value = "视频分类")
    private Long videoCategory;

    @ApiModelProperty(value = "视频标签", notes = "单个标签不超过32字节,最多16个标签")
    private transient List<VideoTag> tags;
    @ApiModelProperty(value = "视频描述")
    private String description;
    @ApiModelProperty(value = "视频封面图片地址")
    private String imageUrl;

    @ApiModelProperty(hidden = true)
    private String status;
    @ApiModelProperty(hidden = true)
    private Date createTime;
    @ApiModelProperty(hidden = true)
    private Date lastEditTime;
    @ApiModelProperty(hidden = true)
    private Long userId;
    @ApiModelProperty(hidden = true)
    private String playUrl;
    @JsonIgnore
    private Integer browseId;


    public Video() {}

    public Video(String title, String name, InputStream videoInputStream) {
        this.title = title;
        this.name = name;
        this.videoInputStream = videoInputStream;
    }

    public Video(String title, File videoFile) {

        InputStream videoInputStream = null;
        try {
            videoInputStream = new FileInputStream(videoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.title = title;
        this.name = videoFile.getName();
        this.videoInputStream = videoInputStream;
    }

    public Integer getBrowseId() {
        return browseId;
    }

    public void setBrowseId(Integer browseId) {
        this.browseId = browseId;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(Long videoCategory) {
        this.videoCategory = videoCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public InputStream getVideoInputStream() {
        return videoInputStream;
    }

    public void setVideoInputStream(InputStream videoInputStream) {
        this.videoInputStream = videoInputStream;
    }


    public List<VideoTag> getTags() {
        return tags;
    }

    public void setTags(List<VideoTag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", videoInputStream=" + videoInputStream +
                ", videoCategory=" + videoCategory +
                ", tags=" + tags +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", userId=" + userId +
                ", playUrl='" + playUrl + '\'' +
                ", browseId=" + browseId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Video video = (Video) o;
        return Objects.equals(videoId, video.videoId) &&
                Objects.equals(title, video.title) &&
                Objects.equals(name, video.name) &&
                Objects.equals(videoInputStream, video.videoInputStream) &&
                Objects.equals(videoCategory, video.videoCategory) &&
                Objects.equals(tags, video.tags) &&
                Objects.equals(description, video.description) &&
                Objects.equals(imageUrl, video.imageUrl) &&
                Objects.equals(status, video.status) &&
                Objects.equals(createTime, video.createTime) &&
                Objects.equals(lastEditTime, video.lastEditTime) &&
                Objects.equals(userId, video.userId) &&
                Objects.equals(playUrl, video.playUrl) &&
                Objects.equals(browseId, video.browseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, title, name, videoInputStream, videoCategory, tags, description, imageUrl, status, createTime, lastEditTime, userId, playUrl, browseId);
    }
}