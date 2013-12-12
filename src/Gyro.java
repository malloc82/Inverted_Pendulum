import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;

import static java.lang.System.out;

public class Gyro {
    public static enum MODE {RATE_MODE, ANGLE_MODE};
    private Port port;
    private EV3GyroSensor sensor;
    private SampleProvider sampler;
    private String mode;
    private int sample_size;
        
    public Gyro (Port p, MODE m) {
        port = p;
        sensor = new EV3GyroSensor(port);
        sensor.reset();
        switch (m) {
            case RATE_MODE:
                mode = "rate";
                sampler = sensor.getRateMode();
                break;
            case ANGLE_MODE:
                mode = "angle";
                sampler = sensor.getAngleMode();
                break;
            default: 
                out.println("Invalid mode number : " + 
                            String.valueOf(m));
                System.exit(0);
        }
        sample_size = sampler.sampleSize();
        out.println(mode + " : sample size = " + 
                    String.valueOf(sample_size));
    }

    public String getMode() {
        return mode;
    }
        
    public float[] getSamples() {
        float[] sample = new float[sample_size];
        sampler.fetchSample(sample, 0);
        return sample;
    }
}
