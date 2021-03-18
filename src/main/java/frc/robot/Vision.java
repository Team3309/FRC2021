package frc.robot;

import friarLib2.vision.LimelightCamera;
import friarLib2.vision.PhotonCameraWrapper;
import friarLib2.vision.visionCamera;

/**
 * Container for the vision systems
 */
public class Vision {
    public static visionCamera ballCam = new PhotonCameraWrapper(""); //TODO: photoncamera name
    public static visionCamera targetCam = new LimelightCamera();

    /**
     * @return the distance in meters from the target
     */
    public static double getDistanceFromTarget () {
        return 0.0; //TODO: trig?
    }
}
