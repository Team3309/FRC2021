package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * Runs the GSCA Auto
 */
public class GSCACommandGroup extends SequentialCommandGroup {
    public GSCACommandGroup (DriveSubsystem drive, IntakeSubsystem intake) {
        addCommands(
                new InstantCommand(intake::startMotor, intake),
                new ConditionalCommand(
                    new FollowTrajectory(drive, "GSCA-red.wpilib.json"), 
                    new FollowTrajectory(drive, "GSCA-blue.wpilib.json"), 
                    new GSCACondition()),
                new InstantCommand(intake::stopMotor, intake)
        );
      }
}
