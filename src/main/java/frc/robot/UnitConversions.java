package frc.robot;

import edu.wpi.first.wpilibj.util.Units;

public class UnitConversions {

    
    public static double driveMPSToEncoderTicksPer100ms (double mps) {
        double wheelDiameterMeters = Units.inchesToMeters(Constants.wheelDiameterInches);
        return mps * (1.0/(wheelDiameterMeters * Math.PI)) * Constants.swerveModuleDriveGearRatio * (2048.0/1.0) * (1.0/10.0);
        //return 4290;
    }

    public static double driveDegreesToEncoderTicks(double degrees) {
        return degrees * (2048.0 / 360.0) * ((100.0 / 24.0) * (48.0/16.0));
    }
}