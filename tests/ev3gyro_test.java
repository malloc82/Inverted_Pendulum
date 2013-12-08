import lejos.hardware.sensor.EV3GyroSensor;

import static java.lang.System.out;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import lejos.hardware.sensor.EV3SensorConstants;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.UARTPort;
import lejos.robotics.SampleProvider;

public class ev3gyro_test {
    private Port[] port = {SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4};
    private EV3GyroSensor  gyros_angle;  // one gyro sensor can only do one mode.
    private EV3GyroSensor  gyros_rate;
    private SampleProvider angle_sampler;
    private SampleProvider rate_sampler;
    
    public ev3gyro_test(int port_angle, int port_rate) {
        gyros_angle = new EV3GyroSensor(port[port_angle]);
        gyros_angle.reset();
        gyros_rate  = new EV3GyroSensor(port[port_rate]);
        gyros_rate.reset();

        angle_sampler = gyros_angle.getAngleMode();
        rate_sampler  = gyros_rate.getRateMode();
    }

    public void read_test () {
        long curr_time = System.currentTimeMillis();
        long loop_time = 30000; // 5sec, in ms 
        long end_time  = curr_time + loop_time;
        int angle_sample_size = angle_sampler.sampleSize();
        int rate_sample_size  = rate_sampler.sampleSize();

        out.println("angle_sample_size="+Integer.toString(angle_sample_size)+
                    ", rate_sample_size="+Integer.toString(rate_sample_size));
        
        while (curr_time <= end_time) {
            
            // must allocate array in loop, otherwise all the sample data will be accumulated
            float[] angle_sample = new float[angle_sample_size]; 
            float[] rate_sample  = new float[rate_sample_size];
            angle_sampler.fetchSample(angle_sample, 0);
            rate_sampler.fetchSample(rate_sample, 0);
            out.println(Long.toString(curr_time) + 
                        "    sample : angle=" + Float.toString(angle_sample[0]) + 
                        ", sample : rate=" + Float.toString(rate_sample[0]));

            curr_time = System.currentTimeMillis();
        }
    }

    // public void runtest() {
    //     int [] current = new int[port.length];
    //     for (int i = 0; i < port.length; i++) {
    //         int typ = port[i].getPortType();
    //         if (typ == EV3SensorConstants.CONN_INPUT_UART) {
    //             out.println("Open port " + i);
    //             // UARTPort u = port[i].open(UARTPort.class);
    //             // String modeName = u.getModeName(0);
    //             EV3GyroSensor gyro = new EV3GyroSensor(port[i]);
    //             // if (modeName.indexOf(0) >= 0)modeName = modeName.substring(0, modeName.indexOf(0));
    //             SampleProvider angle_sample = gyro.getAngleMode();
    //             out.println("Uart sensor: " + angle_sample);
    //             out.println("");
    //             out.println("Loop for reading test");
    //             while(true) {
    //                 int sampleSize = angle_sample.sampleSize();
    //                 float[] sample = new float[sampleSize];
    //                 angle_sample.fetchSample(sample, 0);
    //                 for(int j=0; j < sampleSize; ++j) 
    //                     out.println("sample[" + j + "] is " + sample[j]);
    //             }
    //             // String className = sensorClasses.get(modeName);
    //             // out.println("Sensor class for " + modeName + " is " + className);
    //             // u.close();
    //         }
    //     }
    //     return;
    // }

    public static void main(String[] args) {        
        int port_angle = -1;
        int port_rate  = -1;
        out.println("gyro test start ...");
        for (int i=0; i < args.length; ++i) {
            String[] tokens = args[i].split("=");
            if (tokens[0].equals("angle") && tokens.length == 2) {
                port_angle = Integer.parseInt(tokens[1]);
            } else if (tokens[0].equals("rate") && tokens.length == 2) {
                port_rate = Integer.parseInt(tokens[1]);
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
        ev3gyro_test n = new ev3gyro_test(port_angle, port_rate);
        n.read_test();
        // runtest();
        out.println("gyro test end. ");
    }
}

