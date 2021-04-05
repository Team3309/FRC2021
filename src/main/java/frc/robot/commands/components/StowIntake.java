package frc.robot.commands.components;

import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StowIntake extends CommandBase {

    public IntakeSubsystem intake;

    public StowIntake(IntakeSubsystem intake) {
        intake = this.intake;
        addRequirements(intake);
    
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        intake.stow();
    }

    public void end() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
