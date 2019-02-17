/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Vision.VisionServer;
import frc.robot.commands.ControlArm;
import frc.robot.commands.DriveByJoystickCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.GoStraight1;
import frc.robot.commands.LockJacks;
import frc.robot.commands.TurnByR;
import frc.robot.commands.UnDropHatch;
import frc.robot.commands.controlClimbing1;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.DriverInterface;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.HatchPanelsSystem;
import frc.robot.subsystems.Climb;;

public class Robot extends TimedRobot {
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static Chassis chassis;
  public static DriverInterface driverInterface;
  public static Robot robot = null;
 // public static Lift lift;
  public static HatchPanelsSystem hatchPanelsSystem;
  public static Command teleopCommand;
  public static Compressor compressor;
  public static Climb climb;
  public VisionServer visionServer;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  ControlArm controlArm;
  DriveByJoystickCommand driveCommand;
  

  @Override
  public void robotInit() {
    visionServer = new VisionServer();
    robot = this;
    chassis= new Chassis();
    driverInterface= new DriverInterface();
    m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    driveCommand = new DriveByJoystickCommand();
    teleopCommand = driveCommand;
    compressor = new Compressor(RobotMap.portPCM);
    compressor.start();
    hatchPanelsSystem = new HatchPanelsSystem();
    SmartDashboard.putData(new GoStraight1());
    climb = new Climb();
    controlArm = new ControlArm();

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    initEnabled();
    chassis.setSlowMode();
    m_autonomousCommand = new TurnByR(90, 60, 500);
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    enabledPeriodic();
  }

  @Override
  public void teleopInit() {
    initEnabled();
    chassis.setSlowMode();//setFastMode();
    controlArm.start();
    teleopCommand.start();
   
  }


  @Override
  public void teleopPeriodic() {
    enabledPeriodic();  
  }

  @Override
  public void testPeriodic() {
  }

  private void initEnabled() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    if(teleopCommand.isRunning()) {
      teleopCommand.close();
    }
    if(controlArm.isRunning()) {
      controlArm.close();
    }
//    controlArm.start();
    chassis.resetEncs();
    compressor.start();
   /* Command c = new LockJacks();
    c.start();
    c = new UnDropHatch();
    c.start(); */
  }

  private void enabledPeriodic() {
    chassis.UpdateStatus();
    hatchPanelsSystem.UpdateStatus();
    Scheduler.getInstance().run();
  }
}
