package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveTeleop extends CommandBase {
  private final DriveSubsystem drive;
  private Joystick leftStick = OperatorInterface.DriverLeft;
  private Joystick rightStick = OperatorInterface.DriverRight;
  private XboxController xbox = OperatorInterface.OperatorController;

  public DriveTeleop (DriveSubsystem drive) {
    this.drive = drive;

    addRequirements(drive);
  }

  @Override
  public void execute() {
    //Get joystick values, but with deadband
    double leftstickX = (Math.abs(xbox.getX(GenericHID.Hand.kLeft)) > Constants.xboxControllerDeadband) ? xbox.getX(GenericHID.Hand.kLeft): 0;
    double leftstickY = (Math.abs(xbox.getY(GenericHID.Hand.kLeft)) > Constants.xboxControllerDeadband) ? xbox.getY(GenericHID.Hand.kLeft): 0;
    double rightstickX = (Math.abs(xbox.getX(GenericHID.Hand.kRight)) > Constants.xboxControllerDeadband) ? xbox.getX(GenericHID.Hand.kRight): 0;
    double rightstickY = (Math.abs(xbox.getY(GenericHID.Hand.kRight)) > Constants.xboxControllerDeadband) ? xbox.getY(GenericHID.Hand.kRight): 0;

    double ySpeed = (-leftstickY * Constants.maxDriveSpeed) / 3.281;  // positive getY() is down
    double xSpeed = (-leftstickX * Constants.maxDriveSpeed) / 3.281;  // positive getX() is to the right
    double angularSpeed = Units.rotationsPerMinuteToRadiansPerSecond(rightstickX * Constants.maxAngularSpeed);

    //ChassisSpeeds speeds = new ChassisSpeeds(ySpeed, xSpeed, angularSpeed);
    ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(ySpeed, xSpeed, angularSpeed, drive.getRobotRotation());

    drive.setChassisSpeeds(speeds);

    SmartDashboard.putString("speeds", speeds.toString());
  }
}
