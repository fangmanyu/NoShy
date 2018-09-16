package xin.stxkfzx.noshy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "帖子消息")
public class PostInformation {
    @ApiModelProperty(hidden = true)
    private Integer infoId;

    @ApiModelProperty(value = "信息内容", required = true)
    @NotNull(message = "消息内容不能为空")
    private String infoContent;

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "图片消息地址")
    private String imageUrl;

    @NotNull
    @Min(0)
    @ApiModelProperty(value = "所属帖子Id", required = true)
    private Integer postId;

    @Override
    public String toString() {
        return "PostInformation{" +
                "infoId=" + infoId +
                ", infoContent='" + infoContent + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", imageUrl='" + imageUrl + '\'' +
                ", postId=" + postId +
                '}';
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent == null ? null : infoContent.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

}