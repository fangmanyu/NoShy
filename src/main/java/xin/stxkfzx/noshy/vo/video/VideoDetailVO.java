package xin.stxkfzx.noshy.vo.video;

import xin.stxkfzx.noshy.domain.BrowseInformation;

/**
 * 视频详情 VO
 *
 * @author fmy
 * @date 2018-11-02 20:08
 */
public class VideoDetailVO {
    private VideoVO video;
    private BrowseInformation browseInformation;

    @Override
    public String toString() {
        return "VideoDetailVO{" +
                "video=" + video +
                ", browseInformation=" + browseInformation +
                '}';
    }

    public VideoVO getVideo() {
        return video;
    }

    public void setVideo(VideoVO video) {
        this.video = video;
    }

    public BrowseInformation getBrowseInformation() {
        return browseInformation;
    }

    public void setBrowseInformation(BrowseInformation browseInformation) {
        this.browseInformation = browseInformation;
    }
}
