package com.example.testffmpeg;

import java.util.List;

/**
 * Created by android on 2016/9/10.
 */

public class VideoSize {
    private String ChannelID,StreamTypeId;
    private List<VideoSizeInner> videoSizeInner;

    public String getChannelID() {
        return ChannelID;
    }

    public void setChannelID(String channelID) {
        ChannelID = channelID;
    }

    public String getStreamTypeId() {
        return StreamTypeId;
    }

    public void setStreamTypeId(String streamTypeId) {
        StreamTypeId = streamTypeId;
    }

    public List<VideoSizeInner> getVideoSizeInner() {
        return videoSizeInner;
    }

    public void setVideoSizeInner(List<VideoSizeInner> videoSizeInner) {
        this.videoSizeInner = videoSizeInner;
    }
}
