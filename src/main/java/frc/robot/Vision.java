package frc.robot;

import friarLib2.vision.LimelightCamera;
import friarLib2.vision.PhotonCameraWrapper;
import friarLib2.vision.visionCamera;

/**
 * Container for the vision systems
 * 
 */
public class Vision {
    public static visionCamera ballCam; //For GSC
    public static visionCamera targetCam; //For Scoring

    public Vision () {
        ballCam = new PhotonCameraWrapper(""); //TODO: photoncamera name
        targetCam = new LimelightCamera();
    }
}
