package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay.InvalidValueException;
import frc.robot.subsystems.DriveSubsystem;

public class GSCA extends CommandBase {
    private final DriveSubsystem drive;
    private boolean red = false;
  
    public GSCA(DriveSubsystem drive) {
      this.drive = drive;
      

      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(drive);
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

      if (red) {
        new FollowTrajectory(drive, "GSCB-red.wpilib.json");
      } else if (!red) {
        new FollowTrajectory(drive, "GCSB-blue.wpilib.json");
      } 
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
  }
  