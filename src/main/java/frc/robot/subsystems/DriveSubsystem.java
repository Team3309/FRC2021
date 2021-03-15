package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import friarLib2.utility.SwerveModule;

public class DriveSubsystem extends SubsystemBase {

    private SwerveModule frontLeftModule;
    private SwerveModule frontRightModule;
    private SwerveModule backLeftModule;
    private SwerveModule backRightModule;

    private HolonomicDriveController holonomicController;
    private SwerveDriveOdometry swerveOdometry;
    private SwerveDriveKinematics swerveKinematics;
    private Pose2d currentRobotPose = new Pose2d();

    private Pose2d autoStartingPose = new Pose2d(0, 0, new Rotation2d()); //TODO: have this change based on the selected auto

    /**
     * Initialize the swerve modules and Kinematics/Odometry objects
     */
    public DriveSubsystem() {
        frontLeftModule = new SwerveModule(Constants.frontLeftModuleDriveMotorID, Constants.frontLeftModulRotationMotorID);
        frontRightModule = new SwerveModule(Constants.frontRightModuleDriveMotorID, Constants.frontRightModuleRotationMotorID);
        backLeftModule = new SwerveModule(Constants.backLeftModuleDriveMotorID, Constants.backLeftModuleRotationMotorID);
        backRightModule = new SwerveModule(Constants.backRightModuleDriveMotorID, Constants.backRightModuleRotationMotorID);

        holonomicController = new HolonomicDriveController(
            Constants.holonomicControllerPID, 
            Constants.holonomicControllerPID, 
            Constants.holonomicControllerPIDTheta
        );

        swerveKinematics = new SwerveDriveKinematics(
            Constants.frontLeftModuleTranslation,
            Constants.frontRightModuleTranslation,
            Constants.backLeftModuleTranslation,
            Constants.backRightModuleTranslation
        );

        swerveOdometry = new SwerveDriveOdometry(
            swerveKinematics, 
            Rotation2d.fromDegrees(-1/*TODO: get gyro angle*/), 
            autoStartingPose
        );
    }

    public void setModuleStates (SwerveModuleState[] states) {
        frontLeftModule.setState(states[0]);
        frontRightModule.setState(states[1]);
        backLeftModule.setState(states[2]);
        backRightModule.setState(states[3]);
    }

    public void setChassisSpeeds (ChassisSpeeds speeds) {
        SwerveModuleState[] moduleStates = swerveKinematics.toSwerveModuleStates(speeds); //Generate the swerve module states
        setModuleStates(moduleStates);
    }

    public Pose2d getRobotPose () {
        return currentRobotPose;
    }

    public void setAutoStartingPose (Pose2d pose) {
        autoStartingPose = pose;
    }

    @Override
    public void periodic() {
        //Update the odometry
        Rotation2d gyroAngle = Rotation2d.fromDegrees(-1/*TODO: get gyro angle*/);

        currentRobotPose = swerveOdometry.update(
            gyroAngle,
            frontLeftModule.getState(),
            frontRightModule.getState(),
            backLeftModule.getState(),
            backRightModule.getState()
        );
    }
}