package Device;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Sensor {

    int state = 0;

    private SerialPort serialPort;

    public Sensor(String deviceName) {
        serialPort = new SerialPort(deviceName);
    }

    public boolean isOpened() {
        if (serialPort == null) {
            return false;
        } else {
            return serialPort.isOpened();
        }
    }

    public String getSensorName() {
        if (serialPort == null) {
            return "none";
        }
        return serialPort.getPortName();
    }

    public static String[] getSensorList() {
        return SerialPortList.getPortNames();
    }

    public void start() {
        try {

            serialPort.openPort();

            if (!serialPort.isOpened()) {

                return;
            }

            serialPort.setParams(9600, 8, 1, 0);

            SerialPortEventListener listener = new SenListener();
            serialPort.addEventListener(listener);
            //checkSend();

        } catch (SerialPortException ex) {
            // TODO: error handling
            JOptionPane.showMessageDialog(null, "Can not set the port!!!!");
            //System.out.println(ex);
        }
    }

    public void stop() {
        if (serialPort == null) {
            return;
        }

        try {
            serialPort.writeBytes("C\r".getBytes());
            serialPort.removeEventListener();
            serialPort.purgePort(SerialPort.PURGE_RXABORT | SerialPort.PURGE_TXCLEAR);
            serialPort.closePort();
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    private String sensorData(Byte[] sensorData) {

        String data = byteArrayToString(sensorData);
        return data;

    }

    private String byteArrayToString(Byte[] array) {
        byte[] bytes = new byte[array.length];
        int i = 0;
        for (byte b : array) {
            bytes[i++] = b;
        }

        return new String(bytes);
    }

    //tell arduino to send messages;
    public void checkSend() {

        try {
            Thread.sleep(2000);
            serialPort.writeString("t");

        } catch (Exception e) {
        }
        /*new Thread() {
            public void run() {
                while (state == 0) {

                    try {

                        serialPort.writeString("t");
                        Thread.sleep(1000);

                    } catch (Exception e) {
                    }

                }
            }

        }.start();*/

    }

    private class SenListener implements SerialPortEventListener {

        List<Byte> sensorBytes = new ArrayList<>();

        @Override
        public void serialEvent(SerialPortEvent event) {

            if (state == 0) {
                state = 1;
            }

            if (event.isRXCHAR() && event.getEventValue() > 0) {

                try {
                    byte[] bs;
                    bs = serialPort.readBytes();

                    for (byte b : bs) {
                        if (b == '\r') {
                            // end of frame data received
                            String f = sensorData(sensorBytes.toArray(new Byte[sensorBytes.size()]));
                            DeviceManager.getdata(f);
                            sensorBytes.clear();

                        } else {
                            // byte received, add to buffer
                            sensorBytes.add(b);
                        }
                    }

                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }

        }
    }

}
