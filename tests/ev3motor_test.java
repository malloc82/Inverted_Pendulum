import static java.lang.System.out;
import java.lang.Object;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class ev3motor_test {
    Object obj;
    public void motor_test() {

        out.println("setting parameters ...");
        RegulatedMotor Motor_r = Motor.A;
        out.println("here");
        Motor_r.resetTachoCount();
        Motor_r.rotateTo(0);
        // Motor_r.setSpeed(200);
        // Motor_r.setAcceleration(800);
        // out.println("start rotating ...");
        Motor_r.setAcceleration(20);
        Motor_r.forward();
        Delay.msDelay(10000);

        out.println("backward rotation");

        Motor_r.setAcceleration(20);
        Motor_r.backward();
        Delay.msDelay(50000);
        
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
