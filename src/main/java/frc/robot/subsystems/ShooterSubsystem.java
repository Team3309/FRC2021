package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Vision;
import friarLib2.hardware.BoschLinearMotor;
import friarLib2.math.LinearRegression;
import friarLib2.utility.PIDParameters;

public class ShooterSubsystem extends SubsystemBase {

    private WPI_TalonFX topFlywheelMotor;
    private WPI_TalonFX bottomFlywheelMotor;
    private WPI_TalonFX feederMotor;
    private BoschLinearMotor linearMotor;

    private LinearRegression regression;

    public ShooterSubsystem () {
        topFlywheelMotor = new WPI_TalonFX(Constants.topShooterMotorID);
        bottomFlywheelMotor = new WPI_TalonFX(Constants.bottomShooterMotorID);
        feederMotor = new WPI_TalonFX(Constants.feederMotorID);
        configMotor(topFlywheelMotor, Constants.topFlywheelPID);
        configMotor(bottomFlywheelMotor, Constants.bottomFlywheelPID);

        //Compute a linear regression of distances and shooter angles so that we can aim anywhere
        regression = new LinearRegression(Constants.aimRegressionData);
    }

    private static void configMotor (WPI_TalonFX motor, PIDParameters PID) {
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Coast);

        motor.config_kP(0, PID.kP);
        motor.config_kI(0, PID.kI);
        motor.config_kD(0, PID.kD);
    }

    public void setFlywheelSpeeds (int topFlywheelSpeed, int bottomFlyWheelSpeed) {
        topFlywheelMotor.set(ControlMode.Velocity, topFlywheelSpeed);
        bottomFlywheelMotor.set(ControlMode.Velocity, bottomFlyWheelSpeed);
    }

    public void stopMotors () {
        topFlywheelMotor.stopMotor();
        bottomFlywheelMotor.stopMotor();
    }

    public void setAngleDegrees (double angle) {
        // Law of cosines
        linearMotor.setDistance(
            Math.sqrt(
                Math.pow(Constants.shooterTriangleB, 2) + Math.pow(Constants.shooterTriangleC, 2) // b^2 + c^2
                - (2 * Constants.shooterTriangleB * Constants.shooterTriangleC * Math.cos(Math.toRadians(angle) + Constants.shooterTriangleA)))); // -2bc * cos(theta + a)
    }

    public void setAngleRadians (double angle) {
        setAngleDegrees(Units.radiansToDegrees(angle));
    }

    public boolean isUpToSpeed () {
        return (topFlywheelMotor.getClosedLoopError() <= Constants.flywheelSpeedTolerance) && (bottomFlywheelMotor.getClosedLoopError() <= Constants.flywheelSpeedTolerance);
    }

    public void aim () {
        setAngleDegrees(regression.evaluate(Vision.getDistanceFromTarget()));
    }

    public void feed() {
        feederMotor.set(ControlMode.Velocity, Constants.feederMotorStandardSpeed);
    }
    public void shoot () {
        if (isUpToSpeed() && topFlywheelMotor.getClosedLoopTarget() + bottomFlywheelMotor.getClosedLoopTarget() != 0) {
            feed();
        }
    }

    
}
