package frc.robot.utility;

public class PIDParameters {
    public double kP = 0;
    public double kI = 0;
    public double kD = 0;
    public double kF = 0;
    
    public PIDParameters (double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    public PIDParameters (double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
}