/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Climb;

public class ControlJacks extends Command {

  JackStatus target;
  double startPitch;
  boolean isFinished = false;
  public static final double K_P = 0.3; 


  public enum JackStatus{

    robot_up,
    robot_keep_up,
    back_down_forward_up,
    robot_down;
  }


  public ControlJacks(JackStatus target) {
    this.target = target;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isFinished = false;
    startPitch = Robot.chassis.getGyroPitch();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    switch(target){
      case robot_up:
        robot_up();
        break;
      case back_down_forward_up:
        back_down_front_up();
        break;
      case robot_down:
        robot_down();
        break;
      case robot_keep_up:
        keep_up();
    }

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

  private void robot_up(){
    if(Robot.climb.isBackSwitchUpPressed()){
      if(Robot.climb.isFrontSwitchUpPressed()){
        Robot.climb.setValue_backJack(Climb.BACK_POWER_TO_STAY_UP);
        Robot.climb.setValue_frontJack(Climb.FRONT_POWER_TO_STAY_UP);
        isFinished = true;
        return;
      }
      else{
        Robot.climb.setValue_backJack(Climb.BACK_POWER_TO_STAY_UP);
        Robot.climb.setValue_frontJack(Climb.FRONT_POWER_TO_MOVE_UP);
        return;
      }
    }
    else if(Robot.climb.isFrontSwitchUpPressed()){
      Robot.climb.setValue_backJack(Climb.BACK_POWER_TO_MOVE_UP);
      Robot.climb.setValue_frontJack(Climb.FRONT_POWER_TO_MOVE_UP);
    }
    else{
      double error = Robot.chassis.getGyroPitch() - startPitch;
      double p = error * K_P;
      double front_power = Climb.FRONT_POWER_TO_MOVE_UP + p;
      double back_power = Climb.BACK_POWER_TO_MOVE_UP - p;
      System.out.println("p: " + p + "/ front power: " + front_power 
      + "/ back power: " + back_power);
      Robot.climb.setValue_backJack(back_power);
      Robot.climb.setValue_frontJack(front_power);
    }
  }
  private void back_down_front_up() {

  }
  private void robot_down() {

  }
  private void keep_up() {

  }
}
