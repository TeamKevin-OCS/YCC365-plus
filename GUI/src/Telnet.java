import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;

public class Telnet implements Runnable, TelnetNotificationHandler {

    public static CountDownLatch latch;
    public static CountDownLatch latch2;

    static TelnetClient client = null;
    static OutputStream outStream;
    static byte[] buff_output;
    static Integer dir;
    static Boolean end_loop;
    static Thread reader;

    public static void connect(String ip, int port) throws Exception {
        client = null;
        outStream = null;
        buff_output = null;
        dir = null;
        end_loop = null;
        reader = null;

        latch = new CountDownLatch(1);
        latch2 = new CountDownLatch(1);

        client = new TelnetClient();

        try {
            client.connect(ip, port);
        } catch (IOException e) {
            System.err.println("Exception while connecting:" + e.getMessage());
            System.exit(1);
        }

        reader = new Thread(new Telnet());
        client.registerNotifHandler(new Telnet());

        reader.start();
        outStream = client.getOutputStream();

        latch2.countDown();

        end_loop = false;
        buff_output = new byte[1024];

        latch.await();
    }

    @Override
    public void receivedNegotiation(int negotiation_code, int option_code)
    {
        String command = null;
        switch (negotiation_code) {
            case TelnetNotificationHandler.RECEIVED_DO:
                command = "DO";
                break;
            case TelnetNotificationHandler.RECEIVED_DONT:
                command = "DONT";
                break;
            case TelnetNotificationHandler.RECEIVED_WILL:
                command = "WILL";
                break;
            case TelnetNotificationHandler.RECEIVED_WONT:
                command = "WONT";
                break;
            case TelnetNotificationHandler.RECEIVED_COMMAND:
                command = "COMMAND";
                break;
            default:
                command = Integer.toString(negotiation_code); // Should not happen
                break;
        }
        System.out.println("Received " + command + " for option code " + option_code);
    }

    /***
     * Reader thread.
     * Reads lines from the TelnetClient and echoes them
     * on the screen.
     ***/
    @Override
    public void run()
    {
        try {
            latch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InputStream instr = client.getInputStream();

        try{
            byte[] buff = new byte[1024];
            int ret_read = 0;

            String output = "";

            do {
                ret_read = instr.read(buff);
                if (ret_read > 0) {
                    output = new String(buff, 0, ret_read);
                }

                if (output.contains("localhost login:") && !output.contains("Login incorrect")) {
                    byte[] byteArr = "root\r\n".getBytes();
                    outStream.write(byteArr);
                    outStream.flush();
                } else if (output.contains("incorrect")) {
                    byte[] byteArr = "root\r\n".getBytes();
                    outStream.write(byteArr);
                    outStream.flush();
                } else if (output.contains("Password:")) {
                    byte[] byteArr = "cxlinux\r\n".getBytes();
                    outStream.write(byteArr);
                    outStream.flush();
                } else if (output.contains("/bin/hostname: not found")) {
                    byte[] byteArr = "cd /\r\n".getBytes();
                    outStream.write(byteArr);
                    outStream.flush();
                    byteArr = "home/ptz_test\r\n".getBytes();
                    outStream.write(byteArr);
                    outStream.flush();
                    latch.countDown();
                }
            } while (ret_read >= 0);
            latch.countDown();
        }
        catch (IOException e) {
            System.err.println("Exception while reading socket:" + e.getMessage());
        }

        try {
            client.disconnect();
        }
        catch (IOException e) {
            System.err.println("Exception while closing telnet:" + e.getMessage());
        }
    }

    public static void moving(int dir, int status) {
        if (client == null) {
            try {
                connect("1.1.1.1", 23);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (status == 1) {
                String action = String.valueOf(dir) + "\r\n";
                byte[] byteArr = action.getBytes();
                outStream.write(byteArr);
                outStream.flush();
            } else {
                byte[] byteArr = "0\r\n".getBytes();
                outStream.write(byteArr);
                outStream.flush();
            }
        } catch (IOException i) {
            i.printStackTrace();
        }

    }
}
