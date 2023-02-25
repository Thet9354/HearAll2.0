package com.example.hearlall.rendering;

import com.example.hearlall.imaging.IFrame;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;


public class SkeletonRenderer implements IRenderer {

    private Scalar skeletonColour;

    public SkeletonRenderer() {
       setDefaultColour();
    }

    @Override
    public void display(IFrame inputFrame) {

//         display skeleton
        Imgproc.drawContours(inputFrame.getRGBA(),
                inputFrame.getSkeletonContours(),
                -1,
                skeletonColour,
                1);

    }

    private void setDefaultColour(){
        skeletonColour = new Scalar(0,0,255,255);
    }

}
