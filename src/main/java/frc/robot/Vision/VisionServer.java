package frc.robot.Vision;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class VisionServer extends Thread {

    public static final int PORT = 8083;

    ServerSocket srvSocket;

    public VisionServer() {
        start();
    }

    @Override
    public void run() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e2) {
            addr = null;
            e2.printStackTrace();
		}
        while(true) {
           try {
                srvSocket = new ServerSocket(PORT,2,addr);
                while(true) {
                    Socket s = srvSocket.accept();
                    new SocketHandlerTask(s);
                }
            } catch (Exception e) {
                try {
                    srvSocket.close();
                } catch (Exception e1) {}
            }
        }
    }
}