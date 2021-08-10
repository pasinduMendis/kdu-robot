package Device;

import java.util.ArrayList;

public class DeviceManager {

    
    private static Sensor sensordevice;

    private final static ArrayList<SensorListener> sensor = new ArrayList<>();

    ///// for sensor interfacing
    public static boolean isSenOpen(String senName) {
        if (sensordevice != null && senName != null) {
            return (sensordevice.getSensorName().
                    equals(senName) && sensordevice.isOpened());
        } else {
            return false;
        }
    }

    public static void addSensorListener(SensorListener l) {
        sensor.add(l);
    }

    public static void removeSensorListener(SensorListener l) {
        sensor.remove(l);
    }

    public static String[] getSensorList() {
        return Sensor.getSensorList();
    }

    public static void openSensor(String deviceName) {
        sensordevice = new Sensor(deviceName);
        sensordevice.start();
    }

    public static void closeSensor(String senName) {
        sensordevice.stop();
    }

    public static void sensorSet() {

        sensordevice.checkSend();

    }

    public static void getdata(String dt) {

        for (SensorListener l : sensor) {
            l.sensorReceived(dt);
        }

    }

   
}
