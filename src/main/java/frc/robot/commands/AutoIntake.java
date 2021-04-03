package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoIntake extends CommandBase {
    
    public IntakeSubsystem intake;

    public AutoIntake(IntakeSubsystem intake) {
        intake = this.intake;
        addRequirements(intake);
    }

    @Override

    public void initialize() {
        
    }

    @Override
    public void execute() {}

    
    public void end() {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
