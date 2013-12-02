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
    public void runtest() {
        Port[] port = {SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4};
        int [] current = new int[port.length];
        for (int i = 0; i < port.length; i++) {
            int typ = port[i].getPortType();
            if (typ == EV3SensorConstants.CONN_INPUT_UART) {
                out.println("Open port " + i);
                // UARTPort u = port[i].open(UARTPort.class);
                // String modeName = u.getModeName(0);
                EV3GyroSensor gyro = new EV3GyroSensor(port[i]);
                // if (modeName.indexOf(0) >= 0)modeName = modeName.substring(0, modeName.indexOf(0));
                SampleProvider angle_sample = gyro.getAngleMode();
                out.println("Uart sensor: " + angle_sample);
                out.println("");
                out.println("Loop for reading test");
                while(true) {
                    int sampleSize = angle_sample.sampleSize();
                    float[] sample = new float[sampleSize];
                    angle_sample.fetchSample(sample, 0);
                    for(int j=0; j < sampleSize; ++j) 
                        out.println("sample[" + j + "] is " + sample[j]);
                }
                // String className = sensorClasses.get(modeName);
                // out.println("Sensor class for " + modeName + " is " + className);
                // u.close();
            }
        }
        return;
    }

    public static void main(String[] args) {
        out.println("gyro test start ...");
        new ev3gyro_test().runtest();
        out.println("gyro test end. ");
    }
}

