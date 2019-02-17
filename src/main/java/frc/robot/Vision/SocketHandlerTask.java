package frc.robot.Vision;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SocketHandlerTask extends Thread {

    public Socket socket;
    public boolean isFront;

    Gson gson;
    JsonStreamParser parser;

    VisionData visionData;

    public SocketHandlerTask(Socket socket) {
        super();
        this.socket = socket;
        gson = new Gson();
/*        try{
            parser = new JsonStreamParser(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e){
            e.printStackTrace();
        } */
        start();
    }

    @Override
    public void run() {
        System.out.println("is connected = " + socket.isConnected());
        boolean isFront = true;
        char[] buf = new char[1024];
        InputStreamReader is;
        try {
            is = new InputStreamReader(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int n = 0;
        while (socket != null && socket.isConnected()) {
            try {
                n = is.read(buf);
                if(n > 0) {
                    System.out.println(" got " + n + " - " + new String(buf,0,n));
                } else {
                    System.out.println(" got " + n);
                }
/*                JsonElement element = parser.next();
                visionData = gson.fromJson(element, VisionData.class);
                isFront = visionData.front;
                SmartDashboard.putBoolean(isFront?"Front OK":"Back OK", true);
                visionData.set(); */
            } catch (Exception ex) {
                System.out.println("error in parse data  " + ex);
                if (socket.isInputShutdown() ||
                    socket.isClosed() || 
                    !socket.isConnected() || 
                    ex instanceof EOFException) {
                  SmartDashboard.putBoolean(isFront?"Front OK":"Back OK", false);
                  try {
                    socket.close();
                  } catch (Exception e2) {
                  }
                  break;
                }
            }
        }
    }
}