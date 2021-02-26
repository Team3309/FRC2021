package frc.robot.subsystems;

import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import friarLib2.utility.SwerveModule;

public class DriveSubsystem extends SubsystemBase {

    private SwerveModule frontLeftModule;
    private SwerveModule frontRightModule;
    private SwerveModule backLeftModule;
    private SwerveModule backRightModule;

    /**
     * Creates a new ExampleSubsystem.
     */
    public DriveSubsystem() {
        frontLeftModule = new SwerveModule(Constants.frontLeftModuleDriveMotorID, Constants.frontLeftModulRotationMotorID);
        frontRightModule = new SwerveModule(Constants.frontRightModuleDriveMotorID, Constants.frontRightModuleRotationMotorID);
        backLeftModule = new SwerveModule(Constants.backLeftModuleDriveMotorID, Constants.backLeftModuleRotationMotorID);
        backRightModule = new SwerveModule(Constants.backRightModuleDriveMotorID, Constants.backRightModuleRotationMotorID);
    }

    public void setModuleStates (SwerveModuleState[] states) {
        frontLeftModule.setState(states[0]);
        frontRightModule.setState(states[1]);
        backLeftModule.setState(states[2]);
        backRightModule.setState(states[3]);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}