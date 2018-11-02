package xin.stxkfzx.noshy.vo.video;

/**
 * 视频 凭证
 *
 * @author fmy
 * @date 2018-09-30 12:56
 */
public class AuthVo {

    private String videoId;
    private String uploadAuth;
    private String uploadAddress;

    @Override
    public String toString() {
        return "AuthVo{" +
                "videoId='" + videoId + '\'' +
                ", uploadAuth='" + uploadAuth + '\'' +
                ", uploadAddress='" + uploadAddress + '\'' +
                '}';
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUploadAuth() {
        return uploadAuth;
    }

    public void setUploadAuth(String uploadAuth) {
        this.uploadAuth = uploadAuth;
    }

    public String getUploadAddress() {
        return uploadAddress;
    }

    public void setUploadAddress(String uploadAddress) {
        this.uploadAddress = uploadAddress;
    }
}
