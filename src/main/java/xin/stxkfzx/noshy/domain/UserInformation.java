package xin.stxkfzx.noshy.domain;

import java.io.Serializable;

public class UserInformation implements Serializable {
    private static final long serialVersionUID = -786180867400350687L;
    private Long userId;

    private String headPortraitAddr;

    private Integer rank;

    private Integer experience;

    private String introduction;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHeadPortraitAddr() {
        return headPortraitAddr;
    }

    public void setHeadPortraitAddr(String headPortraitAddr) {
        this.headPortraitAddr = headPortraitAddr;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", headPortraitAddr=").append(headPortraitAddr);
        sb.append(", rank=").append(rank);
        sb.append(", experience=").append(experience);
        sb.append(", introduction=").append(introduction);
        sb.append("]");
        return sb.toString();
    }
}