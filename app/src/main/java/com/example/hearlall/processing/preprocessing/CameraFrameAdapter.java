package com.example.hearlall.processing.preprocessing;

import com.example.hearlall.imaging.Frame;
import com.example.hearlall.imaging.IFrame;
import com.example.hearlall.processing.IFrameProcessor;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;



public class CameraFrameAdapter implements IFramePreProcessor {

    private IFrameProcessor downSampler, resizer;
    private IFrame outputFrame;

    public CameraFrameAdapter(IFrameProcessor downSamplingFrameProcessor,
                              IFrameProcessor resizingFrameProcessor) {
        downSampler = downSamplingFrameProcessor;
        resizer = resizingFrameProcessor;
    }

    @Override
    public IFrame preProcess(CvCameraViewFrame inputFrame) {

        // Create the pre-processed output frame
        outputFrame = new Frame(inputFrame.rgba());

//         Resize image
        outputFrame = resizer.process(outputFrame);

        // Set the original size of the frame
        outputFrame.setOriginalSize(inputFrame.rgba().size());

        // Resize image - currently unused
//        outputFrame = resizer.process(outputFrame);

        // Further downsampling for efficiency
        outputFrame = downSampler.process(outputFrame);

        return outputFrame;
    }

}
