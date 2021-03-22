package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

//TODO: make it decide which path to run
public class GSCB extends CommandBase {
    private final DriveSubsystem drive;
  
    public GSCB(DriveSubsystem drive) {
      this.drive = drive;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(drive);
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
  }