package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import friarLib2.hardware.SwerveModule;
import friarLib2.math.CTREModuleState;
import friarLib2.utility.PIDParameters;

/**
 * Represents the 2022 revision of our swerve module.
 * 
 * <p>
 * This year's version features an improved powertrain for the drive motor
 * (reduced slippage through the use of 5mm GT2 belts) and a CTRE CANCoder
 * on the steering axis. In addition, the drivetrain has been geared up
 * to 23 ft/s (~7 m/s).
 * 
 * <p>
 * The CANCoder is used to dectect any slippage in the module's steering 
 * axis. It is found by comparing the Falcon 500's integrated encoder value to
 * that of the CANCoder. If there is a difference, then the robot knows that 
 * the belts have slipped and can alert the operators accordingly.
 * 
 * <p>
 * To zero the module, we use the CANCoder's absolute positioning to set
 * the Falcon encoder's value. The CANCoder's absolute reading when the 
 * module is facing zero degrees is stored in the encoder's "custom slot" at
 * index zero. Since that slot only stores a 32 bit integer, the actual value
 * we store there is the magnet offest multiplied by 100. Such multiplication
 * allows us to gain two decimal places of precision (which is plenty for this
 * task) from that integer slot.
 */
public class SwerveModule2022 implements SwerveModule {
    /********** Constants **********/
    public static final double WHEEL_DIAMETER_INCHES = 3.8;
    public static final double DRIVE_GEAR_RATIO = (60. / 20.) * (16. / 34.) * (45. / 15.);
    public static final double STEERING_GEAR_RATIO_FALCON = (100. / 24.) * (48. / 16.); // Gear ratio between the Falcon's shaft and the steering axis
    public static final double STEERING_GEAR_RATIO_ENCODER = (1. / 1.); // Gear ratio between the CANCoder and the steering axis
    public static final double SLIP_THRESHOLD = 5; // Steering axis will be considered to have slipped if the 

    public static final PIDParameters DRIVE_PID_GAINS = new PIDParameters(.1, 0.0007, 0.1);
    public static final PIDParameters STEERING_PID_GAINS = new PIDParameters(.1, 0.002, 0);
    public static final double ABSOLUTE_MAX_DRIVE_SPEED = 7; // meters/sec

    /********** Member Variables **********/
    public String name; // Used for diplaying values on SmartDashboard
    private WPI_TalonFX driveMotor;
    private WPI_TalonFX steeringMotor;
    private CANCoder steeringEncoder;

    /** 
     * How many degrees the steering axis must be at (relative to its zeroed
     * position) to be facing forward on the robot. Modules on different
     * corners of a drivetrain will have different offsets
     */
    private final double steeringOffset;

    private double lastAngle = 0.0;

    public SwerveModule2022 (double steeringOffset, int driveMotorID, int steeringMotorID, int encoderID, String name) {
        this.name = name;
        driveMotor = new WPI_TalonFX(driveMotorID);
        steeringMotor = new WPI_TalonFX(steeringMotorID);
        configMotors();

        this.steeringOffset = steeringOffset;

        // Initialize the encoder
        // DO NOT configure the factory defaults. Doing so will reset the manually tuned
        // magnet offsets stored in the encoder's "custom slot"
        steeringEncoder = new CANCoder(encoderID);
        steeringEncoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);
        steeringEncoder.configMagnetOffset(0);

