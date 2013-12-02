import static java.lang.System.out;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;

public class ev3motor_test {
    public void motor_test() {
        RegulatedMotor Motor_r = Motor.A;
        Motor_r.resetTachoCount();
        Motor_r.rotateTo(0);
        Motor_r.setSpeed(10);
        Motor_r.rotateTo(360);
        out.println("current speed is : " + Motor_r.getSpeed());
    }
    public static void main(String[] args) {
        out.println("motor test start ...");
        new ev3motor_test().motor_test();
        out.println("motor test end.");
    }
}
