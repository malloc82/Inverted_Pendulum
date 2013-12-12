import static java.lang.System.out;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Arrays;

import java.lang.Float;
import java.lang.Math;

import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3SensorConstants;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.UARTPort;
import lejos.robotics.SampleProvider;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.utility.Integration;

public class Pendulum {
    // private static final int ANGLE_MODE = 1;
    // private static final int RATE_MODE = 0;

    private float desired_angle;

    private HashMap<String, Float> PID_params = 
        new HashMap<String, Float>();
    private Port[] sensor_ports = {SensorPort.S1, 
                                   SensorPort.S2, 
                                   SensorPort.S3, 
                                   SensorPort.S4};

    private int port_angle = -1;
    private int port_rate  = -1;

    private Gyro gyro_angle;
    private Gyro gyro_rate;
    private Integration angle_error_int;

    private RegulatedMotor leftMotor;
    private RegulatedMotor rightMotor;

    public static void main(String[] args) {
        Pendulum pendulum = new Pendulum(args);

        out.println("****************************************************");
        out.println("*                                                  *");
        out.println("*    Initializing sensors and motors ...           *");
        out.println("*                                                  *");
        out.println("****************************************************");
        Delay.msDelay(1000);
        pendulum.init();

        out.println("****************************************************");
        out.println("*                                                  *");
        out.println("*    PID_controller start in ...                   *");
        out.println("*                                                  *");
        out.println("****************************************************");
        out.println("3");
        Delay.msDelay(1000);
        out.println("2");
        Delay.msDelay(1000);
        out.println("1");
        Delay.msDelay(1000);
        out.println("Now");
        pendulum.PID_controller();
    }

    public Pendulum (String[] args) {
        desired_angle = 0;

        PID_params.put("Kp", new Float(1.0));
        PID_params.put("Kd", new Float(1.0));
        PID_params.put("Ki", new Float(1.0));
        for (int i=0; i < args.length; ++i) {
            out.println(args[i]);
            String[] tokens = args[i].split("=");
            out.println(tokens[0]);
            if (tokens.length == 2) {
                if (tokens[0].equals("Kp")) {
                    out.println("here");
                    PID_params.put("Kp", 
                                   Float.parseFloat(tokens[1]));
                } else if (tokens[0].equals("Kd")) { 
                    PID_params.put("Kd", 
                                   Float.parseFloat(tokens[1]));
                } else if (tokens[0].equals("Ki")) { 
                    PID_params.put("Ki", 
                                   Float.parseFloat(tokens[1]));
                } else if (tokens[0].equals("angle")) {
                    port_angle = Integer.parseInt(tokens[1]);
                } else if (tokens[0].equals("rate")) {
                    port_rate = Integer.parseInt(tokens[1]);
                }
            }
        }
        if (port_angle < 0) {
            out.println("Do not have gyro angle sensor information ... quit.");
            return;
        }
        if (port_rate < 0) {
            out.println("Do not have gyro rate sensor information ... quit.");
            return;
        }

        out.println("PID parameters are: ");
        out.println("    Kp = " + 
                    Float.toString(PID_params.get("Kp")));
        out.println("    Kd = " + 
                    Float.toString(PID_params.get("Kd")));
        out.println("    Ki = " + 
                    Float.toString(PID_params.get("Ki")));
        out.println("");

        out.println("Gyro angle port = " + 
                    String.valueOf(port_angle));
        out.println("Gyro rate port  = " + 
                    String.valueOf(port_rate));
    }

    public void init() {
        out.println("Initializing gyro sensors ... ");
        boolean angle_condition = false;
        float   current_angle   = 0;
        gyro_angle = new Gyro(sensor_ports[port_angle], Gyro.MODE.ANGLE_MODE);
        gyro_rate  = new Gyro(sensor_ports[port_rate],  Gyro.MODE.RATE_MODE);        
        out.println("");
        out.println("*****************************************");
        out.println("*   Testing Gyro Sensor Sampling        *");
        out.println("*****************************************");
        out.println("");
        while (!angle_condition) {
            current_angle = (gyro_angle.getSamples())[0];
            Delay.msDelay(5000);
            // while (System.currentTimeMillis() <= curr_time + interval);
            if ((gyro_angle.getSamples())[0] - current_angle != 0)  {
                out.println("");
                out.println("*****************************************");
                out.println("*   Angle sampling faulty.              *");
                out.println("*   Reinitializing angle gyro sensor.   *");
                out.println("*****************************************");
                out.println("");
                out.println("3");
                Delay.msDelay(1000);
                out.println("2");
                Delay.msDelay(1000);
                out.println("1");
                Delay.msDelay(1000);
                // reseting gyro sensors
                gyro_angle.reset();
                gyro_rate.reset();
            } else {
                out.println("");
                out.println("*****************************************");
                out.println("*       Angle sampling is ok.           *");
                out.println("*****************************************");
                out.println("");
                angle_condition = true;
            }
        }
        out.println("done.");        

        out.println("Initializing motors ... ");
        leftMotor = Motor.D;
        rightMotor = Motor.A;
        leftMotor.resetTachoCount();
        rightMotor.resetTachoCount();
        leftMotor.rotateTo(0);
        rightMotor.rotateTo(0);
        out.println("done.");
        
    }

    public int radian2degree(float radian) {
        return (int)(radian / 180.0 * 3.1415926 + 0.5);
    }

    public void PID_controller() {

        float Kd = PID_params.get("Kd");
        float Kp = PID_params.get("Kp");
        float Ki = PID_params.get("Ki");

        float error = 0;
        float curr_angle = (gyro_angle.getSamples())[0];
        float curr_rate  = -(gyro_rate.getSamples())[0];

        error = (desired_angle - curr_angle);
        angle_error_int = new Integration(0, error);
        
        while (true) {
            float integral = (float)angle_error_int.addReading(error);
            int acceleration = radian2degree(Kp*error + Kd*(curr_rate) + Ki*integral);

            out.println(String.valueOf(System.currentTimeMillis()) + 
                        ":   angle = " + String.valueOf(curr_angle) + 
                        ", rate = " + String.valueOf(curr_rate) + 
                        ", acceleration = " + String.valueOf(acceleration) + 
                        ", integral_error = " + String.valueOf(integral));

            if ( acceleration > 0 ) {
                leftMotor.setAcceleration(acceleration);
                rightMotor.setAcceleration(acceleration);
                leftMotor.forward();
                rightMotor.forward();
            } else if ( acceleration < 0 ) {
                leftMotor.setAcceleration(-acceleration);
                rightMotor.setAcceleration(-acceleration);
                leftMotor.backward();
                rightMotor.backward();
            } else {
                // not sure 
            }
            curr_angle =  (gyro_angle.getSamples())[0];
            curr_rate  = -(gyro_rate.getSamples())[0];
            error = (desired_angle - curr_angle);
        }
    }
}
