import java.net.InetAddress;

public class Device {
    private String ip;
    private String mac;

    public Device(String input_ip, String input_max) {
        this.ip = input_ip;
        this.mac = input_max;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

}
