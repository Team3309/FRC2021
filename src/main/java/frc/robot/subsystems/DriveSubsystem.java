package frc.robot.subsystems;

import com.analog.adis16470.frc.ADIS16470_IMU;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SwerveModule2021;
import frc.robot.SwerveModule2022;
import friarLib2.hardware.SwerveModule;

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

        leftModule = new SwerveModule2022(0, Constants.leftModuleDriveMotorID, Constants.leftModuleRotationMotorID, 50, "Left module");
        rightModule = new SwerveModule2021(Constants.rightModuleDriveMotorID, Constants.rightModuleRotationMotorID, "Right module");

        swerveKinematics = new SwerveDriveKinematics(
            Constants.leftModuleTranslation,
            Constants.rightModuleTranslation
        );

        swerveOdometry = new SwerveDriveOdometry(
            swerveKinematics, 
            getRobotRotation()
        );
    }

    /**
     * Sets the states of each swerve module, i.e: translates swerve module states into robot motion.
     * 
     * @param states The array of states to which the modules should be set.
     */
    public void setModuleStates (SwerveModuleState[] states) {
        leftModule.setState(states[0]);
        rightModule.setState(states[1]);
    }

    /**
     * Calculate and set the requred SwerveModuleStates for a given ChassisSpeeds
     * 
     * @param speeds
     */
    public void setChassisSpeeds (ChassisSpeeds speeds) {
        SwerveModuleState[] moduleStates = swerveKinematics.toSwerveModuleStates(speeds); //Generate the swerve module states
        SwerveDriveKinematics.normalizeWheelSpeeds(moduleStates, Units.feetToMeters(Constants.absouluteMaxDriveSpeed));
        setModuleStates(moduleStates);
    }

    /**
     * Get the current robot pose according to dead-reckoning odometry
     * See https://docs.wpilib.org/en/stable/docs/software/kinematics-and-odometry/swerve-drive-odometry.html for more details
     * 
     * @return Current robot pose
     */
    public Pose2d getRobotPose () {
        return currentRobotPose;
    }

    /**
     * Use the IMU to read the robot's yaw
     * 
     * @return Rotation2d representing IMU's measured angle
     */
    public Rotation2d getRobotRotation () {
        double angle = imu.getAngle();
        return Rotation2d.fromDegrees(angle);
    }

    /**
     * Set the odometry readings
     * 
     * @param pose Pose to be written to odometry
     * @param rotation Roatation to be written to odometry
     */
    public void resetOdometry (Pose2d pose, Rotation2d rotation) {
        swerveOdometry.resetPosition(pose, rotation);
    }

    @Override
    public void periodic() {
        //Update the odometry using module states and chassis rotation
        currentRobotPose = swerveOdometry.update(
            getRobotRotation(),
            leftModule.getState(),
            rightModule.getState()
        );

        SmartDashboard.putNumber("Left Module angle", leftModule.getState().angle.getDegrees());
        SmartDashboard.putNumber("Right Module angle", rightModule.getState().angle.getDegrees());
        SmartDashboard.putNumber("Left Module speed", leftModule.getState().speedMetersPerSecond);
        SmartDashboard.putNumber("RIght Module speed", rightModule.getState().speedMetersPerSecond);
        SmartDashboard.putNumber("Robot heading", getRobotRotation().getDegrees());
        /*SmartDashboard.putNumber("Left Module Drive error", leftModule.driveMotor.getClosedLoopError());
        SmartDashboard.putNumber("Right Module Drive error", rightModule.driveMotor.getClosedLoopError());
        SmartDashboard.putNumber("Left Module Rotation error", leftModule.rotationMotor.getClosedLoopError());
        SmartDashboard.putNumber("Right Module Rotation error", rightModule.rotationMotor.getClosedLoopError());*/
        SmartDashboard.putString("Odometry", currentRobotPose.toString());

        leftModule.outputToDashboard();
        rightModule.outputToDashboard();
    }

}