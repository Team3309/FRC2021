package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import friarLib2.math.CTREModuleState;

/**
 * Class that represents a single swerve drive module
 */
public class SwerveModule {
    public String name; // USed for diplaying values on SmartDashboard
    public WPI_TalonFX driveMotor;
    public WPI_TalonFX rotationMotor;

    private double lastAngle = 0.0;

    public SwerveModule (int driveMotorID, int rotationMotorID, String name) {
        this.name = name;
        driveMotor = new WPI_TalonFX(driveMotorID);
        rotationMotor = new WPI_TalonFX(rotationMotorID);
        configMotors();
    }

    public void configMotors () {
        driveMotor.configFactoryDefault();
        driveMotor.config_kP(0, Constants.drivePID.kP);
        driveMotor.config_kI(0, Constants.drivePID.kI);
        driveMotor.config_kD(0, Constants.drivePID.kD);
        driveMotor.config_IntegralZone(0, 500);

        rotationMotor.configFactoryDefault();
        rotationMotor.config_kP(0, Constants.driveRotationPID.kP);
        rotationMotor.config_kI(0, Constants.driveRotationPID.kI);
        rotationMotor.config_kD(0, Constants.driveRotationPID.kD);
        rotationMotor.config_IntegralZone(0, 500);
    }

    /*public void setState (SwerveModuleState state) {
        
        double currentDegrees = getState().angle.getDegrees() % 360;
        if (currentDegrees > 180) {
            currentDegrees -= 360;
        }

        //state = SwerveModuleState.optimize(state, Rotation2d.fromDegrees(currentDegrees));

        // coming in from the joystick (and kinematics)
        double targetDegrees = state.angle.getDegrees();

        if(Math.abs(currentDegrees) + Math.abs(targetDegrees) > 180
        && Math.signum(currentDegrees) != Math.signum(targetDegrees)) {
            if (currentDegrees < 0) {
                offset--;
            } else {
                offset++;
            }
        }

        double offsetDegrees = targetDegrees + (offset * 360);

        driveMotor.set(ControlMode.Velocity, UnitConversions.driveMPSToEncoderTicksPer100ms(state.speedMetersPerSecond));
        rotationMotor.set(ControlMode.Position, UnitConversions.driveDegreesToEncoderTicks(offsetDegrees));

        SmartDashboard.putNumber(name + " speed target", state.speedMetersPerSecond);
        SmartDashboard.putNumber(name + " target degrees", targetDegrees);
        SmartDashboard.putNumber(name + " offset", offset);
        SmartDashboard.putNumber(name + " offset degrees", offsetDegrees);
        SmartDashboard.putNumber(name + " current degrees", currentDegrees);
    }*/

    /**
     * Set the state of the swerve module. Credit to team 364 for CTREModuleState
     * 
     * @param state the new target state for the module
     */
    public void setState (SwerveModuleState state) {
        
        state = CTREModuleState.optimize(state, getState().angle);

        double velocity = UnitConversions.driveMPSToEncoderTicksPer100ms(state.speedMetersPerSecond);
        driveMotor.set(ControlMode.Velocity, velocity);

        double angle = (Math.abs(state.speedMetersPerSecond) <= (Constants.absouluteMaxDriveSpeed * 0.01)) ? lastAngle : state.angle.getDegrees(); //Prevent rotating module if speed is les then 1%. Prevents Jittering.
        rotationMotor.set(ControlMode.Position, UnitConversions.driveDegreesToEncoderTicks(angle)); 
        lastAngle = angle;
    }

    /**
     * Get the physical position of the module
     * 
     * @return the module's position
     */
    public SwerveModuleState getState () {
        return new SwerveModuleState(
                UnitConversions.driveEncoderTicksPer100msToMPS(driveMotor.getSelectedSensorVelocity()), 
                Rotation2d.fromDegrees(UnitConversions.driveEncoderTicksToDegrees(rotationMotor.getSelectedSensorPosition()))
        );
    }
}