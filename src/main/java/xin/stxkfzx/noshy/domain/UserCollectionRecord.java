package xin.stxkfzx.noshy.domain;

import java.util.Date;

public class UserCollectionRecord {
    private Integer collectionRecordId;

    private Long userId;

    private Integer browseId;

    private Date collectionRecordTime;

    public Integer getCollectionRecordId() {
        return collectionRecordId;
    }

    public void setCollectionRecordId(Integer collectionRecordId) {
        this.collectionRecordId = collectionRecordId;
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

    public Date getCollectionRecordTime() {
        return collectionRecordTime;
    }

    public void setCollectionRecordTime(Date collectionRecordTime) {
        this.collectionRecordTime = collectionRecordTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", collectionRecordId=").append(collectionRecordId);
        sb.append(", userId=").append(userId);
        sb.append(", browseId=").append(browseId);
        sb.append(", collectionRecordTime=").append(collectionRecordTime);
        sb.append("]");
        return sb.toString();
    }
}