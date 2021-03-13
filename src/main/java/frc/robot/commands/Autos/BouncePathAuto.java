package frc.robot.commands.Autos;

import frc.robot.subsystems.DriveSubsystem;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BouncePathAuto extends CommandBase {
  private final DriveSubsystem drive;

  Trajectory leg1;
  Trajectory leg2;
  Trajectory leg3;
  Trajectory leg4;

  /**
   * @param drive The drive subsystem
   */
  public BouncePathAuto (DriveSubsystem drive) {
    this.drive = drive;
    addRequirements(drive);

    openTrajectoryFromJSON(leg1, "bounceLeg1");
    openTrajectoryFromJSON(leg2, "bounceLeg2");
    openTrajectoryFromJSON(leg3, "bounceLeg3");
    openTrajectoryFromJSON(leg4, "bounceLeg4");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private void openTrajectoryFromJSON (Trajectory trajectory, String JSONName) {
    String leg1JSON = "paths/" + JSONName + ".wpilib.json";
    trajectory = new Trajectory();
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(leg1JSON);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {}
  }
}
