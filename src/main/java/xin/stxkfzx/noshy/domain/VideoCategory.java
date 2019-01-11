package xin.stxkfzx.noshy.domain;

import java.io.Serializable;
import java.util.List;

public class VideoCategory implements Serializable {
    private static final long serialVersionUID = 6663671532107877503L;
    private Long categoryId;

    private String categoryName;

    private Long aliyunId;

    /**
     * -1 表示一级分类
     */
    private Long parentId;

    /**
     * 直接子类分类列表
     */
    private List<VideoCategory> childrenCategory;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Long getAliyunId() {
        return aliyunId;
    }

    public void setAliyunId(Long aliyunId) {
        this.aliyunId = aliyunId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<VideoCategory> getChildrenCategory() {
        return childrenCategory;
    }

    public void setChildrenCategory(List<VideoCategory> childrenCategory) {
        this.childrenCategory = childrenCategory;
    }

    @Override
    public String toString() {
        return "VideoCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", aliyunId=" + aliyunId +
                ", parentId=" + parentId +
                ", childrenCategory=" + childrenCategory +
                '}';
    }
}