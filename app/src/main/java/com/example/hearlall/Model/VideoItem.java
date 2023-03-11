package com.example.hearlall.Model;

public class VideoItem {
    private String mTitle;
    private String mPath;
    private String mSubtitlePath;

    public VideoItem(String title, String path, String subtitlePath) {
        mTitle = title;
        mPath = path;
        mSubtitlePath = subtitlePath;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPath() {
        return mPath;
    }

    public String getSubtitlePath() {
        return mSubtitlePath;
    }
}
