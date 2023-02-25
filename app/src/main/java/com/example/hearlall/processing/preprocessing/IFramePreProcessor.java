package com.example.hearlall.processing.preprocessing;

import com.example.hearlall.imaging.IFrame;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;


public interface IFramePreProcessor {

    IFrame preProcess(CvCameraViewFrame inputFrame);

}
