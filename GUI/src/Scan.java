import java.net.InetAddress;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Scan {
    public static ArrayList<Device> getDevices() throws Exception {
        // Flush ARP Table
        flushARP();

        // Update ARP Table
        updateARP();

        String OUI = "30-4a-26"; // Organisationally Unique Identifier (BABYFOON)

        try {
            ArrayList<Device> DeviceList = getDevicesFromARP(OUI);
            System.out.println("FINISHED REFRESH");
            return DeviceList;
        } catch (Exception e) {
            System.out.println("FINISHED REFRESH");
            return null;
        }
    }

    public static ArrayList<String> getIpAddresses() throws SocketException {
        Enumeration<NetworkInterface> nets =
                NetworkInterface.getNetworkInterfaces();
        ArrayList<String> addresses = new ArrayList<>();
        for (NetworkInterface networkInterface: Collections.list(nets)) {
            Enumeration<InetAddress> addr = networkInterface.getInetAddresses();
            if (addr.hasMoreElements()) {
                addresses.add(addr.nextElement().getHostAddress());
            }
        }
        return addresses;
    }


    public static String getARPTable(String cmd) throws IOException {
        Scanner s =
                new Scanner(Runtime.getRuntime().exec(cmd)
                        .getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static ArrayList<Device> getDevicesFromARP(String OUI)
            throws Exception {
        ArrayList<String> addresses = getIpAddresses();

        ArrayList<Device> DeviceList = new ArrayList<>();

        for (String addr : addresses) {
            String cmd_get_arp = "arp -a -N " + addr;

            String arp = getARPTable(cmd_get_arp);

            String[] arp_parts = arp.split(" ");

            List<String> arp_list =
                    new ArrayList<String>(Arrays.asList(arp_parts));
            arp_list.removeAll(Arrays.asList("", null));

            for (int i = 9; i < arp_list.size(); i++) {
                if (arp_list.get(i + 2).equals("dynamic")) {
                    Device device =
                            new Device(arp_list.get(i), arp_list.get(i + 1));
                    if (device.getMac().startsWith(OUI)) {
                        DeviceList.add(device);
                    }
                }
                i += 3;
            }
        }

        return DeviceList;
    }

    public static void flushARP() throws Exception {
        String cmd_flush_ARP = "arp -d";
        Runtime.getRuntime().exec(cmd_flush_ARP);
    }

    public static void updateARP() throws Exception {
        String cmd_update_ARP = "for /L %a in (1,1,254) do @start /b " +
                "ping 192.168.1.%a -w 100 -n 2 >nul";

        ProcessBuilder builder =
                new ProcessBuilder("cmd.exe", "/c", cmd_update_ARP);
        builder.redirectErrorStream(true);
        Process p = builder.start();

        TimeUnit.SECONDS.sleep(1);
    }
}