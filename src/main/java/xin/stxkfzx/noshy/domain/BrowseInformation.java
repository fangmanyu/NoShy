package xin.stxkfzx.noshy.domain;

import java.io.Serializable;

/**
 * @author fmy
 */
public class BrowseInformation implements Serializable {
    public static final int NONE = 0;
    public static final int VIDEO = 1;
    public static final int POST = 2;
    public static final int CHALLENGE = 3;

    public static final String PAGEVIEWS = "pageviews";
    public static final String LIKES = "likes";
    public static final String SHARES = "shares";
    private static final long serialVersionUID = 2027545917933771641L;

    private Integer browseId;

    /** 浏览量*/
    private Integer pageviews;

    /** 点赞量*/
    private Integer likes;

    /** 转发分享量*/
    private Integer shares;

    /**
     * 浏览类型.
     * 0 不可用;
     * 1 视频;
     * 2 帖子
     */
    private Integer browseType;

    public Integer getBrowseId() {
        return browseId;
    }

    public void setBrowseId(Integer browseId) {
        this.browseId = browseId;
    }

    public Integer getPageviews() {
        return pageviews;
    }

    public void setPageviews(Integer pageviews) {
        this.pageviews = pageviews;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Integer getBrowseType() {
        return browseType;
    }

    public void setBrowseType(Integer browseType) {
        this.browseType = browseType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", browseId=").append(browseId);
        sb.append(", pageviews=").append(pageviews);
        sb.append(", likes=").append(likes);
        sb.append(", shares=").append(shares);
        sb.append(", browseType=").append(browseType);
        sb.append("]");
        return sb.toString();
    }
}