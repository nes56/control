/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.GroupOfMotors;
import frc.robot.Robot;

public class TurnByDegrees extends Command {

  static final double K_P = 0.01;
	static final double WHEEL_BASE = 635;
	static final double FULL_CIRCLE = Math.PI * WHEEL_BASE; // Wheel Base is the Diameter
	static final double MM_PER_DEGREE = FULL_CIRCLE/360;
	static final double VELOCITY = 10 * MM_PER_DEGREE / 10;

	public double targetAngle;
	public double angle;
	double lastSetPosition;
	double tgtPosL = 0;
	double tgtPosR = 0;
	double basePosL = 0;
	double basePosR = 0;
	double lastRateAngle = 0;
	double velocity = 0;
	
	long _startTime;
	int nLoop = 0;


  public TurnByDegrees(double angle) {
		this.angle = angle;
	}
	
	


  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
		Robot.chassis.Set_K_P(K_P);
    Robot.chassis.SetCommand(this);
		_startTime = System.currentTimeMillis();
		targetAngle= Robot.chassis.NormalizeAngle(angle + Robot.chassis.GetAngle());
		lastRateAngle = Robot.chassis.GetAngle();
		nLoop = 0;
		System.out.println("Turn By Degree to " + angle +  " startTime = " + _startTime + " start angle=" + Robot.chassis.GetAngle() + 
				" Target=" + targetAngle);
		setPosition(angle);
		
  }
  
  private double remaining() {
		return targetAngle - Robot.chassis.GetAngle();
	}
	
	private void setPosition(double angle) {
		if(angle > 30) {
			velocity = VELOCITY;
		} else if(angle > 2) {
			velocity = VELOCITY / 2;
		} else if(angle < -30) {
			velocity =  -VELOCITY;
		} else if(angle < -2) {
			velocity = -VELOCITY / 2;
		} else {
			velocity -= 0;
		}
		System.out.println("Turn to " + angle + " velocity = " + velocity);
		Robot.chassis.SetValue(velocity, -velocity);
	}


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    nLoop++;
		if(nLoop == 2) {
			setPosition(remaining());
		}
		if(Math.abs(velocity) > VELOCITY && Math.abs(remaining()) < 30) {
			setPosition(remaining());
		}

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((angle >= 0 && remaining() <=3 ) || (angle < 0 && remaining() >= -3)) {
			return true;
		}
		return false;
	}


  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("END Turn By Degree to " + angle +  " end angle=" + Robot.chassis.GetAngle()); 
    Robot.chassis.motorsLeft.ConfigKP(GroupOfMotors.K_P);
    Robot.chassis.motorsRight.ConfigKP(GroupOfMotors.K_P);
		Robot.chassis.StopMotors();
		Robot.chassis.SetCommand(null);

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
