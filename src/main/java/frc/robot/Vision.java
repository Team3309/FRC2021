package frc.robot;

import friarLib2.vision.limelightCamera;
import friarLib2.vision.photonCamera;
import friarLib2.vision.visionCamera;

/**
 * Container for the vision systems
 * 
 */
public class Vision {
    public static visionCamera ballCam; //For GSC
    public static visionCamera targetCam; //For Scoring

    public Vision () {
        ballCam = new photonCamera(""); //TODO: photoncamera name
        targetCam = new limelightCamera();
    }
}
