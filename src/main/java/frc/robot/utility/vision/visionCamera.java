package frc.robot.utility.vision;

public interface visionCamera {
    public boolean hasTargets ();
    public visionTarget[] getTargets ();
    public visionTarget getBestTarget ();
    public void setPipeline (String pipelineName);
}