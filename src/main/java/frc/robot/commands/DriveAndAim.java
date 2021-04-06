package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.Vision;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * Drive relative to the field while aiming to the target by adjusing the angle of the shooter and rotsationg the drivebase
 */
public class DriveAndAim extends CommandBase {

    private final DriveSubsystem drive;
    private final ShooterSubsystem shooter;

    private XboxController xbox = OperatorInterface.OperatorController;

    public DriveAndAim(DriveSubsystem drive, ShooterSubsystem shooter) {
      this.drive = drive;
      this.shooter = shooter;
      addRequirements(drive, shooter);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      shooter.setFlywheelSpeeds(Constants.topFlywheelSpeed, Constants.bottomFlyWheelSpeed);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      double leftstickX = OperatorInterface.applyDeadband(xbox.getX(GenericHID.Hand.kLeft), Constants.xboxControllerDeadband);
      double leftstickY = OperatorInterface.applyDeadband(xbox.getY(GenericHID.Hand.kLeft), Constants.xboxControllerDeadband);
      double rightstickX = OperatorInterface.applyDeadband(xbox.getX(GenericHID.Hand.kRight), Constants.xboxControllerDeadband);
      double rightstickY = OperatorInterface.applyDeadband(xbox.getY(GenericHID.Hand.kRight), Constants.xboxControllerDeadband);

      //shooter.aim(); //Set the angle of the shooter based on distance from target

      double forwardSpeed = (-leftstickY * Constants.maxDriveSpeed) / 3.281;  // positive getY() is down
      double sidewaysSpeed = (leftstickX * Constants.maxDriveSpeed) / 3.281;  // positive getX() is to the right
      double angularSpeed = 0;//Constants.driveAimPID.calculate(Vision.targetCam.getBestTarget().getX(), 0);

      //Set drive speeds relative to field
      ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(forwardSpeed, sidewaysSpeed, angularSpeed, drive.getRobotRotation());

      drive.setChassisSpeeds(speeds);
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      shooter.stopMotors();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
  }
  