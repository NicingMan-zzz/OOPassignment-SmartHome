import java.util.*;

class DeviceOverloadException extends Exception {
    public DeviceOverloadException(String message) {
        super(message);
    }
}

public abstract class SmartDevice {
    private String deviceID;
    private boolean isAltered;
    protected String deviceName;

    public SmartDevice(String deviceID, String deviceName) {
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.isAltered = false;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public boolean getStatus() {
        return isAltered;
    }

    public void togglePower() {
        this.isAltered = !this.isAltered;
    }

    public abstract void adjustSettings(double value) throws DeviceOverloadException;
    
    public abstract String getDeviceDetails();

    @Override
    public String toString() {
        return getDeviceDetails();
    }
}
