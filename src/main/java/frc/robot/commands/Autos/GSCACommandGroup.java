package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Spin360;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class GSCACommandGroup extends SequentialCommandGroup {
    public GSCACommandGroup (DriveSubsystem drive, IntakeSubsystem intake) {
        addCommands(
                new InstantCommand(intake::startMotor, intake),
                //new Spin360(drive),
                new ConditionalCommand(
                    new FollowTrajectory(drive, "GSCA-red.wpilib.json"), 
                    new FollowTrajectory(drive, "GSCA-blue.wpilib.json"), 
                    new GSCA()),
                new InstantCommand(intake::stopMotor, intake)
        );
      }
}
