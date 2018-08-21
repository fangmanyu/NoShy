package xin.stxkfzx.noshy.dto;

import xin.stxkfzx.noshy.domain.Video;
import xin.stxkfzx.noshy.domain.VideoCategory;

import java.util.List;

/**
 * 封装Video的DTO类
 *
 * @author fmy
 * @date 2018-07-24 17:36
 */
public class VideoDTO {
    private Boolean success;
    private String message;
    private Video video;
    private Integer count;
    private List<Video> videoList;
    private String playUrl;
    private VideoCategory videoCategory;

    public VideoDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public VideoDTO(Boolean success, String message, Video video) {
        this.success = success;
        this.message = message;
        this.video = video;
    }

    public VideoDTO(Boolean success, String message, List<Video> videoList) {
        this.success = success;
        this.message = message;
        this.videoList = videoList;
    }

    @Override
    public String toString() {
        return "VideoDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", video=" + video +
                ", count=" + count +
                ", videoList=" + videoList +
                ", playUrl='" + playUrl + '\'' +
                ", videoCategory=" + videoCategory +
                '}';
    }

    public VideoCategory getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(VideoCategory videoCategory) {
        this.videoCategory = videoCategory;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
