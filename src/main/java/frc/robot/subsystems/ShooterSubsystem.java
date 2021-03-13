package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import friarLib2.utility.PIDParameters;

public class ShooterSubsystem extends SubsystemBase {

    WPI_TalonFX topFlywheelMotor;
    WPI_TalonFX bottomFlywheelMotor;

    public ShooterSubsystem() {
        topFlywheelMotor = new WPI_TalonFX(Constants.topShooterMotorID);
        bottomFlywheelMotor = new WPI_TalonFX(Constants.bottomShooterMotorID);
        configMotor(topFlywheelMotor, Constants.topFlywheelPID);
        configMotor(bottomFlywheelMotor, Constants.bottomFlywheelPID);
    }

    private static void configMotor(WPI_TalonFX motor, PIDParameters PID) {
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Coast);

        motor.config_kP(0, PID.kP);
        motor.config_kI(0, PID.kI);
        motor.config_kD(0, PID.kD);
    }

    public void setFlywheelSpeeds (int topFlywheelSpeed, int bottomFlywheelSpeed) {
        topFlywheelMotor.set(ControlMode.Velocity, topFlywheelSpeed);
        bottomFlywheelMotor.set(ControlMode.Velocity, bottomFlywheelSpeed);
    }

    public void stopMotors() {
        topFlywheelMotor.stopMotor();
        bottomFlywheelMotor.stopMotor();
    }

}
