/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveByJoystickCommand extends Command {

  public static final double MIN_JS_VALUE = 0.2;

  public DriveByJoystickCommand() {
    
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
      double leftJoystickValue=Robot.driverInterface.joystickLeft.getY();   
      double rightJoystickValue=Robot.driverInterface.joystickRight.getY();
      double lValue= Math.abs(leftJoystickValue) * leftJoystickValue;
      double rValue = Math.abs(rightJoystickValue) * rightJoystickValue;
      lValue =  Math.abs(lValue)<MIN_JS_VALUE ? 0 : lValue;
      rValue =  Math.abs(rValue)<MIN_JS_VALUE ? 0 : rValue;
    //  System.out.println("left value = " + lValue);
    //  System.out.println("right value = " + rValue);
      Robot.chassis.SetValue(-1 * lValue, -1 * rValue);
      edu.wpi.first.wpilibj.drive.DifferentialDrive dd;
      //dd.arcadeDrive(xSpeed, zRotation);
    }
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
