package xin.stxkfzx.noshy.vo.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 添加帖子参数封装
 *
 * @author fmy
 * @date 2018-09-16 22:09
 */
@ApiModel(description = "添加帖子参数")
public class AddPostVO {

    @NotNull
    @Range(min = 0, max = 2)
    @ApiModelProperty(value = "权限")
    private Integer authority;

    @NotNull
    @Min(0)
    @ApiModelProperty(value = "帖子分类")
    private Integer postCategory;

    @NotEmpty
    @ApiModelProperty(value = "标题")
    private String title;

    @NotEmpty
    @ApiModelProperty(value = "描述")
    private String description;

    @Override
    public String toString() {
        return "AddPostVO{" +
                ", authority=" + authority +
                ", postCategory=" + postCategory +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public Integer getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(Integer postCategory) {
        this.postCategory = postCategory;
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
}
