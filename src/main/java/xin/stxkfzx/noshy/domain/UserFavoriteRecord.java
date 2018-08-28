package xin.stxkfzx.noshy.domain;

import java.util.Date;

public class UserFavoriteRecord {
    private Integer favoriteRecordId;

    private Long userId;

    private Integer browseId;

    private Date favoriteRecordTime;

    public Integer getFavoriteRecordId() {
        return favoriteRecordId;
    }

    public void setFavoriteRecordId(Integer favoriteRecordId) {
        this.favoriteRecordId = favoriteRecordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBrowseId() {
        return browseId;
    }

    public void setBrowseId(Integer browseId) {
        this.browseId = browseId;
    }

    public Date getFavoriteRecordTime() {
        return favoriteRecordTime;
    }

    public void setFavoriteRecordTime(Date favoriteRecordTime) {
        this.favoriteRecordTime = favoriteRecordTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", favoriteRecordId=").append(favoriteRecordId);
        sb.append(", userId=").append(userId);
        sb.append(", browseId=").append(browseId);
        sb.append(", favoriteRecordTime=").append(favoriteRecordTime);
        sb.append("]");
        return sb.toString();
    }
}