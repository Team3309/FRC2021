package frc.robot.subsystems;

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

    private SwerveDriveOdometry swerveOdometry;
    private SwerveDriveKinematics swerveKinematics;
    private Pose2d currentRobotPose = new Pose2d();

    /**
     * Initialize the swerve modules and Kinematics/Odometry objects
     */
    public DriveSubsystem() {
        //initialize swerve modules
        frontLeftModule = new SwerveModule(Constants.frontLeftModuleDriveMotorID, Constants.frontLeftModulRotationMotorID);
        frontRightModule = new SwerveModule(Constants.frontRightModuleDriveMotorID, Constants.frontRightModuleRotationMotorID);
        backLeftModule = new SwerveModule(Constants.backLeftModuleDriveMotorID, Constants.backLeftModuleRotationMotorID);
        backRightModule = new SwerveModule(Constants.backRightModuleDriveMotorID, Constants.backRightModuleRotationMotorID);

        swerveKinematics = new SwerveDriveKinematics(
            Constants.frontLeftModuleTranslation,
            Constants.frontRightModuleTranslation,
            Constants.backLeftModuleTranslation,
            Constants.backRightModuleTranslation
        );

        swerveOdometry = new SwerveDriveOdometry(
            swerveKinematics, 
            getRobotRotation()
        );
    }

    /***
     * Sets the states of each swerve module, i.e: translates swerve module states into robot motion.
     * 
     * @param states The array of states to which the modules should be set.
     */
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

    public Rotation2d getRobotRotation () {
        return Rotation2d.fromDegrees(-1/*TODO: get gyro angle*/);
    }

    public void resetOdometry (Pose2d pose) {
        swerveOdometry.resetPosition(pose, getRobotRotation());
    }

    @Override
    public void periodic() {
        //Update the odometry
        Rotation2d gyroAngle = getRobotRotation();

        currentRobotPose = swerveOdometry.update(
            gyroAngle,
            frontLeftModule.getState(),
            frontRightModule.getState(),
            backLeftModule.getState(),
            backRightModule.getState()
        );
    }

}