package frc.robot.subsystems;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SwerveModule;

public class DriveSubsystem extends SubsystemBase {

    private ADIS16470_IMU imu;

    private SwerveModule leftModule;
    private SwerveModule rightModule;

    private SwerveDriveOdometry swerveOdometry;
    private SwerveDriveKinematics swerveKinematics;
    private Pose2d currentRobotPose = new Pose2d();

    /**
     * Initialize the swerve modules and Kinematics/Odometry objects
     */
    public DriveSubsystem() {
        imu = new ADIS16470_IMU();
        imu.calibrate();

        leftModule = new SwerveModule(Constants.leftModuleDriveMotorID, Constants.leftModulRotationMotorID);
        rightModule = new SwerveModule(Constants.rightModuleDriveMotorID, Constants.rightModuleRotationMotorID);

        swerveKinematics = new SwerveDriveKinematics(
            Constants.leftModuleTranslation,
            Constants.rightModuleTranslation
        );

        swerveOdometry = new SwerveDriveOdometry(
            swerveKinematics, 
            getRobotRotation()
        );
    }

    public void setModuleStates (SwerveModuleState[] states) {
        leftModule.setState(states[0]);
        rightModule.setState(states[1]);
    }

    public void setChassisSpeeds (ChassisSpeeds speeds) {
        SwerveModuleState[] moduleStates = swerveKinematics.toSwerveModuleStates(speeds); //Generate the swerve module states
        setModuleStates(moduleStates);
    }

    public Pose2d getRobotPose () {
        return currentRobotPose;
    }

    public Rotation2d getRobotRotation () {
        double angle  = imu.getAngle() % 360;
        return Rotation2d.fromDegrees(angle);
    }

    public void resetOdometry (Pose2d pose) {
        swerveOdometry.resetPosition(pose, getRobotRotation());
    }

    @Override
    public void periodic() {
        //Update the odometry
        currentRobotPose = swerveOdometry.update(
            getRobotRotation(),
            leftModule.getState(),
            rightModule.getState()
        );

        SmartDashboard.putNumber("Left Module angle", leftModule.getState().angle.getDegrees());
        SmartDashboard.putNumber("Robot heading", getRobotRotation().getDegrees());
        SmartDashboard.putNumber("Left Module Drive error", leftModule.driveMotor.getClosedLoopError());
        SmartDashboard.putNumber("Right Module Drive error", rightModule.driveMotor.getClosedLoopError());
        SmartDashboard.putNumber("Left Module Rotation error", leftModule.rotationMotor.getClosedLoopError());
        SmartDashboard.putNumber("Right Module Rotation error", rightModule.rotationMotor.getClosedLoopError());
        SmartDashboard.putString("Odometry", currentRobotPose.toString());
    }
}