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
import friarLib2.math.CTREModuleState;
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
        double leftstickX = OperatorInterface.applyDeadband(xbox.getX(GenericHID.Hand.kLeft), Constants.xboxControllerDeadband);
        double leftstickY = OperatorInterface.applyDeadband(xbox.getY(GenericHID.Hand.kLeft), Constants.xboxControllerDeadband);
        double rightstickX = OperatorInterface.applyDeadband(xbox.getX(GenericHID.Hand.kRight), Constants.xboxControllerDeadband);
        double rightstickY = OperatorInterface.applyDeadband(xbox.getY(GenericHID.Hand.kRight), Constants.xboxControllerDeadband);

        double ySpeed = Units.feetToMeters(Constants.maxDriveSpeed * leftstickY * leftstickY);  // positive getY() is down
        ySpeed = (-leftstickY < 0) ? ySpeed * -1 : ySpeed; // Make the output value negative if the joystick value is negative

        double xSpeed = Units.feetToMeters(Constants.maxDriveSpeed * leftstickX * leftstickX);  // positive getX() is to the right
        xSpeed = (-leftstickX < 0) ? xSpeed * -1 : xSpeed;

        double angularSpeed = Units.rotationsPerMinuteToRadiansPerSecond(Constants.maxAngularSpeed * rightstickX * rightstickX);
        angularSpeed = (-rightstickX < 0) ? angularSpeed * -1 : angularSpeed;

        // Point in the direction the joystick is pointing
        if (OperatorInterface.OperatorController.getBumper(GenericHID.Hand.kRight)) {
            double targetAngle = Math.toDegrees(Math.atan2(-leftstickY, leftstickX));
            targetAngle = CTREModuleState.placeInAppropriate0To360Scope(drive.getRobotRotation().getDegrees(), targetAngle);
            SmartDashboard.putNumber("Target heading", targetAngle);
            angularSpeed = Math.toRadians(Constants.robotRotationPID.calculate(drive.getRobotRotation().getDegrees(), targetAngle));
            SmartDashboard.putNumber("Rotational speed", angularSpeed);
        } else {
            //Constants.robotRotationPID.reset(drive.getRobotRotation().getRadians());
        }

        //ChassisSpeeds speeds = new ChassisSpeeds(ySpeed, xSpeed, angularSpeed);
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(ySpeed, xSpeed, angularSpeed, drive.getRobotRotation()); // Create ChassisSpeeds based on robot heading

        drive.setChassisSpeeds(speeds);

        SmartDashboard.putString("speeds", speeds.toString());
    }
}
