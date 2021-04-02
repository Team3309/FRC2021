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

        holonomicController = new HolonomicDriveController(
            Constants.holonomicControllerPID, 
            Constants.holonomicControllerPID, 
            Constants.holonomicControllerPIDTheta
        );
        holonomicController.setTolerance(new Pose2d(new Translation2d(.2, .2), Rotation2d.fromDegrees(360)));

        trajectory = openTrajectoryFromJSON(trajectoryJSON); //Load the pathweaver trajectory
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drive.resetOdometry(trajectory.getInitialPose());
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Trajectory.State goal = trajectory.sample(timer.get()); //Find the pose

        //Use the holonomic drive controller to calculate the requred chassis speeds to follow the trajectory
        drive.setChassisSpeeds(holonomicController.calculate(
            drive.getRobotPose(), 
            goal, 
            drive.getRobotRotation()
        ));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return /*holonomicController.atReference() &&*/ timer.get() >= trajectory.getTotalTimeSeconds();
    }

    /**
     * Read the JSON output from pathweaver and convert it to a trajectory object
     *
     * @param JSONPath the path to the JSON, e.x. "paths/bounceLeg1.wpilib.json"
     */
    private Trajectory openTrajectoryFromJSON (String JSONPath) {
        Trajectory trajectory = new Trajectory();
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(JSONPath);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Couldn't load trajectory", true);
        }

        return trajectory;
    }
}
