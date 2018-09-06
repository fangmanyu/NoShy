package xin.stxkfzx.noshy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 添加评论参数封装
 *
 * @author fmy
 * @date 2018-09-05 17:23
 */
@ApiModel(description = "添加评论参数")
public class AddCommentVO {
    @NotNull
    @ApiModelProperty(value = "浏览信息Id", required = true)
    private Integer browseId;

    @NotEmpty
    @ApiModelProperty(value = "评论内容", required = true)
    private String commentContent;

    @ApiModelProperty(value = "父类Id")
    private Integer parentId;

    @Override
    public String toString() {
        return "AddCommentVO{" +
                "browseId=" + browseId +
                ", commentContent='" + commentContent + '\'' +
                ", parentId=" + parentId +
                '}';
    }

    public Integer getBrowseId() {
        return browseId;
    }

    public void setBrowseId(Integer browseId) {
        this.browseId = browseId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
