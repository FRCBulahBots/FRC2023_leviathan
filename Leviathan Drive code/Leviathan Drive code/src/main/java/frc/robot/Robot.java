// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
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
  private double startTime;

  private DrivetrainSubsystem drive = new DrivetrainSubsystem();
  private IntakeSubsystem intake = new IntakeSubsystem();
  private PivotSubsystem pivot = new PivotSubsystem();
  private ExtendSubsystem extend = new ExtendSubsystem();

  private SerialPort arduino; //The serial port that we try to communicate with
  private Timer timer; //The timer to keep track of when we send our signal to the Arduino

  private String colorSelected;
  private final SendableChooser<String> colorChooser = new SendableChooser<>();

  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    colorChooser.setDefaultOption("Blue", "blue");
    colorChooser.addOption("Red", "red");
    colorChooser.addOption("Yellow", "yellow");
    colorChooser.addOption("Purple", "purple");
    SmartDashboard.putData("Color Choices", colorChooser);
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    //A "Capture Try/Catch". Tries all the possible serial port
    //connections that make sense if you're using the USB ports
    //on the RoboRIO. It keeps trying unless it never finds anything.
    try {
      arduino = new SerialPort(9600, SerialPort.Port.kUSB);
      System.out.println("Connected on kUSB!");
    } catch (Exception e) {
      System.out.println("Failed to connect on kUSB, trying kUSB 1");

      try {
        arduino = new SerialPort(9600, SerialPort.Port.kUSB1);
        System.out.println("Connected on kUSB1!");
      } catch (Exception e1) {
        System.out.println("Failed to connect on kUSB1, trying kUSB 2");

        try {
          arduino = new SerialPort(9600, SerialPort.Port.kUSB2);
          System.out.println("Connected on kUSB2!");
        } catch (Exception e2) {
          System.out.println("Failed to connect on kUSB2, all connection attempts failed!");
        }
      }
    }

    //Create a timer that will be used to keep track of when we should send
    //a signal and start it. 
    timer = new Timer();
    timer.start();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //If more than 5 seconds has passed
    colorSelected = colorChooser.getSelected();
    if(timer.get() > 5) {
      //Output that we wrote to the arduino, write our "trigger byte"
      //to the arduino and reset the timer for next time
      System.out.println("Wrote to Arduino");
      arduino.writeString(colorSelected);
      timer.reset();
      if(arduino.getBytesReceived() > 0) {
        System.out.print(arduino.readString());
      }
    }
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
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
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    startTime = Timer.getFPGATimestamp();
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();
    if (time - startTime < 1) {
      Commands.runOnce(
          () -> {
            pivot.setGoal(PivotConstants.highNode);
            pivot.enable();
          },
          pivot);
    } else if (time - startTime > 1 && time - startTime < 3) {
      Commands.runOnce(
          () -> {
            pivot.setGoal(PivotConstants.highNode);
            pivot.enable();
          },
          pivot);
      Commands.runOnce(
          () -> {
            extend.setGoal(ExtendConstants.out);
            extend.enable();
          },
          extend);
    } else if (time - startTime > 3 && time - startTime < 4) {
      new InstantCommand(() -> intake.setSpeed(IntakeConstants.OUT_SPEED));
    } else if (time - startTime > 4 && time - startTime < 6) {
      new InstantCommand(() -> intake.setSpeed(IntakeConstants.STOP_SPEED));
      drive.arcadeDrive(-.6, 0);
    } else {
      drive.arcadeDrive(0, 0);
    }
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
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

