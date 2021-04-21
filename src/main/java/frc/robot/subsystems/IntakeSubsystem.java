package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private WPI_VictorSPX intakeMotor;

    public IntakeSubsystem() {
        intakeMotor = new WPI_VictorSPX(Constants.intakeMotorID);
        intakeMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setPower (double power) {
        intakeMotor.set(ControlMode.PercentOutput, power);
    }

    public void startMotor () {
        setPower(Constants.intakeMotorPower);
    }

    public void stopMotor () {
        intakeMotor.stopMotor();
    }
  
    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
}
