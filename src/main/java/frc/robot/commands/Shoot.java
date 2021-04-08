package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {
  private final ShooterSubsystem shooter;

  public Shoot (ShooterSubsystem shooter) {
    this.shooter = shooter;

    //addRequirements(shooter);
  }

  @Override
  public void execute() {
    //if (shooter.isUpToSpeed()) {
      shooter.setIndexerMotorPower(Constants.indexerMotorPower);
    //}
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stopIndexerMotor();
  }
}
