package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class that represents a single swerve drive module
 */
public class SwerveModule {
    public WPI_TalonFX driveMotor;
    public WPI_TalonFX rotationMotor;

    private SwerveModuleState targetState = new SwerveModuleState();

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

        rotationMotor.configFactoryDefault();
        rotationMotor.config_kP(0, Constants.driveRotationPID.kP);
        rotationMotor.config_kI(0, Constants.driveRotationPID.kI);
        rotationMotor.config_kD(0, Constants.driveRotationPID.kD);
    }

    public void setState (SwerveModuleState state) {
        targetState = state;

        driveMotor.set(ControlMode.Velocity, UnitConversions.driveMPSToEncoderTicksPer100ms(state.speedMetersPerSecond));
        rotationMotor.set(ControlMode.Position, UnitConversions.driveDegreesToEncoderTicks(state.angle.getDegrees()));

        SmartDashboard.putNumber("error", driveMotor.getClosedLoopError());
        SmartDashboard.putNumber("velocity", UnitConversions.driveMPSToEncoderTicksPer100ms(state.speedMetersPerSecond));
    }

    public SwerveModuleState getState () {
        return targetState;
    }
}