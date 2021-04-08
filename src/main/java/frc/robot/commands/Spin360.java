package frc.robot.commands;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class Spin360 extends CommandBase {

    DriveSubsystem drive;
    double initialPosition;

    public Spin360 (DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
      }
    
      // Called when the command is initially scheduled. 
      @Override
      public void initialize() {
        initialPosition = drive.getRobotRotation().getDegrees();
      }
    
      // Called every time the scheduler runs while the command is scheduled.
      @Override
      public void execute() {
          drive.setChassisSpeeds(new ChassisSpeeds(0, 0, 5));
      }
    
      // Called once the command ends or is interrupted.
      @Override
      public void end(boolean interrupted) {
      }
    
      // Returns true when the command should end.
      @Override
      public boolean isFinished() {
        return Math.abs(drive.getRobotRotation().getDegrees() - (initialPosition + 360)) < 5;
      }
}
