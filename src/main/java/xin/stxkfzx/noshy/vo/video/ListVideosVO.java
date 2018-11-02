package xin.stxkfzx.noshy.vo.video;

/**
 * 视频列表参数
 *
 * @author fmy
 * @date 2018-09-23 23:25
 */
public class ListVideosVO {

    private String search;

    private Integer videoCategory;

    @Override
    public String toString() {
        return "ListVideosVO{" +
                "search='" + search + '\'' +
                ", videoCategory=" + videoCategory +
                '}';
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(Integer videoCategory) {
        this.videoCategory = videoCategory;
    }
}
