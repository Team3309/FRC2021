/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.Units;
import friarLib2.utility.PIDParameters;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final double mu_static = 1.23;
    /********** Drive Control Constants **********/
    public static final double maxDriveSpeed = 14; // ft/s
    public static final double maxAngularSpeed = 20; // rpm

    /********** Drive Tuning Constants **********/
    public static final PIDParameters drivePID = new PIDParameters(0, 0, 0);
    public static final PIDParameters driveRotationPID = new PIDParameters(0, 0, 0);
    public static final PIDController holonomicControllerPID = new PIDController(0, 0, 0);
    public static final ProfiledPIDController holonomicControllerPIDTheta = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));

    /********** Physical Drive Constants **********/
    public static final int frontLeftModuleDriveMotorID = 1;
    public static final int frontLeftModulRotationMotorID = 2;

    public static final int frontRightModuleDriveMotorID = 3;
    public static final int frontRightModuleRotationMotorID = 4;

    public static final int backLeftModuleDriveMotorID = 5;
    public static final int backLeftModuleRotationMotorID = 6;

    public static final int backRightModuleDriveMotorID = 7;
    public static final int backRightModuleRotationMotorID = 8;

    //THESE ARE THE DISTANCES OF EACH OF THE SWERVE MODULES FROM THE CENTER OF THE ROBOT
    //Positive x values represent moving toward the front of the robot
    //Positive y values represent moving toward the left of the robot
    public static final Translation2d frontLeftModuleTranslation = new Translation2d(Units.feetToMeters(1), Units.feetToMeters(1));
    public static final Translation2d frontRightModuleTranslation = new Translation2d(Units.feetToMeters(1), Units.feetToMeters(-1));
    public static final Translation2d backLeftModuleTranslation = new Translation2d(Units.feetToMeters(-1), Units.feetToMeters(1));
    public static final Translation2d backRightModuleTranslation = new Translation2d(Units.feetToMeters(-1), Units.feetToMeters(-1));



    /********** Physical Shooter Constants **********/
    public static final int topShooterMotorID = 9;
    public static final int bottomShooterMotorID = 10;

    /********** Shooter Tuning Constants **********/
    public static PIDParameters topFlywheelPID = new PIDParameters(0, 0, 0);
    public static PIDParameters bottomFlywheelPID = new PIDParameters(0, 0, 0);
}