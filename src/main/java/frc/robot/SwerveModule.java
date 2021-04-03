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

    /***
     * Sets the motor PID values to those which will make the robot move the way we want.
     */
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

    /***
     * Translates swerve module states to specific motor motion.
     * 
     * @param state The state to which the swerve module should be set.
     */
    public void setState (SwerveModuleState state) {
        state = SwerveModuleState.optimize(state, state.angle);

        driveMotor.set(ControlMode.Velocity, UnitConversions.driveMPSToEncoderTicksPer100ms(state.speedMetersPerSecond));
        rotationMotor.set(ControlMode.Position, UnitConversions.driveDegreesToEncoderTicks(state.angle.getDegrees()));

        targetState = state;
    }

    public SwerveModuleState getState () {
        return targetState;
    }
}