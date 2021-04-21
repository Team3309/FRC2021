package frc.robot.commands.Autos;

import java.util.function.BooleanSupplier;

import frc.robot.Vision;

/**
 * Decides to run either blue or red paths for Galactic Search A
 */
public class GSCACondition implements BooleanSupplier {
  /**
   * Returns true if red path and false if blue
   * @return
   */
  @Override
  public boolean getAsBoolean() {
    return true;//Vision.ballCam.getBestTarget().getX() < 10; //TODO: make this work
  }
}
  