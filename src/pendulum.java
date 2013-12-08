import static java.lang.System.out;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Arrays;

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

public class pendulum {
    private static float desired_angle;
    private static Integration angle_error_int;
    private static Derivative  angle_error_dev = 0;

    private static HashMap<String, Float> PID_params = new HashMap<String, Float>();
    private static int[] gyro_ports;
    private static Port[] port = {SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4};
        
    private static EV3GyroSensor[]  gyro;
    private static SampleProvider[] angle_sampler;
    private static int[] sampleSize = new int[2];

    private static RegulatedMotor leftMotor  = Motor.D;
    private static RegulatedMotor rightMotor = Motor.A;

    public static void main(String[] args) {

        desired_angle = 0;

        PID_params.put("Kp", 1.0);
        PID_params.put("Kd", 1.0);
        PID_params.put("Ki", 1.0);
        for (int i=0; i < args.length; ++i) {
            out.println(args[i]);
            String[] tokens = args[i].split("=");
            out.println(tokens[0]);
            if (tokens.length == 2) {
                if (tokens[0].equals("Kp")) {
                    out.println("here");
                    PID_params.put("Kp", Float.parseFloat(tokens[1]));
                } else if (tokens[0].equals("Kd")) { 
                    PID_params.put("Kd", Float.parseFloat(tokens[1]));
                } else if (tokens[0].equals("Ki")) { 
                    PID_params.put("Ki", Float.parseFloat(tokens[1]));
                } // else if (tokens[0].equals("gyro")) {
                //     String[] ports = tokens[1].split(",");
                //     gyro_ports = new int[ports.length];
                //     for (int j = 0; j < ports.length; ++j) {
                //         gyro_ports[j] = Integer.parseInt(ports[j]);
                //     }
                // }
            }
        }
        out.println("PID parameters are: ");
        out.println("    Kp = " + Float.toString(PID_params.get("Kp")));
        out.println("    Kd = " + Float.toString(PID_params.get("Kd")));
        out.println("    Ki = " + Float.toString(PID_params.get("Ki")));
        out.println("");
        // out.println("Gyro ports are : " + Arrays.toString(gyro_ports));

    }

    public static void init() {
        out.println("Initializing gyro sensors ... ");
        gyro_ports = new int[2];
        gyro_ports[0] = 1;
        gyro_ports[1] = 2;
        gyro          = new EV3GyroSensor[gyro_ports.length];
        angle_sampler = new SampleProvider[gyro_ports.length];
        for (int i = 0; i < gyro_ports.length; ++i) {
            gyro[i] = new EV3GyroSensor(port[gyro_ports[i]]);
            gyro[i].reset(); // reset gyro sensor
            angle_sample[i] = gyro.getAngleMode();
        }
        
        out.println("done.");        
    }

    public static float PID_controller() {
        
        // float angular_acceleration = PID_params.get("Kp")*(
    }

    public static float getAngle() {
        sampleSize[0] = angle_sampler[0].sampleSize();
        sampleSize[1] = angle_sampler[1].sampleSize();

        if (sampleSize[0] > 1) {
            out.println(String.valueOf(System.currentTimeMillis()) + " : sample size 0 > 1");
        }
        if (sampleSize[1] > 1) {
            out.println(String.valueOf(System.currentTimeMillis()) + " : sample size 1 > 1");
        }

        float[] sample0 = new float[sampleSize[0]];
        float[] sample1 = new float[sampleSize[1]];

        angle_sampler[0].fetchSample(sample0, 0);
        angle_sampler[1].fetchSample(sample1, 0);
        
        return (sample0[0] + sample[1]) / 2;
    }

    public static void PID_controller() {
        float Kd = PID_params.get("Kd");
        float Kp = PID_params.get("Kp");
        float Ki = PID_params.get("Ki");

        float error = 0;
        float curr_angle = getAngle();
        
        leftMotor.resetTachoCount();
        rightMotor.resetTachoCount();

        leftMotor.rotateTo(0);
        rightMotor.rotateTo(0);

        error = (desired_angle - curr_angle);
        angle_error_int = new Integration(0, error);
        angle_error_dev = new Derivative(error);
        
        while (true) {
            
            acceleration = 
                Kp*error + 
                Kd*angle_error_dev.add(reading) + 
                Ki*angle_error_int.addreading(error);
            acceleration = acceleration / 180 * 3.1415926; // acceleration has to be in degree

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
            curr_angle = getAngle();
        }
    }

    public class Derivative {
        private long[]   readingTimeMillis = new long[2];
        private double[] readingValue = new double[2];
        private double   gradient = 0;
        
        public Derivative(double initial_reading) {
            
            readingTimeMillis[0] = System.currentTimeMillis();
            readingTimeMillis[1] = readingTimeMillis[0];

            readingValue[0] = initial_reading;
            readingValue[1] = readingValue[0];
            
            gradient = 0;
        }

        public double addreading(double reading) {
            double curr_time = System.currentTimeMillis();
            readingTimeMillis[1] = readingTimeMillis[0];
            readingValue[1] = readingValue[0];
            
            readingTimeMillis[0] = curr_time;
            readingValue[0] = reading;
            
            gradient = (readingValue[0] - readingValue[1]) / (readingTimeMillis[0] - readingTimeMillis[1]);
            return gradient;
        }
    }
}


