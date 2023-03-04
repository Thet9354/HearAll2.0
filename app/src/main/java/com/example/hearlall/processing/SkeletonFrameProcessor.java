package com.example.hearlall.processing;

import com.example.hearlall.imaging.IFrame;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.Ximgproc;

import java.util.ArrayList;
import java.util.List;


public class SkeletonFrameProcessor implements IFrameProcessor {

    private final Mat skeletonMask = new Mat();
    private final Mat hierarchy = new Mat();
    private final List<MatOfPoint> skeletonContours = new ArrayList<>();

    @Override
    public IFrame process(IFrame inputFrame) {

        skeletonContours.clear();

        Ximgproc.thinning(inputFrame.getMaskedImage(), skeletonMask, Ximgproc.THINNING_ZHANGSUEN);

        Imgproc.findContours(skeletonMask,
                skeletonContours,
                hierarchy,
                Imgproc.RETR_CCOMP,
                Imgproc.CHAIN_APPROX_SIMPLE);

        inputFrame.setSkeleton(skeletonMask);
        inputFrame.setSkeletonContours(skeletonContours);

        return inputFrame;

    }

}
