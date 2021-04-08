package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Spin360;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class GSCBCommandGroup extends SequentialCommandGroup {
    public GSCBCommandGroup (DriveSubsystem drive, IntakeSubsystem intake) {
        addCommands(
                new InstantCommand(intake::startMotor, intake),
                //new Spin360(drive),
                new ConditionalCommand(
                    new FollowTrajectory(drive, "GSCB-red.wpilib.json"), 
                    new FollowTrajectory(drive, "GSCB-blue.wpilib.json"), 
                    new GSCB()),
                new InstantCommand(intake::stopMotor, intake)
        );
      }
}
