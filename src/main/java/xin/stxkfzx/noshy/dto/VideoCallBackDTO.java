package xin.stxkfzx.noshy.dto;

/**
 * 视频回调返回参数封装
 *
 * @author fmy
 * @date 2018-07-26 0:10
 */
public class VideoCallBackDTO {
    private String eventTime;
    private String eventType;
    private String videoId;
    private String status;


    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VideoCallBackDTO{" +
                "eventTime='" + eventTime + '\'' +
                ", eventType='" + eventType + '\'' +
                ", videoId='" + videoId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
