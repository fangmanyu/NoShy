package xin.stxkfzx.noshy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 添加浏览记录 VO
 *
 * @author fmy
 * @date 2018-11-01 22:07
 */
@ApiModel
public class AddRecordVO {

    @NotNull
    @Min(0)
    @ApiModelProperty(value = "浏览类型", example = "1(视频), 2(帖子), 3(挑战)")
    private Integer browseType;

    @NotEmpty
    @ApiModelProperty(value = "所属Id", example = "具体的videoId, postId, challengeId")
    private String belongId;

    @NotEmpty
    @ApiModelProperty(value = "添加浏览类型", example = "pageviews(浏览量), likes(点赞量), shares(转发分享)")
    private String typeName;

    public Integer getBrowseType() {
        return browseType;
    }

    public void setBrowseType(Integer browseType) {
        this.browseType = browseType;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
