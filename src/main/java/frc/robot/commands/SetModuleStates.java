package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetModuleStates extends CommandBase {
  private final DriveSubsystem drive;
  private Joystick leftStick = OperatorInterface.DriverLeft;
  private Joystick rightStick = OperatorInterface.DriverRight;
  private XboxController xbox = OperatorInterface.OperatorController;

  public SetModuleStates (DriveSubsystem drive) {
    this.drive = drive;

    addRequirements(drive);
  }

  @Override
  public void execute() {
    double leftstickX = xbox.getX(GenericHID.Hand.kLeft);
    double leftstickY = xbox.getY(GenericHID.Hand.kLeft);
    double rightstickX = xbox.getX(GenericHID.Hand.kRight);
    double rightstickY = xbox.getY(GenericHID.Hand.kRight);

    SwerveModuleState[] states = {
        new SwerveModuleState(1, new Rotation2d(Units.degreesToRadians(90))), 
        new SwerveModuleState(0, new Rotation2d(Units.degreesToRadians(0)))
    };

    drive.setModuleStates(states);
  }
}
