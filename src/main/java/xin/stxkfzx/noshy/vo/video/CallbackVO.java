package xin.stxkfzx.noshy.vo.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 视频回调参数
 *
 * @author 59261
 * @date 2018-10-03 13:59
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackVO {

    @JsonProperty(value = "Status")
    private String Status;
    @JsonProperty(value = "EventType")
    private String EventType;
    @JsonProperty(value = "VideoId")
    private String VideoId;
    @JsonProperty(value = "FileUrl")
    private String FileUrl;

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.FileUrl = fileUrl;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        this.EventType = eventType;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        this.VideoId = videoId;
    }

    @Override
    public String toString() {
        return "CallbackVO{" +
                "Status='" + Status + '\'' +
                ", EventType='" + EventType + '\'' +
                ", VideoId='" + VideoId + '\'' +
                ", FileUrl='" + FileUrl + '\'' +
                '}';
    }
}