        zeroSteering();
    }

    /**
     * Sets the motor PID values to those which will make the robot move the way we want.
     */
    public void configMotors () {
        driveMotor.configFactoryDefault();
        PIDParameters.configureMotorPID(driveMotor, DRIVE_PID_GAINS);
        driveMotor.config_IntegralZone(0, 500);

        steeringMotor.configFactoryDefault();
        PIDParameters.configureMotorPID(steeringMotor, STEERING_PID_GAINS);
        steeringMotor.config_IntegralZone(0, 500);
    }

    /**
     * Set the state of the swerve module. Credit to team 364 for CTREModuleState
     * 
     * @param state The new target state for the module
     */
    public void setState (SwerveModuleState state) {
        
        state = CTREModuleState.optimize(state, getState().angle);

        double velocity = Conversions.mpsToEncoderTicksPer100ms(state.speedMetersPerSecond);
        driveMotor.set(ControlMode.Velocity, velocity);

        double angle = (Math.abs(state.speedMetersPerSecond) <= (ABSOLUTE_MAX_DRIVE_SPEED * 0.01)) ? lastAngle : state.angle.getDegrees(); // Prevent rotating module if speed is less than 1%. Prevents Jittering.
        steeringMotor.set(ControlMode.Position, Conversions.degreesToEncoderTicksFalcon(angle)); 
        lastAngle = angle;
    }

    /**
     * Get the physical position of the module
     * 
     * @return The module's position
     */
    public SwerveModuleState getState () {
        return new SwerveModuleState(
            Conversions.encoderTicksPer100msToMps(driveMotor.getSelectedSensorVelocity()), 
            Rotation2d.fromDegrees(getSteeringDegreesFromFalcon())
        );
    }

    /**
     * @return If the belts for the steering axis hav slipped
     */
    @Override
    public boolean steeringHasSlipped () {
        return Math.abs(
            getSteeringDegreesFromFalcon() -
            getSteeringDegreesFromEncoder()
            ) >= SLIP_THRESHOLD;
    }

    /**
     * Use the CANCoder (whoose belt will not slip under normal circumstances)
     * to reset the steering Falcon's intgrated encoder.
     */
    @Override
    public void zeroSteering () {
        double encoderOffset = getMagnetOffsetFromCANCoderSlot() + steeringOffset;

        double absolutePosition = steeringEncoder.getAbsolutePosition() - encoderOffset;
        double absolutePositionFalcon = Conversions.degreesToEncoderTicksFalcon(absolutePosition);
        double absolutePositionEncoder = Conversions.degreesToEncoderTicksCANCoder(absolutePosition);
        steeringMotor.setSelectedSensorPosition(absolutePositionFalcon);
        steeringEncoder.setPosition(absolutePositionEncoder);
    }

    /**
     * Gets the value stored in the CANCoder's custom paramter slot zero,
     * divided by 100.
     * 
     * <p>
     * That slot, if configured correctly, should contain the absolute encoder
     * reading when the swerve module's steering axis is at zero degrees, 
     * multiplied by 100.
     * 
     * <p>
     * To configure it correctly, open OutlineViewer and, under the 
     * "SmartDahsboard" table, there should be a numeric field called 
     * "{module name} CANCoder absolute value". With the robot disabled, move
     * the steering axis of the module to be at the zero degrees position, then
     * note the aforementioned dashboard value, times 100. Finally, use Phoenix
     * Tuner to set the "Custom Param 0" field on the appropriate encoder 
     * (under the "Config" tab).
     */
    private double getMagnetOffsetFromCANCoderSlot () {
        return steeringEncoder.configGetCustomParam(0) / 100.0;
    }

    /**
     * @return The position of the steering axis according to the Falcon's encoder
     */
    private double getSteeringDegreesFromFalcon () {
        return Conversions.encoderTicksToDegreesFalcon(steeringMotor.getSelectedSensorPosition());
    }

    /**
     * @return The position of the steering axis according to the CANCoder
     */
    private double getSteeringDegreesFromEncoder () {
        return Conversions.encoderTicksToDegreesCANCoder(steeringEncoder.getPosition());
    }

    @Override
    public void outputToDashboard () {
        SmartDashboard.putNumber(name + " CANCoder absolute value", steeringEncoder.getAbsolutePosition());
        SmartDashboard.putNumber(name + " CANCoder raw value", steeringEncoder.getPosition());
        SmartDashboard.putNumber(name + " CANCoder degrees", getSteeringDegreesFromEncoder());
        SmartDashboard.putNumber(name + " Falcon degrees", getSteeringDegreesFromFalcon());
        SmartDashboard.putNumber(name + " Falcon raw value", steeringMotor.getSelectedSensorPosition());
        SmartDashboard.putBoolean(name + " has slipped", steeringHasSlipped());
        SmartDashboard.putNumber(name + " magnet offset", getMagnetOffsetFromCANCoderSlot());
    }

    /**
     * Unit conversions for the swerve module
     */
    public static class Conversions {
        public static double mpsToEncoderTicksPer100ms (double mps) {
            double wheelDiameterMeters = Units.inchesToMeters(WHEEL_DIAMETER_INCHES);
            return mps * (1.0/(wheelDiameterMeters * Math.PI)) * DRIVE_GEAR_RATIO * (2048.0/1.0) * (1.0/10.0);
        }

        public static double encoderTicksPer100msToMps (double encoderTicksPer100ms) {
            return encoderTicksPer100ms / mpsToEncoderTicksPer100ms(1);
        }

        public static double degreesToEncoderTicksFalcon (double degrees) {
            return degrees * (2048.0 / 360.0) * STEERING_GEAR_RATIO_FALCON;
        }

        public static double encoderTicksToDegreesFalcon (double encoderTicks) {
            return encoderTicks / degreesToEncoderTicksFalcon(1);
        }

        public static double degreesToEncoderTicksCANCoder (double degrees) {
            return degrees * STEERING_GEAR_RATIO_ENCODER;
        }

        public static double encoderTicksToDegreesCANCoder (double encoderTicks) {
            return encoderTicks / degreesToEncoderTicksCANCoder(1);
        }
    }
}