package frc.robot.commands.Autos;

import java.util.function.BooleanSupplier;

import frc.robot.Vision;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Decides to run either blue or red paths for Galactic Search B
 */
public class GSCB implements BooleanSupplier {
  /**
   * Returns true if red path and false if blue
   * @return
   */
  @Override
  public boolean getAsBoolean() {
    return true;//Vision.ballCam.getBestTarget().getX() < 10; //TODO: make this work
  }
}
  