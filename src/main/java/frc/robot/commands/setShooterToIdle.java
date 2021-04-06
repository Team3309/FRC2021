package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class setShooterToIdle extends CommandBase {

    ShooterSubsystem shooter;

    public setShooterToIdle(ShooterSubsystem selectedShooter) {
        shooter = selectedShooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        SmartDashboard.putBoolean("AButton Pressed:", false);
        shooter.stopMotors();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
