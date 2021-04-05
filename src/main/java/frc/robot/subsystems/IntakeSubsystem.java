package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase{
    
    private TalonFX intakeMotor;
    private TalonFX stowMotor;

    public IntakeSubsystem() {
        intakeMotor = new TalonFX(Constants.intakeMotorID);
        intakeMotor.setNeutralMode(NeutralMode.Brake);
        stowMotor = new TalonFX(Constants.stowMotorID);
        stowMotor.setNeutralMode(NeutralMode.Brake);
    }


    /**
     * Temporary method for deploying intake from stow position.
     */

    public void deploy() {
        stowMotor.set(ControlMode.Velocity, Constants.stowMotorStandardSpeed);
    }

    /**
     * Temporary method for stowing intake from deployed position
     */

    public void stow() {
        stowMotor.set(ControlMode.Velocity, -Constants.stowMotorStandardSpeed);
    }

    /**
     * Intakes power cells for robot use. 
     */
    public void intakePowerCells() {
        intakeMotor.set(ControlMode.Velocity, Constants.intakeMotorStandardSpeed);
    }

    /**
     * Outtakes, but doesn't shoot, power cells.
     */
    public void outtakePowerCells() {
        intakeMotor.set(ControlMode.Velocity, - Constants.intakeMotorStandardSpeed);
    }

    /**
     * Stops intake entirely.
     */
    public void stop() {
        intakeMotor.set(ControlMode.Velocity, 0);
    }
}
