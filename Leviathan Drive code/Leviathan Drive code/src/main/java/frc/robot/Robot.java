// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.StopBits;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.DriveTrainConstants;
import frc.robot.Constants.ExtendConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExtendSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PivotSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private SerialPort arduino;
  private Timer timer;

  private byte[] colorSelected;
  private SendableChooser<byte[]> colorChooser = new SendableChooser<>();
  private byte[] Startup = {0x0};
  private byte[] BlueAlliance = {0x1};
  private byte[] RedAlliance = {0x2};
  private byte[] Cone = {0x3};
  private byte[] Cube = {0x4};
  private byte[] RGB = {0x5};
  
  private Command m_autonomousCommand;
  private RobotContainer robotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    colorChooser.setDefaultOption("Startup", Startup);
    colorChooser.addOption("Blue Alliance", BlueAlliance);
    colorChooser.addOption("Red Alliance", RedAlliance);
    colorChooser.addOption("Cone", Cone);
    colorChooser.addOption("Cube", Cube);
    colorChooser.addOption("RGB", RGB);
    SmartDashboard.putData(colorChooser);
    try {
      arduino = new SerialPort(9600, SerialPort.Port.kUSB);
      System.out.println("Connected!");
    } catch (Exception e) {
      System.out.println("Failed poopy poop");
    }
    timer = new Timer();
    timer.start();
    //arduino.write(Startup, 1);
  }

  @Override
  public void robotPeriodic() {
    colorSelected = colorChooser.getSelected();
    if(timer.get() > .5){
      System.out.println("Wrote to Arduino");
      arduino.write(colorSelected, 1);
      if(arduino.getBytesReceived() > 0){
        System.out.println(colorSelected);
      }
      timer.reset();
    }
CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    // A "Capture Try/Catch". Tries all the possible serial port
    // connections that make sense if you're using the USB ports
    // on the RoboRIO. It keeps trying unless it never finds anything.
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
