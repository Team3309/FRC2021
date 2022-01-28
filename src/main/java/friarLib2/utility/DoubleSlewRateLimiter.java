package friarLib2.utility;

import edu.wpi.first.wpilibj.SlewRateLimiter;

/**
 * Just like WPILib's SlewRateLimiter, but with different limits for
 * acceleration and deceleration.
 */
public class DoubleSlewRateLimiter {
    private SlewRateLimiter accelLimiter;
    private SlewRateLimiter decelLimiter;

    private double lastSpeed;

    public DoubleSlewRateLimiter (double accelLimit, double decelLimit) {
        accelLimiter = new SlewRateLimiter(accelLimit);
        decelLimiter = new SlewRateLimiter(decelLimit);
    }

    public double calculate (double speed) {
        double out;
        if (speed - lastSpeed >= 0) { // If the speed has increased since the last call...
            out = accelLimiter.calculate(speed);
            decelLimiter.reset(speed);
        } else {
            out = decelLimiter.calculate(speed);
            accelLimiter.reset(speed);
        }
        lastSpeed = speed;

        return out;
    }

    public void reset (double value) {
        accelLimiter.reset(value);
        decelLimiter.reset(value);
    }
}
