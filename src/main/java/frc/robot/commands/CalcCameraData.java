/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.VectorsCalculate;
import frc.robot.Vision.VisionData;
import frc.robot.subsystems.HatchPanelsSystem;

public class CalcCameraData extends Command {

  boolean isFound;
  boolean isPush;

  public CalcCameraData(boolean isPush) {
    this.isPush = isPush;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isFound = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.chassis.isReverseMode()){
      return VisionData.backData.found;
    }else{
      return VisionData.frontData.found;
    } 
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    VectorsCalculate vectors;

    if(Robot.chassis.isReverseMode()){
      vectors = new VectorsCalculate(VisionData.frontData.p1.a, VisionData.frontData.p1.d,
      VisionData.frontData.p2.a, VisionData.frontData.p2.d);
      Robot.hatchPanelsSystem.SetPosituon(HatchPanelsSystem.CHANGE_DIR_MOVE);   
    }else{
      vectors = new VectorsCalculate(VisionData.backData.p1.a, VisionData.backData.p1.d,
      VisionData.backData.p2.a, VisionData.backData.p2.d);   
      Robot.hatchPanelsSystem.SetPosituon(0);   
    } 
    
    (new ControlHatchByVision(vectors.Get_First_vector().angle, vectors.Get_First_vector().length,
     vectors.Get_R_vector().length, vectors.Get_Line_vector().angle, 
     vectors.Get_Final_Vector().length, isPush)).start();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
