/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class controlClimbing1 extends Command {

  double K_P = 2.8/100; // 1 degree error = correct +/- 1% absolute power
  double K_I = K_P/100.0;
  double K_D = 5.0 / 100.0;
  double basePitch = 0;
  boolean inRaiseBackJack = false;
  double sumError;
  double lastError;


  public controlClimbing1() {
    sumError = 0.0;
    lastError = 0;
  }

  @Override
  protected void initialize() {
    basePitch = Robot.chassis.getGyroPitch(); // assume current pitch is horizontal
    // set the chassis data - slow, forward and disabled JS
    Robot.chassis.setSlowMode(); 
    Robot.chassis.SetReverseMode(false);
    Robot.chassis.SetCommand(this);
    // use this to keep the back jack raised at the end
    inRaiseBackJack = false; 
  }

  @Override
  protected void execute() {
    Robot.chassis.StopMotors();
    // get the JS values and normalized
    double moveV = -Robot.driverInterface.joystickRight.getY();
    double jacksV = Robot.driverInterface.joystickRight.getRawAxis(3);
    double frontJackV = Robot.driverInterface.joystickLeft.getRawAxis(3);
    double backJackV = -Robot.driverInterface.joystickLeft.getY();
    jacksV = (jacksV - 1) / -2; // down is 0, up is 1 (joystick provide 1 at up, -1 at down)
    jacksV = jacksV * jacksV;
    frontJackV = (frontJackV - 1) / 2; // move from 0 to -1 (lift fron tp)
    // calculate the pitch correction
    double pitchError = Robot.chassis.getGyroPitch() - basePitch; // error < 0 - front is higher
    sumError += pitchError;
    double i = sumError * K_I;
    double p = K_P * pitchError;
//   double d = (lastError - pitchError) * K_D;
    lastError = pitchError;
    double corr = Math.abs(p + i);
    System.out.println("p = " + p + " sumError = " + sumError + " corr = " + corr + " i = " + i);
    double Moshe = 0.2; //0.79; // Front = Rear * Ratio
    double bjack = jacksV;
    double fjack = jacksV*Moshe;
    if(pitchError < 0) { // front is high
      bjack += corr;
    } else {
      fjack += corr * Moshe;
    }
    if(pitchError > 15) {
      fjack = 0;
    } else if(pitchError < -15) {
      bjack = 0;
    }
    // check if raising/raised front jack (fjack is less then min) - in this case - ignore correction
    if(frontJackV < -0.1) {
      fjack = frontJackV;
      bjack = jacksV;
    }
    // check if raising back jack
    if(backJackV < -0.1) { 
      inRaiseBackJack = true;
      bjack = backJackV;
    }
  // if raised back jack and the JS value is 0 - forced a back jack mainintain value
    if(inRaiseBackJack && backJackV > -0.1) {
      bjack = -0.2;
    }
    // show results
    SmartDashboard.putNumber("Jack Front", fjack);
    SmartDashboard.putNumber("Jack back", bjack);
    SmartDashboard.putNumber("Jack picth", pitchError);
    System.out.println(" b/f jack: " + bjack +  "/" + fjack + " pitch:" + pitchError);

    // put values
    if(jacksV < 0.5){
      Robot.climb.setValue_backJack(jacksV);
      Robot.climb.setValue_frontJack(jacksV);
    }
    else{
      Robot.climb.setValue_backJack(bjack);
      Robot.climb.setValue_frontJack(fjack);
    }
    Robot.climb.setValue_moveMotor(moveV);
    // if front is raised - also use chassis motors
    if(fjack < 0) { 
      Robot.chassis.SetSpeed(moveV*400,moveV*400);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
		Robot.chassis.SetCommand(null);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
