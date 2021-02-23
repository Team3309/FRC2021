package frc.robot.utility.vision;

public class visionTarget {

    public visionTarget (double x, double y, double area, double skew) {
        this.x = x;
        this.y = y;
        this.area = area;
        this.skew = skew;
    }

    public double x; //X offset from crosshair (Yaw)
    public double y; //Y offset from crosshair (Pitch)
    public double area; //Area
    public double skew; //Skew
}
