package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterPrototypeSubsystem;

public class setShooterToIdle extends CommandBase {

    ShooterPrototypeSubsystem shooter;

    public setShooterToIdle(ShooterPrototypeSubsystem selectedShooter) {
        shooter = selectedShooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        SmartDashboard.putBoolean("AButton Pressed:", false);
        shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
