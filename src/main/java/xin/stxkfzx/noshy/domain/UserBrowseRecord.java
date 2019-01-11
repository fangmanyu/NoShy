package xin.stxkfzx.noshy.domain;

import java.io.Serializable;
import java.util.Date;

public class UserBrowseRecord implements Serializable {
    private static final long serialVersionUID = 4335576242588109817L;
    private Integer browseRecordId;

    private Long userId;

    private Integer browseId;

    private Date browseRecordTime;

    public Integer getBrowseRecordId() {
        return browseRecordId;
    }

    public void setBrowseRecordId(Integer browseRecordId) {
        this.browseRecordId = browseRecordId;
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

    public Date getBrowseRecordTime() {
        return browseRecordTime;
    }

    public void setBrowseRecordTime(Date browseRecordTime) {
        this.browseRecordTime = browseRecordTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", browseRecordId=").append(browseRecordId);
        sb.append(", userId=").append(userId);
        sb.append(", browseId=").append(browseId);
        sb.append(", browseRecordTime=").append(browseRecordTime);
        sb.append("]");
        return sb.toString();
    }
}