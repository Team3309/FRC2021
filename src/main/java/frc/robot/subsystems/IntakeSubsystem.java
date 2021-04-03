package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Constants;

public class IntakeSubsystem {
    
    private TalonFX intakeMotor;

    public IntakeSubsystem() {
        intakeMotor = new TalonFX(Constants.intakeMotorID);
        intakeMotor.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Intakes power cells for robot use. 
     */
    public void intake() {
        intakeMotor.set(ControlMode.Velocity, Constants.intakeMotorStandardSpeed);
    }

    /**
     * Outtakes (doesn't shoot) power cells.
     */
    public void outtake() {
        intakeMotor.set(ControlMode.Velocity, - Constants.intakeMotorStandardSpeed);
    }

    /**
     * Stops intake entirely.
     */
    public void stop() {
        intakeMotor.set(ControlMode.Velocity, 0);
    }
}
