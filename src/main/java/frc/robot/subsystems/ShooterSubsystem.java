package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Vision;
import friarLib2.math.LinearRegression;
import friarLib2.utility.PIDParameters;

public class ShooterSubsystem extends SubsystemBase {

    private WPI_TalonFX topFlywheelMotor;
    private WPI_TalonFX bottomFlywheelMotor;

    private LinearRegression regression;

    public ShooterSubsystem () {
        topFlywheelMotor = new WPI_TalonFX(Constants.topShooterMotorID);
        bottomFlywheelMotor = new WPI_TalonFX(Constants.bottomShooterMotorID);
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
        //TODO: set angle
    }

    public void setAngleRadians (double angle) {
        setAngleDegrees(Units.radiansToDegrees(angle));
    }

    public boolean isUpToSpeed () {
        return (topFlywheelMotor.getClosedLoopError() <= Constants.flywheelSpeedTolearace) && (bottomFlywheelMotor.getClosedLoopError() <= Constants.flywheelSpeedTolearace);
    }

    public void aim () {
        setAngleDegrees(regression.evaluate(Vision.getDistanceFromTarget()));
    }

    public void shoot () {
        if (isUpToSpeed() && topFlywheelMotor.getClosedLoopTarget() + bottomFlywheelMotor.getClosedLoopTarget() != 0) {
            //TODO: move a powercell into the flywheels
        }
    }
}
