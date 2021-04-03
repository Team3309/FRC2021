package frc.robot.subsystems;

<<<<<<< Updated upstream
=======
import com.analog.adis16470.frc.ADIS16470_IMU;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
>>>>>>> Stashed changes
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
     * Creates a new DriveSubsystem object.
     *
     *
     */
    public DriveSubsystem() {
        //initialize swerve modules
        frontLeftModule = new SwerveModule(Constants.frontLeftModuleDriveMotorID, Constants.frontLeftModulRotationMotorID);
        frontRightModule = new SwerveModule(Constants.frontRightModuleDriveMotorID, Constants.frontRightModuleRotationMotorID);
        backLeftModule = new SwerveModule(Constants.backLeftModuleDriveMotorID, Constants.backLeftModuleRotationMotorID);
        backRightModule = new SwerveModule(Constants.backRightModuleDriveMotorID, Constants.backRightModuleRotationMotorID);
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

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

}