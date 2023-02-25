package com.example.hearlall.processing.postprocessing;


import com.example.hearlall.imaging.IFrame;

public interface IFramePostProcessor {

    IFrame postProcess(IFrame inputFrame);

}
