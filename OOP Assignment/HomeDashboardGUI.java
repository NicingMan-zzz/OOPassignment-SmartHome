import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class HomeDashboardGUI extends JFrame {
    private HomeManagerEngine coreEngine;

    private JTextArea displayPanelLog;
    private JTextField deviceIdInput;
    private JTextField configurationValueInput;
    private JButton actionTogglePower;
    private JButton actionApplyConfig;

    public HomeDashboardGUI() {
        coreEngine = new HomeManagerEngine();
        initializeWindowFrame();
    }

    private void initializeWindowFrame() {
        setTitle("Smart Home Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(new Color(230, 240, 255));

        JLabel titleLabel = new JLabel("Smart Home Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(30, 80, 160));
        titleLabel.setBorder(new EmptyBorder(15, 10, 5, 10));
        add(titleLabel, BorderLayout.NORTH);

        displayPanelLog = new JTextArea();
        displayPanelLog.setEditable(false);
        displayPanelLog.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayPanelLog.setBackground(new Color(245, 250, 255));
        displayPanelLog.setForeground(new Color(20, 20, 60));
        displayPanelLog.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(displayPanelLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 140, 220), 1),
            "Device Status",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13),
            new Color(30, 80, 160)
        ));
        scrollPane.setBackground(new Color(230, 240, 255));
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        controlPanel.setBackground(new Color(210, 225, 250));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 140, 220), 1),
                "Controls",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                new Color(30, 80, 160)
            ),
            new EmptyBorder(6, 8, 8, 8)
        ));

        JLabel deviceLabel = new JLabel("Device ID:");
        deviceLabel.setFont(new Font("Arial", Font.BOLD, 13));
        deviceLabel.setForeground(new Color(30, 60, 130));
        controlPanel.add(deviceLabel);

        deviceIdInput = new JTextField();
        deviceIdInput.setFont(new Font("Arial", Font.PLAIN, 13));
        deviceIdInput.setBackground(Color.WHITE);
        deviceIdInput.setBorder(BorderFactory.createLineBorder(new Color(100, 140, 220)));
        controlPanel.add(deviceIdInput);

        JLabel valueLabel = new JLabel("Temperature / Brightness:");
        valueLabel.setFont(new Font("Arial", Font.BOLD, 13));
        valueLabel.setForeground(new Color(30, 60, 130));
        controlPanel.add(valueLabel);

        configurationValueInput = new JTextField();
        configurationValueInput.setFont(new Font("Arial", Font.PLAIN, 13));
        configurationValueInput.setBackground(Color.WHITE);
        configurationValueInput.setBorder(BorderFactory.createLineBorder(new Color(100, 140, 220)));
        controlPanel.add(configurationValueInput);

        actionTogglePower = new JButton("Turn On / Off");
        actionTogglePower.setFont(new Font("Arial", Font.BOLD, 13));
        actionTogglePower.setBackground(new Color(70, 130, 220));
        actionTogglePower.setForeground(Color.WHITE);
        actionTogglePower.setFocusPainted(false);
        actionTogglePower.setCursor(new Cursor(Cursor.HAND_CURSOR));
        controlPanel.add(actionTogglePower);

        actionApplyConfig = new JButton("Change Temp / Brightness");
        actionApplyConfig.setFont(new Font("Arial", Font.BOLD, 13));
        actionApplyConfig.setBackground(new Color(70, 130, 220));
        actionApplyConfig.setForeground(Color.WHITE);
        actionApplyConfig.setFocusPainted(false);
        actionApplyConfig.setCursor(new Cursor(Cursor.HAND_CURSOR));
        controlPanel.add(actionApplyConfig);

        add(controlPanel, BorderLayout.SOUTH);

        DashboardEventHandler clickRouter = new DashboardEventHandler();
        actionTogglePower.addActionListener(clickRouter);
        actionApplyConfig.addActionListener(clickRouter);

        refreshConsoleDisplay();
    }

    private void refreshConsoleDisplay() {
        displayPanelLog.setText(coreEngine.generateSystemSummaryText());
    }

    private class DashboardEventHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String targetID = deviceIdInput.getText().trim();
            SmartDevice targetDevice = coreEngine.lookupDevice(targetID);

            if (targetDevice == null) {
                JOptionPane.showMessageDialog(HomeDashboardGUI.this,
                    "Device ID '" + targetID + "' was not found.\nPlease check the ID and try again.",
                    "Device Not Found",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (event.getSource() == actionTogglePower) {
                targetDevice.togglePower();
                refreshConsoleDisplay();

                String state = targetDevice.getStatus() ? "ON" : "OFF";
                JOptionPane.showMessageDialog(HomeDashboardGUI.this,
                    targetDevice.getDeviceID() + " has been turned " + state + ".",
                    "Power Updated",
                    JOptionPane.INFORMATION_MESSAGE);

            } else if (event.getSource() == actionApplyConfig) {
                try {
                    double value = Double.parseDouble(configurationValueInput.getText().trim());
                    targetDevice.adjustSettings(value);
                    refreshConsoleDisplay();
                    configurationValueInput.setText("");

                    JOptionPane.showMessageDialog(HomeDashboardGUI.this,
                        "Setting applied successfully to " + targetDevice.getDeviceID() + ".",
                        "Setting Updated",
                        JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(HomeDashboardGUI.this,
                        "Please enter a valid number (e.g. 22 or 75).",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                } catch (DeviceOverloadException e) {
                    JOptionPane.showMessageDialog(HomeDashboardGUI.this,
                        e.getMessage(),
                        "Out of Range",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeDashboardGUI().setVisible(true);
            }
        });
    }
}