package friarLib2.vision;

public class visionTarget {

    private double x; //X offset from crosshair (Yaw)
    private double y; //Y offset from crosshair (Pitch)
    private double area; //Area
    private double skew; //Skew

    public visionTarget (double x, double y, double area, double skew) {
        this.x = x;
        this.y = y;
        this.area = area;
        this.skew = skew;
    }

    public double getX () {return x;}
    public double getY () {return y;}
    public double getArea () {return area;}
    public double getSkew () {return skew;}
}
