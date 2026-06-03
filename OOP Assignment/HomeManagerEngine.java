import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class HomeManagerEngine {
    private DefaultListModel<SmartDevice> systemRegistryModel;

    public HomeManagerEngine() {
        systemRegistryModel = new DefaultListModel<>();
        seedDefaultInventory();
    }

    private void seedDefaultInventory() {
        systemRegistryModel.addElement(new SmartClimateSystem("AC-1", "Living Room AC"));
        systemRegistryModel.addElement(new SmartClimateSystem("AC-2", "Bedroom AC"));
        systemRegistryModel.addElement(new SmartSecurityLight("LIGHT-1", "Living Room Light"));
        systemRegistryModel.addElement(new SmartSecurityLight("LIGHT-2", "Bedroom Light"));
    }

    public DefaultListModel<SmartDevice> getDeviceModel() {
        return systemRegistryModel;
    }

    public ArrayList<SmartDevice> getDevicesAsList() {
        ArrayList<SmartDevice> list = new ArrayList<>();
        for (int i = 0; i < systemRegistryModel.size(); i++) {
            list.add(systemRegistryModel.get(i));
        }
        return list;
    }

    public SmartDevice lookupDevice(String id) {
        for (int i = 0; i < systemRegistryModel.size(); i++) {
            SmartDevice device = systemRegistryModel.get(i);
            if (device.getDeviceID().equalsIgnoreCase(id)) {
                return device;
            }
        }
        return null;
    }

    public String generateSystemSummaryText() {
        StringBuilder reportBuffer = new StringBuilder();
        reportBuffer.append("--- LETS MAKE OUR HOME SMART TOGETHER ---\n\n");
        
        for (int i = 0; i < systemRegistryModel.size(); i++) {
            SmartDevice device = systemRegistryModel.get(i);
            reportBuffer.append(device.getDeviceDetails()).append("\n");
        }
        return reportBuffer.toString();
    }
}
