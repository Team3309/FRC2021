package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ZeroShooterAngle extends CommandBase {
    private final ShooterSubsystem shooter;

    public ZeroShooterAngle (ShooterSubsystem shooter) {
        this.shooter = shooter;

        addRequirements(shooter);
    }

    @Override
    public void execute() {
        if (!shooter.getLimitSwitch()) {
            shooter.linearMotor.set(ControlMode.PercentOutput, -1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.linearMotor.stopMotor();
        if (!interrupted) {
            shooter.linearMotor.setSelectedSensorPosition(0);
        }
    }

    @Override
    public boolean isFinished () {
        return shooter.getLimitSwitch();
    }
}
