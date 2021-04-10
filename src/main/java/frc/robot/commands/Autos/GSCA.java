package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay.InvalidValueException;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class GSCA extends CommandBase {
    private DriveSubsystem drive;
    private IntakeSubsystem intake;
    private boolean red = false;
  
    public GSCA(DriveSubsystem drive, IntakeSubsystem intake) {
      drive = this.drive;
      intake = this.intake;
      

      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(drive, intake);
      if (DriverStation.getInstance().getAlliance() != DriverStation.Alliance.Invalid)  {
        if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
          red = true;
        } 
      } else {
        throw new InvalidValueException("Invalid Alliance.");
      }
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

      new ParallelRaceGroup(
        new InstantCommand(intake::intakePowerCells, intake),
        red ? new FollowTrajectory(drive, "GSCB-red.wpilib.json") 
        : new FollowTrajectory(drive, "GSCB-blue.wpilib.json")
      );
     
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return true;
    }
  }
  