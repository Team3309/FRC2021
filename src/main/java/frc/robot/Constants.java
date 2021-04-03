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

    public static final double xboxControllerDeadband = .05;

    /********** Drive Control Constants **********/
    public static final double maxDriveSpeed = 12; // ft/s
    public static final double maxAngularSpeed = 100; // rpm

    /********** Drive Tuning Constants **********/
    public static final PIDParameters drivePID = new PIDParameters(.1, 0.001, 0.1);
    public static final PIDParameters driveRotationPID = new PIDParameters(.1, 0.002, 0);
    public static final PIDController holonomicControllerPID = new PIDController(.1, 0, 0);
    public static final ProfiledPIDController holonomicControllerPIDTheta = new ProfiledPIDController(.1, 0, 0, new TrapezoidProfile.Constraints(0, 0));
    public static final PIDController driveAimPID = new PIDController(0, 0, 0); //Controls the rotation of the drivebase when aiming

    /********** Physical Drive Constants **********/
    public static final int leftModuleDriveMotorID = 1;
    public static final int leftModulRotationMotorID = 2;

    public static final int rightModuleDriveMotorID = 5;
    public static final int rightModuleRotationMotorID = 6;

    public static final double wheelDiameterInches = 3.8;
    public static final double swerveModuleDriveGearRatio = 2.94;//(45.0/15.0) * (16.0/34.0) * (36.0/24.0) * (50.0/36.0);

    //THESE ARE THE DISTANCES OF EACH OF THE SWERVE MODULES FROM THE CENTER OF THE ROBOT
    //Positive x values represent moving toward the front of the robot
    //Positive y values represent moving toward the left of the robot
    public static final Translation2d leftModuleTranslation = new Translation2d(0, Units.inchesToMeters(9.4041647005));
    public static final Translation2d rightModuleTranslation = new Translation2d(0, Units.inchesToMeters(-9.4041647005));



    /********** Physical Shooter Constants **********/
    public static final int topShooterMotorID = 9;
    public static final int bottomShooterMotorID = 10;

    /*
     * See https://photos.google.com/u/1/photo/AF1QipN6BZ05ZhEWi89uwusuFR4eUEyHCo-4Ylrg3jJu for more details 
     */
    public static final double shooterTriangleB = 4.825; //in
    public static final double shooterTriangleC = 4.545; //in
    public static final double shooterTriangleA = 6.81; //degrees

    /********** Shooter Tuning Constants **********/
    public static PIDParameters topFlywheelPID = new PIDParameters(0, 0, 0);
    public static PIDParameters bottomFlywheelPID = new PIDParameters(0, 0, 0);
    public static int topFlywheelSpeed = 17000; //Encoder ticks per 100ms
    public static int bottomFlyWheelSpeed = 17000;
    public static int flywheelSpeedTolearace = 100; //Will only shoot powercells if flywheel speed is within this rage of the target speed

    /**
     * A 2D array for tuning the shooter.
     * 
     * The first value in each sub-array is the distance in meters from the goal. The second 
     * one is the angle of the shooter, found through trial and error, to shoot into the goal
     * at that distance.
     * 
     * VisionSubsystem computes a linear regression to fill in the relationship between
     * distance and angle.
     */
    public static double[][] aimRegressionData = {
        {1, 80},
        {2, 70},
        {3, 60}
    };
}