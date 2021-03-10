package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterPrototypeSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * This class is for prototyping only
 */
public class temporaryShootCommand extends CommandBase {
    
    private final ShooterPrototypeSubsystem shooter;
    private final XboxController controller;

    public temporaryShootCommand (ShooterPrototypeSubsystem selectedShooter, XboxController opController) {
        shooter = selectedShooter;
        controller = opController;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
            SmartDashboard.putBoolean("AButton Pressed:", true);
        double topWheelSpeed = controller.getX(XboxController.Hand.kLeft);
        double bottomWheelSpeed = controller.getY(XboxController.Hand.kRight);
        shooter.shootWithSpin(topWheelSpeed * Constants.shooterMotorModulation, 
            bottomWheelSpeed * Constants.shooterMotorModulation);
    }


    @Override
    public boolean isFinished() {
        return false;
    }
}
