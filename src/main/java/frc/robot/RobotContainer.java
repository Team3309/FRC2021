/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.components.*;
import frc.robot.commands.Autos.*;
import frc.robot.subsystems.*;
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
  private final IntakeSubsystem intake = new IntakeSubsystem();

  private SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  private final BouncePathAuto bounceAuto = new BouncePathAuto(drive);
  private final FollowTrajectory slalomAuto = new FollowTrajectory(drive, "slalomLeg.wpilib.json");
  private final FollowTrajectory barrelAuto = new FollowTrajectory(drive, "barrelRun.wpilib.json");
  private final GSCA gsca = new GSCA(drive, intake);
  private final GSCB gscb = new GSCB(drive, intake);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    autoChooser.addOption("Bounce Path", bounceAuto);
    autoChooser.addOption("Slalom Path", slalomAuto);
    autoChooser.setDefaultOption("Barrel Run", barrelAuto);
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
    //When right bumper is pressed on Xbox controller, toggle DriveAndAim
    new JoystickButton(OperatorInterface.OperatorController, XboxController.Button.kBumperRight.value)
        .toggleWhenPressed(new DriveAndAim(drive, shooter));

    //When right trigget is pressed on Xbox controller, launch a powercell
    new JoystickButton(OperatorInterface.OperatorController, XboxController.Axis.kRightTrigger.value)
        .whenPressed(new InstantCommand(shooter::shoot, shooter));

    new JoystickButton(OperatorInterface.OperatorController, XboxController.Button.kA.value).whileHeld(new SetModuleStates(drive));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
