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
}
class SmartClimateSystem extends SmartDevice {
    private double currentTemperature;

    public SmartClimateSystem(String deviceID, String deviceName) {
        super(deviceID, deviceName);
        this.currentTemperature = 24.0; 
    }

    @Override
    public void adjustSettings(double targetValue) throws DeviceOverloadException {
        if (targetValue < 16.0 || targetValue > 30.0) {
            throw new DeviceOverloadException("CRITICAL ERROR: Temperature target " 
                + targetValue + "C out of safe operational limit (16C - 30C).");
        }
        this.currentTemperature = targetValue;
    }

    @Override
    public String getDeviceDetails() {
        String state = getStatus() ? "ACTIVE" : "OFFLINE";
        return "Unit: " + deviceName + " [" + getDeviceID() + "] | Status: " 
            + state + " | Target Temp: " + currentTemperature + "C";
    }
}

class SmartSecurityLight extends SmartDevice {
    private int brightnessLevel;

    public SmartSecurityLight(String deviceID, String deviceName) {
        super(deviceID, deviceName);
        this.brightnessLevel = 50; 
    }

    @Override
    public void adjustSettings(double targetValue) throws DeviceOverloadException {
        int targetInt = (int) targetValue;
        if (targetInt < 0 || targetInt > 100) {
            throw new DeviceOverloadException("CRITICAL ERROR: BRIGHTNESS TOO HIGH " 
                + targetInt + "%");
        }
        this.brightnessLevel = targetInt;
    }

    @Override
    public String getDeviceDetails() {
        String state = getStatus() ? "ON" : "OFF";
        return "System: " + deviceName + " [" + getDeviceID() + "] | Safety Grid: " 
            + state + " | Output Power: " + brightnessLevel + "%";
    }
}