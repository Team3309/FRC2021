package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * Used for manually moving the angle of the shooter; will probably be removed in the near future
 */
public class ShooterAngleTest extends CommandBase {
    private final ShooterSubsystem shooter;

    public ShooterAngleTest (ShooterSubsystem shooter) {
      this.shooter = shooter;
  
      addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }
  
    @Override
    public void execute() {
        if (OperatorInterface.OperatorController.getPOV() == 0 && shooter.linearMotor.getSelectedSensorPosition() < Constants.shooterLinearMotorSoftstop) {
            shooter.linearMotor.set(ControlMode.PercentOutput, 1);
        } else if (OperatorInterface.OperatorController.getPOV() == 180 && !shooter.getLimitSwitch()) {
            shooter.linearMotor.set(ControlMode.PercentOutput, -1);
        } else {
            shooter.linearMotor.stopMotor();
        }
    }
  
    @Override
    public void end(boolean interrupted) {
      shooter.linearMotor.stopMotor();
    }
}
