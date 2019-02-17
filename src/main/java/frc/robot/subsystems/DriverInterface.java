/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.GoStraight;
import frc.robot.commands.ReleaseJacks;
import frc.robot.commands.TurnByDegrees;
import frc.robot.commands.TurnByR;
import frc.robot.commands.controlClimbing1;

/**
 * Add your docs here.
 */
public class DriverInterface {

    public Joystick joystickLeft;
    public Joystick joystickRight;
    public XboxController xbox; 
    
    // Right and Lefgt buttons
    public static int BUTTON_SHIFTER = 1;   // trigger button
    // Right Buttons
    public static int CALC_KF = 4;  // on JS buttom right button
    public static int CHANGE_ARM = 5;  // on JS top left button

    // Left Buttons
    public static int REVERSE_MODE = 9;   // big center button
    public static int FORWARD_MODE = 11;  // big front button
    public static int RESET_ENCODER = 7;  // big back button
    public static int RESET_ARM_ENCODER = 8;  // small back button


    //xbox axises
    public static int ARM = 5;
    

    //xbox button
    public static int START_CLIMB = 2;  // B button
    public static int STOP_CLIMB = 3;  // X button
    public static int RELEASE_JACK = 1;  // A button


    // buttons
    JoystickButton climbButton;
    public JoystickButton stopClimbButton;
  //  JoystickButton calcKF;
    JoystickButton releaseJack;
    public controlClimbing1 climbCmd;
    
    public DriverInterface(){
      joystickRight=new Joystick(1);
      joystickLeft=new Joystick(2);
      xbox= new XboxController(0);
      climbCmd = new controlClimbing1();
      climbButton = new JoystickButton(xbox, START_CLIMB);
      stopClimbButton = new JoystickButton(xbox, STOP_CLIMB);
      climbButton.whenPressed(climbCmd);
      stopClimbButton.cancelWhenPressed(climbCmd);
//      calcKF = new JoystickButton(joystickRight, CALC_KF);
//      calcKF.whenPressed(new CalcKF());
      releaseJack = new JoystickButton(xbox, RELEASE_JACK);
      releaseJack.whenPressed(new ReleaseJacks());
      SmartDashboard.putData(new GoStraight(500, 1000));
      SmartDashboard.putData(new TurnByDegrees(30, 90));
      SmartDashboard.putData(new TurnByR(30, 90, 500));
      
    }
}

