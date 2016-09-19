package com.example.testffmpeg;

import java.util.List;

/**
 * Created by android on 2016/9/10.
 */

public class GetVideoEncode {
    private String ChannelTypeId;
    private List<VideoSize> videoSize;

    public String getChannelTypeId() {
        return ChannelTypeId;
    }

    public void setChannelTypeId(String channelTypeId) {
        ChannelTypeId = channelTypeId;
    }

    public List<VideoSize> getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(List<VideoSize> videoSize) {
        this.videoSize = videoSize;
    }
}
