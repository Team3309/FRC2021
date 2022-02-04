package friarLib2.utility;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;

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

    /**
     * Helper method to initialize a TalonFX's PID parameters
     * 
     * @param motor motor to configure
     * @param PID PID parameters
     */
    public static void configureMotorPID (BaseTalon motor, PIDParameters PID) {
        motor.config_kP(0, PID.kP);
        motor.config_kI(0, PID.kI);
        motor.config_kD(0, PID.kD);
        motor.config_kF(0, PID.kF);
    }
}