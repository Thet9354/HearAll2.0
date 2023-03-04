package com.example.hearlall.rendering;

import com.example.hearlall.imaging.IFrame;
import com.example.hearlall.processing.DetectionMethod;

import java.util.ArrayList;


public class MainRenderer implements IRenderer {

    private final ArrayList<IRenderer> renderers = new ArrayList<>();

    public MainRenderer(DetectionMethod method) {
        setRenderers(method);
    }

    public void display(IFrame inputFrame) {

        for (IRenderer renderer : renderers){
            renderer.display(inputFrame);
        }

    }

    private void setRenderers(DetectionMethod method) {
        switch (method){
            case CANNY_EDGES:
                renderers.add(new CannyEdgesRenderer());
                break;
            case CONTOUR_MASK:
                renderers.add(new ContourMaskRenderer());
                break;
            case SKELETON:
                renderers.add(new SkeletonRenderer());
                break;
            default:
                break;
        }

    }

}
