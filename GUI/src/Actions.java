import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import java.util.ArrayList;

public class Actions {
    public static void movement(int dir, int status) {
        Telnet.moving(dir, status);
    }

    public static void startListening() {
        System.out.println("startListening()");
    }

    public static void stopListening() {
        System.out.println("stopListening()");
    }

    public static void startTalking() {
        System.out.println("startTalking()");
    }

    public static void stopTalking() {
        System.out.println("stopTalking()");
    }

    public static void startAlarm() {
        System.out.println("startAlarm()");
    }

    public static void stopAlarm() {
        System.out.println("stopAlarm()");
    }

    public static void babySounds() {
        System.out.println("babySounds()");
    }

    public static void refresh(EmbeddedMediaPlayerComponent mediaPlayerComponent, String url) {
        mediaPlayerComponent.mediaPlayer().media().play(url);
        System.out.println("refresh()");
    }

    public static void switchCamera() {
        try {
            ArrayList<Device> DeviceList = Scan.getDevices();
            java.awt.EventQueue.invokeLater(() -> {
                SearchCameraGUI scGUI = new SearchCameraGUI();
                if (DeviceList != null) {
                    scGUI.setVisible(true);
                    scGUI.updateDevices(DeviceList);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("switchCamera()");
    }
}
