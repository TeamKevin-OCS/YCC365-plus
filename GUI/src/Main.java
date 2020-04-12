import java.util.*;

public class Main {
    static SearchCameraGUI scGUI;

    public static void main(String[] args) throws Exception {
        // Search network for devices
        ArrayList<Device> DeviceList = Scan.getDevices();

        // Open Search Camera GUI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                scGUI = new SearchCameraGUI();
                scGUI.setVisible(true);
                scGUI.updateDevices(DeviceList);
            }
        });
    }
}