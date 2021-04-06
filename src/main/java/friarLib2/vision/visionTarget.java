package friarLib2.vision;

import edu.wpi.first.wpilibj.geometry.Transform2d;

public class VisionTarget {

    private double x; //X offset from crosshair (Yaw)
    private double y; //Y offset from crosshair (Pitch)
    private double area; //Area
    private double skew; //Skew
    private Transform2d pose; //Location of target relative to robot

    public VisionTarget () {
        x = 0;
        y = 0;
        area = 0;
        skew = 0;
        pose = new Transform2d();
    }

    public VisionTarget (double x, double y, double area, double skew) {
        this.x = x;
        this.y = y;
        this.area = area;
        this.skew = skew;
        pose = new Transform2d();
    }

    public VisionTarget (double x, double y, double area, double skew, Transform2d pose) {
        this.x = x;
        this.y = y;
        this.area = area;
        this.skew = skew;
        this.pose = pose;
    }

    public double getX () {return x;}
    public double getY () {return y;}
    public double getArea () {return area;}
    public double getSkew () {return skew;}
    public Transform2d getPose () {return pose;}
}
