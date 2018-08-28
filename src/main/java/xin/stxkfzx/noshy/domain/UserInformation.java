package xin.stxkfzx.noshy.domain;

public class UserInformation {
    private Long userId;

    /** 头像地址*/
    private String headPortraitAddr;

    /** 等级*/
    private Integer rank;

    /** 经验值*/
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