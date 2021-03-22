/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
<<<<<<< HEAD
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.setShooterToIdle;
=======
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveAndAim;
>>>>>>> 36db5c99832d714adb2a8c71051598b495374f02
import frc.robot.commands.DriveTeleop;
import frc.robot.commands.Autos.*;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterPrototypeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem drive = new DriveSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();

<<<<<<< HEAD
=======
  private SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  private final BouncePathAuto bounceAuto = new BouncePathAuto(drive);
  private final FollowTrajectory slalomAuto = new FollowTrajectory(drive, "paths/slalomLeg.wpilib.json");
  private final FollowTrajectory barrelAuto = new FollowTrajectory(drive, "paths/barrelRun.wpilib.json");
  private final GSCA gsca = new GSCA(drive);
  private final GSCB gscb = new GSCB(drive);
>>>>>>> 36db5c99832d714adb2a8c71051598b495374f02

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    autoChooser.setDefaultOption("Bounce Path", bounceAuto);
    autoChooser.addOption("Slalom Path", slalomAuto);
    autoChooser.addOption("Barrel Run", barrelAuto);
    autoChooser.addOption("GSCA", gsca);
    autoChooser.addOption("GSCB", gscb);
    SmartDashboard.putData(autoChooser);

    configureDefaultCommands();
    configureButtonBindings();
  }

  private void configureDefaultCommands () {
     drive.setDefaultCommand(new DriveTeleop(drive));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
<<<<<<< HEAD
      
    
=======
    //When right bumper is pressed on Xbox controller, toggle DriveAndAim
    new JoystickButton(OperatorInterface.OperatorController, XboxController.Button.kBumperRight.value)
        .toggleWhenPressed(new DriveAndAim(drive, shooter));

    //When right trigget is pressed on Xbox controller, launch a powercell
    new JoystickButton(OperatorInterface.OperatorController, XboxController.Axis.kRightTrigger.value)
        .whenPressed(new InstantCommand(shooter::shoot, shooter));
>>>>>>> 36db5c99832d714adb2a8c71051598b495374f02
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
<<<<<<< HEAD
    // TODO: add a widget to smartDashboard/shuffleboard to choose which auto to run
    return null;
=======
    return autoChooser.getSelected();
>>>>>>> 36db5c99832d714adb2a8c71051598b495374f02
  }
}
