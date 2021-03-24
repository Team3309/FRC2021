package frc.robot;

import edu.wpi.first.wpilibj.util.Units;

public class UnitConversions {
    public static double driveMPSToEncoderTicksPer100ms (double mps) {
        double wheelDiameterMeters = Units.inchesToMeters(Constants.wheelDiameterInches);
        return mps * (1/(wheelDiameterMeters * Math.PI)) * (Constants.swerveModuleDriveGearRatio) * (2048/1) * (1/10);
    }

    public static double driveDegreesToEncoderTicks(double degrees) {
        return degrees * (4096.0 / 360.0);
    }
}