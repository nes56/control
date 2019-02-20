package frc.robot.Utils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.Robot;

public class CameraHandler  extends Thread {
    UsbCamera camera;
    CvSink videoIn;
    CvSource videoOut;
    Mat mat = new Mat();
   
    public CameraHandler() {
        try {
            camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setExposureAuto();
            camera.setFPS(20);
            camera.setResolution(320,240);
            videoIn = CameraServer.getInstance().getVideo();
            videoOut = CameraServer.getInstance().putVideo("Camera",640,480);
            start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Camera NOT started - exception " + ex);
        }
     }

     @Override
     public void run() {
         while(true) {
         try {
             videoIn.grabFrame(mat);
             if(mat.width() != 0) {
             if(Robot.hatchPanelsSystem.isForward()) {
                 Core.rotate(mat, mat, Core.ROTATE_180);
            }
            videoOut.putFrame(mat);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     }
   
}
