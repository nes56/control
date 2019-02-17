/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Climb extends Subsystem  {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public TalonSRX liftMotor_top1;
  public TalonSRX liftMotor_top2;
  public TalonSRX liftMotor_bot;
  public TalonSRX move_motor;

  public static final double BACK_POWER_TO_MOVE_DOWN = 0.5;
  public static final double BACK_POWER_TO_MOVE_UP = 0.5;
  public static final double BACK_POWER_TO_STAY_DOWN = 0.5;
  public static final double BACK_POWER_TO_STAY_UP = 0.5;
  public static final double FRONT_POWER_TO_MOVE_DOWN = 0.5;
  public static final double FRONT_POWER_TO_MOVE_UP = 0.5;
  public static final double FRONT_POWER_TO_STAY_DOWN = 0.5;
  public static final double FRONT_POWER_TO_STAY_UP = 0.5;
  
  public DoubleSolenoid buchna;

public Climb()  {
  liftMotor_bot = new TalonSRX(RobotMap.portClimbMotorBack);
  liftMotor_top1 = new TalonSRX(RobotMap.portClimbMotorFront1);
  liftMotor_top2 = new TalonSRX(RobotMap.portClimbMotorFront2);
  move_motor = new TalonSRX(RobotMap.portClimbMoveMotor);
  buchna = new DoubleSolenoid(RobotMap.portPCM, RobotMap.portClimbBuchnaFwd, RobotMap.portClimbBuchnaBwd);
}

public void setValue_moveMotor(double value){
//  move_motor.set(ControlMode.PercentOutput, value);
} 

public void setValue_frontJack(double value){
  liftMotor_top1.set(ControlMode.PercentOutput, value);
  liftMotor_top2.set(ControlMode.PercentOutput, value);
} 

public void setValue_backJack(double value){
  liftMotor_bot.set(ControlMode.PercentOutput, value);
}

public boolean isFrontSwitchDownPressed(){
  return liftMotor_top1.getSensorCollection().isFwdLimitSwitchClosed();
}

public boolean isFrontSwitchUpPressed(){
  return liftMotor_top1.getSensorCollection().isRevLimitSwitchClosed();
}

public boolean isBackSwitchUpPressed(){
  return liftMotor_bot.getSensorCollection().isFwdLimitSwitchClosed();
}

public boolean isBackSwitchDownPressed(){
  return liftMotor_bot.getSensorCollection().isRevLimitSwitchClosed();
}
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void lock(){
    buchna.set(DoubleSolenoid.Value.kForward);
  }

  public void stopBuchna(){
    buchna.set(DoubleSolenoid.Value.kOff);
  }
  
  public void unlock(){
    buchna.set(DoubleSolenoid.Value.kReverse);
  }

}
