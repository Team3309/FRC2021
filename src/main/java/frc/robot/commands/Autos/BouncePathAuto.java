package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Instructs the robot to follow each of the bounce path's legs in sequence
 */
public class BouncePathAuto extends SequentialCommandGroup {
  public BouncePathAuto (DriveSubsystem drive) {
    addCommands(
            new FollowTrajectory(drive, "bounceLeg1.wpilib.json"),
            new FollowTrajectory(drive, "bounceLeg2.wpilib.json"),
            new FollowTrajectory(drive, "bounceLeg3.wpilib.json"),
            new FollowTrajectory(drive, "bounceLeg4.wpilib.json")
    );
  }
}
