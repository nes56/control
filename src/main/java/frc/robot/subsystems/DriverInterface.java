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
import frc.robot.commands.GoStraight1;
import frc.robot.commands.ReleaseJacks;
import frc.robot.commands.controlClimbing1;

/**
 * Add your docs here.
 */
public class DriverInterface {

    public Joystick joystickLeft;
    public Joystick joystickRight;
    public Joystick Bottom_Button ;
    public Joystick Botton_Forward ;
    public XboxController xbox; 
    
    // Right and Lefgt buttons
    public static int BUTTON_SHIFTER = 1;   // trigger button
    // Right Buttons
    public static int START_CLIMB = 11;  // big front button
    public static int CALC_KF = 4;  // on JS buttom right button
    public static int RELEASE_JACK = 6;  // on JS top right button

    // Left Buttons
    public static int REVERSE_MODE = 9;   // big center button
    public static int FORWARD_MODE = 11;  // big front button
    public static int RESET_ENCODER = 7;  // big back button
    public static int RESET_ARM_ENCODER = 8;  // small back button

    // buttons
    JoystickButton climbButton;
    JoystickButton calcKF;
    JoystickButton releaseJack;
    
    public DriverInterface(){
      joystickRight=new Joystick(1);
      joystickLeft=new Joystick(2);
      xbox= new XboxController(0);
      climbButton = new JoystickButton(joystickRight, START_CLIMB);
   //   climbButton.whenPressed(new controlClimbing1());
      calcKF = new JoystickButton(joystickRight, CALC_KF);
      calcKF.whenPressed(new GoStraight1());
      releaseJack = new JoystickButton(joystickRight, RELEASE_JACK);
      releaseJack.whenPressed(new ReleaseJacks());
    }
}

