/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ControlHatchByVision extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ControlHatchByVision(double angle, double length, double lengthR, double angleLine, 
  double lengthFinal, boolean isPush) {
    addSequential(new TurnByDegrees(angle, 90));
    addSequential(new GoStraight(length, 1000, false, angle));
    addSequential(new TurnByR(angleLine, 90, lengthR, false, true));
    addSequential(new GoStraight(lengthFinal, 1000, isPush, angleLine));
    if(isPush){
      addSequential(new DropHatch());
    }
    else{
      addSequential(new GoStraight(500, 500, true, 1000));
    }
    addSequential(new ChangeRobotDirection());
    addSequential(new GoStraight(500, 700));
  }
}
