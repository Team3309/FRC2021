package frc.robot.commands.Autos;

import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.HolonomicDriveController;
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

        openTrajectoryFromJSON(trajectory, "bounceLeg1"); //Load the pathweaver trajectory
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
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
        return holonomicController.atReference() && timer.get() >= trajectory.getTotalTimeSeconds();
    }

    /**
     * Read the JSON output from pathweaver and convert it to a trajectory object
     *
     * @param trajectory the object to store the read trajectory into
     * @param JSONPath the path to the JSON, e.x. "paths/bounceLeg1.wpilib.json"
     */
    private void openTrajectoryFromJSON (Trajectory trajectory, String JSONPath) {
        String leg1JSON = JSONPath; //"paths/" + JSONName + ".wpilib.json";
        this.trajectory = new Trajectory();
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(leg1JSON);
            this.trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {}
    }
}
