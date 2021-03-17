package friarLib2.vision;

import java.util.List;

import org.photonvision.LEDMode;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;
import org.photonvision.PhotonTrackedTarget;

public class PhotonCameraWrapper implements visionCamera {

    private PhotonCamera camera;

    /**
     * Initializes the PhotonCamera object 
     * 
     * @param name
     */
    public PhotonCameraWrapper (String name) {
        camera = new PhotonCamera(name);
    }

    /**
     * @return if the camera detects a target
     */
    @Override
    public boolean hasTargets() {
        return camera.hasTargets();
    }

    /**
     * @return an array of visionTarget objects
     */
    @Override
    public visionTarget[] getTargets() {

        //Get the list of targets
        PhotonPipelineResult result = camera.getLatestResult();
        List<PhotonTrackedTarget> targetList = result.getTargets();

        //Parse the PhotonTrackedTarget into visionTarget
        visionTarget[] targets = new visionTarget[targetList.size()];
        for (int i = 0; i < targets.length; i++) {
            targets[i] = new visionTarget(
                targetList.get(i).getYaw(), 
                targetList.get(i).getPitch(), 
                targetList.get(i).getArea(), 
                targetList.get(i).getSkew());
        }
        return targets;
    }

    /**
     * @return the object with index 0
     */
    @Override
    public visionTarget getBestTarget() {
        return getTargets()[0];
    }

    /**
     * @param pipeline the pipeline's index, use "driver mode" to enter driver mode
     */
    @Override
    public void setPipeline(String pipeline) {
        if (pipeline.equals("driver mode")) {
            camera.setDriverMode(true);
        } else {
            camera.setDriverMode(false);
            camera.setPipelineIndex(Integer.parseInt(pipeline));
        }
    }

    @Override
    public void setLights(LedMode mode) {
        switch (mode) {
            case on: camera.setLED(LEDMode.kOn); break;
            case off: camera.setLED(LEDMode.kOff); break;
            case blink: camera.setLED(LEDMode.kBlink); break;
            case currentPipeline: camera.setLED(LEDMode.kDefault); break;
        }
    }
}
