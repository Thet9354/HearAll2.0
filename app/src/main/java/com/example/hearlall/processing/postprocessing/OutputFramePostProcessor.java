package com.example.hearlall.processing.postprocessing;


import com.example.hearlall.imaging.IFrame;
import com.example.hearlall.processing.IFrameProcessor;

public class OutputFramePostProcessor implements IFramePostProcessor {

    private final IFramePostProcessor upScalingFramePostProcessor;
    private final IFrameProcessor resizer;
    private IFrame outputFrame;

    public OutputFramePostProcessor(IFramePostProcessor upScalingFramePostProcessor,
                                    IFrameProcessor resizingFrameProcessor) {
        this.upScalingFramePostProcessor = upScalingFramePostProcessor;
        this.resizer = resizingFrameProcessor;
    }

    @Override
    public IFrame postProcess(IFrame inputFrame) {

        // Currently Unused
//        outputFrame = resizer.process(inputFrame);

//        outputFrame = upScalingFramePostProcessor.postProcess(outputFrame);

        return inputFrame;

    }

}
