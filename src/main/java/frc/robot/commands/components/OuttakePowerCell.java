package frc.robot.commands.components;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class OuttakePowerCell extends CommandBase {
    
    public IntakeSubsystem intake;

    public OuttakePowerCell(IntakeSubsystem intake) {
        intake = this.intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        intake.outtakePowerCells();
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
