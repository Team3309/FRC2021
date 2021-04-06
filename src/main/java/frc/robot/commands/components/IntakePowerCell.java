package frc.robot.commands.components;

import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DriverStation;

public class IntakePowerCell extends CommandBase{

    public IntakeSubsystem intake;

    public IntakePowerCell(IntakeSubsystem intake) {
        intake = this.intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        intake.intakePowerCells();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return DriverStation.getInstance().isAutonomousEnabled();
    }
}