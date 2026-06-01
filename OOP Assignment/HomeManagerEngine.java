import java.util.ArrayList;

public class HomeManagerEngine {
    private ArrayList<SmartDevice> systemRegistry;

    public HomeManagerEngine() {
        systemRegistry = new ArrayList<>();
        seedDefaultInventory();
    }

    private void seedDefaultInventory() {
        systemRegistry.add(new SmartClimateSystem("AC-1", "Living Room AC"));
        systemRegistry.add(new SmartClimateSystem("AC-2", "Bedroom AC"));
        systemRegistry.add(new SmartSecurityLight("LIGHT-1", "Living Room Light"));
        systemRegistry.add(new SmartSecurityLight("LIGHT-2", "Bedroom Light"));
    }

    public ArrayList<SmartDevice> getDevices() {
        return systemRegistry;
    }

    public SmartDevice lookupDevice(String id) {
        for (SmartDevice device : systemRegistry) {
            if (device.getDeviceID().equalsIgnoreCase(id)) {
                return device;
            }
        }
        return null;
    }

    public String generateSystemSummaryText() {
        StringBuilder reportBuffer = new StringBuilder();
        reportBuffer.append("--- LETS MAKE OUR HOME SMART TOGETHER ---\n\n");
        for (SmartDevice device : systemRegistry) {
            reportBuffer.append(device.getDeviceDetails()).append("\n");
        }
        return reportBuffer.toString();
    }
}