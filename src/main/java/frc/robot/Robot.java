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
import frc.robot.commands.DriveByJoystickCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.DriverInterface;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.HatchPanelsSystem;
import frc.robot.subsystems.Lift;


public class Robot extends TimedRobot {
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static Chassis chassis;
  public static DriverInterface driverInterface;
  public static Robot robot = null;
 // public static Lift lift;
  public static HatchPanelsSystem hatchPanelsSystem;
  public static Command teleopCommand;
  public static Compressor compressor;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    robot = this;
    chassis= new Chassis();
    driverInterface= new DriverInterface();
    m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    teleopCommand = new DriveByJoystickCommand();
    compressor = new Compressor(11);
  //  hatchPanelsSystem = new HatchPanelsSystem();
//    lift= new Lift();
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
    m_autonomousCommand = m_chooser.getSelected();

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
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    chassis.GyroReset();
    chassis.gyro.calibrate();
    teleopCommand.start();
    compressor.start();

  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    driverInterface.UpdateStatus();
    hatchPanelsSystem.UpdateStatus();
  //  System.out.println("left position = " + chassis.motorsLeft.GetPositionInMM());
  //  System.out.println("right position = " + chassis.motorsRight.GetPositionInMM());
  }

  @Override
  public void testPeriodic() {
  }
}
