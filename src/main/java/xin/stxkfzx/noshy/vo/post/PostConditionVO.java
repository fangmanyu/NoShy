package xin.stxkfzx.noshy.vo.post;

/**
 * 帖子列表参数封装
 *
 * @author fmy
 * @date 2018-09-26 11:13
 */
public class PostConditionVO {
    private Integer postCategory;
    private String search;

    @Override
    public String toString() {
        return "PostConditionVO{" +
                "postCategory=" + postCategory +
                ", search='" + search + '\'' +
                '}';
    }

    public Integer getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(Integer postCategory) {
        this.postCategory = postCategory;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
