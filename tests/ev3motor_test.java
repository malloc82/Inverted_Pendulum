import static java.lang.System.out;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;

public class ev3motor_test {
    public void motor_test() {
        out.println("setting parameters ...");
        RegulatedMotor Motor_r = Motor.A;
        out.println("here");
        Motor_r.resetTachoCount();
        Motor_r.rotateTo(0);
        // Motor_r.setSpeed(200);
        Motor_r.setAcceleration(200);
        // Motor_r.setAcceleration(800);
        // out.println("start rotating ...");
        Motor_r.rotateTo(3600);
        // Motor_r.rotateTo(-3600);
        // int speed = Motor_r.getSpeed();
        // while(speed > 0 && speed != 360) {
        //     out.println("current speed is : " + speed);
        //     speed = Motor_r.getSpeed();
        // //     speed = Motor_r.getSpeed();
        // }
    }
    public static void main(String[] args) {
        out.println("motor test start ...");
        new ev3motor_test().motor_test();
        out.println("motor test end.");
    }
}
