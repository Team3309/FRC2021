package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;

/**
 * Class that represents a single swerve drive module
 */
public class SwerveModule {
    public WPI_TalonFX driveMotor;
    public WPI_TalonFX rotationMotor;

    public SwerveModule (int driveMotorID, int rotationMotorID) {
        driveMotor = new WPI_TalonFX(driveMotorID);
        rotationMotor = new WPI_TalonFX(rotationMotorID);
        configMotors();
    }

    public void configMotors () {
        driveMotor.configFactoryDefault();
        driveMotor.config_kP(0, Constants.drivePID.kP);
        driveMotor.config_kI(0, Constants.drivePID.kI);
        driveMotor.config_kD(0, Constants.drivePID.kD);
        driveMotor.config_IntegralZone(0, 200);

        rotationMotor.configFactoryDefault();
        rotationMotor.config_kP(0, Constants.driveRotationPID.kP);
        rotationMotor.config_kI(0, Constants.driveRotationPID.kI);
        rotationMotor.config_kD(0, Constants.driveRotationPID.kD);
        rotationMotor.config_IntegralZone(0, 200);
    }

    public void setState (SwerveModuleState state) {
        state = optimizeState(getState(), state);

        driveMotor.set(ControlMode.Velocity, UnitConversions.driveMPSToEncoderTicksPer100ms(state.speedMetersPerSecond));
        rotationMotor.set(ControlMode.Position, UnitConversions.driveDegreesToEncoderTicks(state.angle.getDegrees()));
    }

    public SwerveModuleState getState () {
        return new SwerveModuleState(
                driveMotor.getSelectedSensorVelocity() / UnitConversions.driveMPSToEncoderTicksPer100ms(1), 
                Rotation2d.fromDegrees(rotationMotor.getSelectedSensorPosition() / UnitConversions.driveDegreesToEncoderTicks(1)));
    }

    public static SwerveModuleState optimizeState (SwerveModuleState currentState, SwerveModuleState targetState) {
        if (targetState.angle.minus(currentState.angle).getDegrees() < 180) {
            return targetState;
        } else {
            return new SwerveModuleState(-targetState.speedMetersPerSecond, targetState.angle.plus(Rotation2d.fromDegrees(180)));
        }
    }
}