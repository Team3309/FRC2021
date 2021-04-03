package frc.robot;

import friarLib2.vision.PhotonCameraWrapper;
import friarLib2.vision.visionCamera;

/**
 * Container for the vision systems
 */
public class Vision {
    public static visionCamera ballCam = new PhotonCameraWrapper(""); //TODO: photoncamera names
    public static visionCamera targetCam = new PhotonCameraWrapper("");

    /**
     * @return the distance in meters from the target
     */
    public static double getDistanceFromTarget () {
        return targetCam.getBestTarget().getPose().getX();
    }
}
