// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ExtendConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.commands.*;
import frc.robot.commands.Auton_Commands.*;

// import frc.robot.commands.ResetExtend;

import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExtendSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PivotSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final PivotSubsystem pivot = new PivotSubsystem();
  public final ExtendSubsystem extend = new ExtendSubsystem();
  public final IntakeSubsystem intake = new IntakeSubsystem();
  public final DrivetrainSubsystem drive = new DrivetrainSubsystem();

  private final Command ConeMobilityCommand = new ConeMobility(intake, drive, extend, pivot);
  private final Command ConeCommand = new Cone(intake, drive, extend, pivot);
  private final Command CubeMobilityCommand = new CubeMobility(intake, drive, extend, pivot);
  private final Command CubeCommand = new Cube(intake, drive, extend, pivot);
  private final Command NothingCommand = null;
  private final Command Moving2FeetCommand = new JoysticktoEncodedDrive(drive, 24, false);

  // private final Command

  SendableChooser<Command> autoChooser = new SendableChooser<>();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(0);

  public RobotContainer() {
    autoChooser.setDefaultOption("Cone", ConeCommand);
    autoChooser.addOption("Cone + Mobility", ConeMobilityCommand);
    autoChooser.addOption("Cube", CubeCommand);
    autoChooser.addOption("Cube + Mobility", CubeMobilityCommand);
    autoChooser.addOption("Nothing", NothingCommand);
    Shuffleboard.getTab("Auton").add(autoChooser);

    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    CameraServer.startAutomaticCapture();
    // new ResetExtend(extend).schedule();

    // Drive
    drive.setDefaultCommand(new JoystickToDrive(drive, m_driverController, 1, 4));
    m_driverController.povUp().onTrue(new InstantCommand(() -> drive.invertDriveType()));

    m_driverController.start().onTrue(new InstantCommand(() -> drive.resetEncoders()));


//    m_driverController.povLeft()
//        .toggleOnTrue(new StartEndCommand(() -> drive.setDriveMax(0.5), () -> drive.setDriveMax(1.0), drive));
    // m_driverController.povUp().onFalse(new InstantCommand(() ->
    // drive.setDriveType(NeutralMode.Coast)));
    
    
    // Pivot Commands

    // Reset pivot and extend
    m_driverController
        .a()
        .onTrue(
            new ExtendCommand(extend, ExtendConstants.in).alongWith(
                new SequentialCommandGroup(new WaitCommand(0.6),
                    new PivotCommand(pivot, PivotConstants.home))));

    // Substation pivot and extend
    m_driverController
        .b()
        .onTrue(
            new PivotCommand(pivot, PivotConstants.substation).alongWith(
                new SequentialCommandGroup(new WaitCommand(0.5),
                    new ExtendCommand(extend, ExtendConstants.sub))));

    // Middle node pivot and extend
    m_driverController
        .x()
        .onTrue(
            new PivotCommand(pivot, PivotConstants.middleNode).alongWith(
                new SequentialCommandGroup(new WaitCommand(0.6),
                    new ExtendCommand(extend, ExtendConstants.out))));

    m_driverController
        .y()
        .onTrue(
            new GroundPickupCommand(intake, extend, pivot)  
        );

m_driverController
    .povLeft()
    .onTrue(
            new ExtendCommand(extend, ExtendConstants.sub).alongWith(
                new SequentialCommandGroup(new WaitCommand(0.6),
                    new PivotCommand(pivot, PivotConstants.clearBumpers))));
    
    m_driverController
        .leftBumper()
        .onTrue(
            new ExtendCommand(extend, ExtendConstants.in));

    m_driverController
        .povDown()
        .onTrue(
            new ExtendCommand(extend, ExtendConstants.sub));

    m_driverController
        .rightBumper()
        .onTrue(
            new ExtendCommand(extend, ExtendConstants.out));

    // m_driverController.povRight().onTrue(new JoystickToDriveGyro(drive.getYaw() - 90, drive));
    // m_driverController.povLeft().onTrue(new JoystickToDriveGyro(drive.getYaw() + 90, drive));
   
    // Grabber Commands
    m_driverController.leftTrigger()
        .onTrue(new InstantCommand(() -> intake.setSpeed(IntakeConstants.OUT_SPEED)))
        .onFalse(new InstantCommand(() -> intake.setSpeed(IntakeConstants.STOP_SPEED)));

    m_driverController.rightTrigger()
        .onTrue(new InstantCommand(() -> intake.setSpeed(IntakeConstants.IN_SPEED)))
        .onFalse(new InstantCommand(() -> intake.setSpeed(IntakeConstants.STOP_SPEED)));

        // new StartEndCommand(() -> intake.setSpeed(IntakeConstants.IN_SPEED), () -> intake.setSpeed(IntakeConstants.STOP_SPEED), intake);
        // new StartEndCommand(() -> intake.setSpeed(IntakeConstants.OUT_SPEED), () -> intake.setSpeed(IntakeConstants.STOP_SPEED), intake);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Moving2FeetCommand;//autoChooser.getSelected(); 

  }
}
