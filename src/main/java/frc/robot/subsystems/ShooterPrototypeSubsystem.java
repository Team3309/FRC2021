package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Constants;

public class ShooterPrototypeSubsystem extends SubsystemBase{

    TalonFX topWheel;
    TalonFX bottomWheel;
    private static boolean configured;

    public ShooterPrototypeSubsystem() {
        topWheel = new TalonFX(Constants.topShooterMotorID);
        bottomWheel = new TalonFX(Constants.bottomShooterMotorID);
        configured = false;
        configMotors(topWheel, bottomWheel);
    }

    private static void configMotors(TalonFX topMotor, TalonFX bottomMotor) {
        if (!configured) {
        
        topMotor.configFactoryDefault();
        bottomMotor.configFactoryDefault();
        topMotor.setNeutralMode(NeutralMode.Brake);
        bottomMotor.setNeutralMode(NeutralMode.Brake);

        topMotor.config_kP(Constants.topShooterMotorID, Constants.topShooterMotor_kP);
        topMotor.config_kI(Constants.topShooterMotorID, Constants.topShooterMotor_kI);
        topMotor.config_kD(Constants.topShooterMotorID, Constants.topShooterMotor_kD);
        topMotor.config_kD(Constants.topShooterMotorID, Constants.topShooterMotor_kF);

        bottomMotor.config_kP(Constants.bottomShooterMotorID, Constants.bottomShooterMotor_kP);
        bottomMotor.config_kI(Constants.bottomShooterMotorID, Constants.bottomShooterMotor_kI);
        bottomMotor.config_kD(Constants.bottomShooterMotorID, Constants.bottomShooterMotor_kD);
        bottomMotor.config_kF(Constants.bottomShooterMotorID, Constants.bottomShooterMotor_kF);

        configured = true;
        }
        
    }

    public void shootStraight(double velocity) {
        topWheel.set(ControlMode.Velocity, velocity);
        bottomWheel.set(ControlMode.Velocity, -velocity);
    }
    
    public void shootWithSpin(double topVelocity, double bottomVelocity) {
        topWheel.set(ControlMode.Velocity, topVelocity);
        bottomWheel.set(ControlMode.Velocity, bottomVelocity);
    }

    public void stop() {
        topWheel.set(ControlMode.Velocity, 0);
        bottomWheel.set(ControlMode.Velocity, 0);
    }


}
