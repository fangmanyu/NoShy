package xin.stxkfzx.noshy.dto;

import xin.stxkfzx.noshy.domain.BrowseInformation;
import xin.stxkfzx.noshy.domain.Video;
import xin.stxkfzx.noshy.domain.VideoCategory;

import java.util.List;

/**
 * 封装Video的DTO类
 *
 * @author fmy
 * @date 2018-07-24 17:36
 */
public class VideoDTO extends BaseDTO{
    private Video video;
    private Integer count;
    private List<Video> videoList;
    private String playUrl;
    private VideoCategory videoCategory;
    private List<VideoCategory> videoCategoryList;
    private String uploadAuth;
    private String uploadAddress;
    private String videoId;
    private BrowseInformation browseInformation;


    public VideoDTO(Boolean success, String message) {
        super(success, message);
    }

    public VideoDTO(Boolean success, String message, Video video) {
        super(success, message);
        this.video = video;
    }

    public VideoDTO(Boolean success, String message, List<Video> videoList) {
        super(success, message);
        this.videoList = videoList;
    }

    @Override
    public String toString() {
        return "VideoDTO{" +
                "video=" + video +
                ", count=" + count +
                ", videoList=" + videoList +
                ", playUrl='" + playUrl + '\'' +
                ", videoCategory=" + videoCategory +
                ", videoCategoryList=" + videoCategoryList +
                ", uploadAuth='" + uploadAuth + '\'' +
                ", uploadAddress='" + uploadAddress + '\'' +
                ", videoId='" + videoId + '\'' +
                "} " + super.toString();
    }

    public List<VideoCategory> getVideoCategoryList() {
        return videoCategoryList;
    }

    public void setVideoCategoryList(List<VideoCategory> videoCategoryList) {
        this.videoCategoryList = videoCategoryList;
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

    public void setUploadAuth(String uploadAuth) {
        this.uploadAuth = uploadAuth;
    }

    public String getUploadAuth() {
        return uploadAuth;
    }

    public void setUploadAddress(String uploadAddress) {
        this.uploadAddress = uploadAddress;
    }

    public String getUploadAddress() {
        return uploadAddress;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setBrowseInformation(BrowseInformation browseInformation) {
        this.browseInformation = browseInformation;
    }

    public BrowseInformation getBrowseInformation() {
        return browseInformation;
    }
}
