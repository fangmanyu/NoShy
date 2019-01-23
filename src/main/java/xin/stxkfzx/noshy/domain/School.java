package xin.stxkfzx.noshy.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *  用户所属的学校
 *
 * @author fmy
 * @date 2018-07-24 13:46
 */
public class School implements Serializable {
    private static final long serialVersionUID = 5307149136047908044L;
    private Integer schoolId;

    private String schoolName;

    private String schoolDesc;

    private Date createTime;

    private String schoolAddr;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getSchoolDesc() {
        return schoolDesc;
    }

    public void setSchoolDesc(String schoolDesc) {
        this.schoolDesc = schoolDesc == null ? null : schoolDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSchoolAddr() {
        return schoolAddr;
    }

    public void setSchoolAddr(String schoolAddr) {
        this.schoolAddr = schoolAddr == null ? null : schoolAddr.trim();
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", schoolDesc='" + schoolDesc + '\'' +
                ", createTime=" + createTime +
                ", schoolAddr='" + schoolAddr + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        School school = (School) o;
        return Objects.equals(schoolId, school.schoolId) &&
                Objects.equals(schoolName, school.schoolName) &&
                Objects.equals(schoolDesc, school.schoolDesc) &&
                Objects.equals(createTime, school.createTime) &&
                Objects.equals(schoolAddr, school.schoolAddr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolId, schoolName, schoolDesc, createTime, schoolAddr);
    }
}