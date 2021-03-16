package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveTeleop extends CommandBase {
  DriveSubsystem drive;
  public Joystick leftStick;
  public Joystick rightStick;

  public DriveTeleop (DriveSubsystem drive) {
    this.drive = drive;
    Joystick leftStick = OperatorInterface.DriverLeft;
    Joystick rightStick = OperatorInterface.DriverRight;

    double forwardSpeed = (-leftStick.getY() * Constants.maxDriveSpeed) / 3.281;  // positive getY() is down
    double sidewaysSpeed = (leftStick.getX() * Constants.maxDriveSpeed) / 3.281;  // positive getX() is to the right
    double angularSpeed = Units.rotationsPerMinuteToRadiansPerSecond(-rightStick.getX() * Constants.maxAngularSpeed);

    ChassisSpeeds speeds = new ChassisSpeeds(forwardSpeed, sidewaysSpeed, angularSpeed);

    drive.setChassisSpeeds(speeds);
  }
}
