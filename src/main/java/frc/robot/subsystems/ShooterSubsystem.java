package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.UnitConversions;
import frc.robot.Vision;
import friarLib2.math.LinearRegression;
import friarLib2.utility.PIDParameters;

public class ShooterSubsystem extends SubsystemBase {

    private WPI_TalonFX topFlywheelMotor;
    private WPI_TalonFX bottomFlywheelMotor;
    private WPI_TalonSRX linearMotor;
    private WPI_TalonSRX indexerMotor;

    private DigitalInput limitSwitch = new DigitalInput(Constants.shooterLimitSwitchPort);

    private LinearRegression regression;

    public ShooterSubsystem () {
        topFlywheelMotor = new WPI_TalonFX(Constants.topShooterMotorID);
        bottomFlywheelMotor = new WPI_TalonFX(Constants.bottomShooterMotorID);
        configMotor(topFlywheelMotor, Constants.topFlywheelPID);
        configMotor(bottomFlywheelMotor, Constants.bottomFlywheelPID);

        linearMotor = new WPI_TalonSRX(Constants.shooterLinearMotorID);
        configMotor(linearMotor, Constants.shooterLinearMotorPID);

        indexerMotor = new WPI_TalonSRX(Constants.indexerMotorID);
        indexerMotor.setNeutralMode(NeutralMode.Brake);
        
        //Compute a linear regression of distances and shooter angles so that we can aim anywhere
        regression = new LinearRegression(Constants.aimRegressionData);
    }

    private static void configMotor (BaseTalon motor, PIDParameters PID) {
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Coast);

        motor.config_kP(0, PID.kP);
        motor.config_kI(0, PID.kI);
        motor.config_kD(0, PID.kD);
    }

    public void setFlywheelSpeeds (int topFlywheelSpeed, int bottomFlyWheelSpeed) {
        topFlywheelMotor.set(ControlMode.Velocity, topFlywheelSpeed);
        bottomFlywheelMotor.set(ControlMode.Velocity, bottomFlyWheelSpeed);
    }

    public void stopMotors () {
        topFlywheelMotor.stopMotor();
        bottomFlywheelMotor.stopMotor();
    }

    public void setAngleDegrees (double angle) {
        linearMotor.set(ControlMode.Position, UnitConversions.shooterDegreesToEncoderTicks(angle));
    }

    public void setAngleRadians (double angle) {
        setAngleDegrees(Units.radiansToDegrees(angle));
    }

    public boolean isUpToSpeed () {
        return (topFlywheelMotor.getClosedLoopError() <= Constants.flywheelSpeedTolearace) && (bottomFlywheelMotor.getClosedLoopError() <= Constants.flywheelSpeedTolearace);
    }

    public void aim () {
        setAngleDegrees(regression.evaluate(Vision.getDistanceFromTarget()));
    }

    public void setIndexerMotorPower (double power) {
        indexerMotor.set(ControlMode.PercentOutput, power);
    }

    public void stopIndexerMotor () {
        indexerMotor.stopMotor();
    }

    @Override
    public void periodic() {
      SmartDashboard.putNumber("Linear Motor Encoder", linearMotor.getSelectedSensorPosition());
      SmartDashboard.putBoolean("Limit Switch", limitSwitch.get());
    }
}
