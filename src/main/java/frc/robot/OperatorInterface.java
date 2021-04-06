package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class OperatorInterface
{
    // -- Driver
    public static Joystick DriverLeft = new Joystick(0);
    public static Joystick DriverRight = new Joystick(1);


    public static ClusterGroup leftStickLeftCluster = new ClusterGroup(DriverLeft, GenericHID.Hand.kLeft);
    public static ClusterGroup leftStickRightCluster = new ClusterGroup(DriverLeft, GenericHID.Hand.kLeft);
    public ClusterGroup rightStickLeftCluster = new ClusterGroup(DriverRight, GenericHID.Hand.kRight);
    public ClusterGroup rightStickRightCluster = new ClusterGroup(DriverRight, GenericHID.Hand.kRight);


    public static final int LEFT_CLUSTER_1_ID = 11;
    public static final int LEFT_CLUSTER_2_ID = 12;
    public static final int LEFT_CLUSTER_3_ID = 13;
    public static final int LEFT_CLUSTER_4_ID = 14;
    public static final int LEFT_CLUSTER_5_ID = 15;
    public static final int LEFT_CLUSTER_6_ID = 16;
    public static final int RIGHT_CLUSTER_1_ID = 5;
    public static final int RIGHT_CLUSTER_2_ID = 6;
    public static final int RIGHT_CLUSTER_3_ID = 7;
    public static final int RIGHT_CLUSTER_4_ID = 8;
    public static final int RIGHT_CLUSTER_5_ID = 9;
    public static final int RIGHT_CLUSTER_6_ID = 10;

    // -- Operator
    public static XboxController OperatorController = new XboxController(2);

    //needs to be public or it hides all the trigger functionality such as whenActive,
    //which essentially disables the ClusterGroup.
    public static class ClusterGroup extends Trigger {

        Joystick stick;
        GenericHID.Hand side;

        ClusterGroup(Joystick stick, GenericHID.Hand side) {
            this.stick = stick;
            this.side = side;
        }

        @Override
        public boolean get() {
            if (side == GenericHID.Hand.kLeft) {
                return stick.getRawButton(LEFT_CLUSTER_1_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_2_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_3_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_4_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_5_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_6_ID);
            } else {
                return stick.getRawButton(RIGHT_CLUSTER_1_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_2_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_3_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_4_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_5_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_6_ID);
            }
        }
    }

    /**
     * Return zero if the absolute value of joystickValue is less than deadband, else, return joystickValue.
     * Ueseful for ensuring a zero value when a joystick is released.
     * 
     * @param joystickValue the value of the joystick axis
     * @param deadband the deadband to apply
     * @return the adjusted joystickValue
     */
    public static double applyDeadband (double joystickValue, double deadband) {
        return (Math.abs(joystickValue) > deadband) ? joystickValue : 0;
    }
}