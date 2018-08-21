package xin.stxkfzx.noshy.domain;

public class VideoCategory {
    private Long categoryId;

    private String categoryName;

    private Long aliyunId;

    /**
     * -1 表示一级分类
     */
    private Long parentId;

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

    @Override
    public String toString() {
        return "VideoCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", aliyunId=" + aliyunId +
                ", parentId=" + parentId +
                '}';
    }
}