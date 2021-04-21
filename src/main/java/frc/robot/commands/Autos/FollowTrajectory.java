package frc.robot.commands.Autos;

import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Makes the robot follow the pathweaver JSON passed in through the constructor
 */
public class FollowTrajectory extends CommandBase {

    private final DriveSubsystem drive;

    private HolonomicDriveController holonomicController;
    private Timer timer = new Timer();
    private Trajectory trajectory;

    /**
     * @param drive The drive subsystem
     */
    public FollowTrajectory (DriveSubsystem drive, String trajectoryJSON) {
        this.drive = drive;
        addRequirements(drive);

        // The holonomic controller uses the current robot pose and the target pose (from the trajectory) and calculates the required ChassisSpeeds to get to that location
        // See https://docs.wpilib.org/en/stable/docs/software/advanced-controls/trajectories/holonomic.html for more details
        holonomicController = new HolonomicDriveController(
            Constants.holonomicControllerPID, 
            Constants.holonomicControllerPID, 
            Constants.holonomicControllerPIDTheta
        );
        // Set the range where the holonomic controller considers itself at its target location
        holonomicController.setTolerance(new Pose2d(new Translation2d(.09, .09), Rotation2d.fromDegrees(10000000)));

        trajectory = openTrajectoryFromJSON(trajectoryJSON); //Load the pathweaver trajectory
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drive.resetOdometry(trajectory.getInitialPose(), trajectory.getInitialPose().getRotation()); // Re-zero the robot's odometry
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Trajectory.State goal = trajectory.sample(timer.get()); //Find the target pose for the current time

        //Use the holonomic drive controller to calculate the requred chassis speeds to follow the trajectory
        drive.setChassisSpeeds(holonomicController.calculate(
            drive.getRobotPose(), 
            goal, 
            drive.getRobotRotation()
        ));
        
        SmartDashboard.putString("Holonomic controller error", drive.getRobotPose().minus(goal.poseMeters).toString());
        SmartDashboard.putNumber("Holonomic x error",  drive.getRobotPose().minus(goal.poseMeters).getTranslation().getX());
        SmartDashboard.putNumber("Holonomic y error",  drive.getRobotPose().minus(goal.poseMeters).getTranslation().getY());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return holonomicController.atReference() && timer.get() >= trajectory.getTotalTimeSeconds();
    }

    /**
     * Read the JSON output from pathweaver and convert it to a trajectory object
     *
     * @param JSONName the name of the JSON stored in the deploy/output directory, e.x. "bounceLeg1.wpilib.json"
     */
    private Trajectory openTrajectoryFromJSON (String JSONName) {
        JSONName = "output/" + JSONName;

        Trajectory trajectory = new Trajectory();
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(JSONName);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Couldn't load trajectory", true);
        }

        return trajectory;
    }
}
