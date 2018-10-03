package xin.stxkfzx.noshy.vo.video;

/**
 * 视频回调参数
 *
 * @author 59261
 * @date 2018-10-03 13:59
 */
public class CallbackVO {

    private String Status;
    private String EventType;
    private String VideoId;
    private String FileUrl;

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }
}
