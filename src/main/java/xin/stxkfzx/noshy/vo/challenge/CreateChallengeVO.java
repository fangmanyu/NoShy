package xin.stxkfzx.noshy.vo.challenge;

import org.springframework.web.multipart.MultipartFile;
import xin.stxkfzx.noshy.vo.CreateVideoVO;

import javax.validation.constraints.NotEmpty;

/**
 * 创建挑战参数封装
 *
 * @author fmy
 * @date 2018-09-18 9:55
 */
public class CreateChallengeVO {
    @NotEmpty
    private String challengeName;
    @NotEmpty
    private String challengeDescription;

    private MultipartFile challengeImage;

    private String joinChallengeName;

    private CreateVideoVO videoInfo;

    @Override
    public String toString() {
        return "CreateChallengeVO{" +
                "challengeName='" + challengeName + '\'' +
                ", challengeDescription='" + challengeDescription + '\'' +
                ", challengeImage=" + challengeImage +
                ", joinChallengeName='" + joinChallengeName + '\'' +
                ", videoInfo=" + videoInfo +
                '}';
    }

    public CreateVideoVO getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(CreateVideoVO videoInfo) {
        this.videoInfo = videoInfo;
    }

    public MultipartFile getChallengeImage() {
        return challengeImage;
    }

    public void setChallengeImage(MultipartFile challengeImage) {
        this.challengeImage = challengeImage;
    }

    public String getJoinChallengeName() {
        return joinChallengeName;
    }

    public void setJoinChallengeName(String joinChallengeName) {
        this.joinChallengeName = joinChallengeName;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeDescription() {
        return challengeDescription;
    }

    public void setChallengeDescription(String challengeDescription) {
        this.challengeDescription = challengeDescription;
    }
}
