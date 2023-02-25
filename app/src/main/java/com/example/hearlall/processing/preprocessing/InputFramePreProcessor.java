package com.example.hearlall.processing.preprocessing;

import com.example.hearlall.imaging.IFrame;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;


public class InputFramePreProcessor implements IFramePreProcessor {

    private IFramePreProcessor frameAdapter;
    private IFrame outputFrame;

    public InputFramePreProcessor(IFramePreProcessor inputFrameAdapter) {
        frameAdapter = inputFrameAdapter;
    }

    @Override
    public IFrame preProcess(CvCameraViewFrame inputFrame) {

        outputFrame = frameAdapter.preProcess(inputFrame);

        return outputFrame;

    }

    public void setFrameAdapter(IFramePreProcessor frameAdapter) {
        this.frameAdapter = frameAdapter;
    }

}
