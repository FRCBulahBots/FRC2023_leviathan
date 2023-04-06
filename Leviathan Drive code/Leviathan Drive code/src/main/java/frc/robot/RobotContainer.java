// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ExtendConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.commands.ConeMobility;
import frc.robot.commands.JoystickToDrive;
import frc.robot.commands.JoystickToDriveGyro;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExtendSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PivotSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.SendableCameraWrapper;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final PivotSubsystem pivot = new PivotSubsystem();
  public final ExtendSubsystem extend = new ExtendSubsystem();
  public final IntakeSubsystem intake = new IntakeSubsystem();
  public final DrivetrainSubsystem drive = new DrivetrainSubsystem();
  private final Command ConeMobilityCommand = new ConeMobility(intake, drive, extend, pivot);
  private final Command ConeCommand = new ConeMobility(intake, drive, extend, pivot);
  private final Command NothingCommand = null;

  SendableChooser<Command> autoChooser = new SendableChooser<>();
  
  
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(0);
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    autoChooser.setDefaultOption("Cone + Mobility", ConeMobilityCommand);
    autoChooser.addOption("Cone", ConeCommand);
    autoChooser.addOption("Nothing", NothingCommand);
    SmartDashboard.putData(autoChooser);
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);
    // Drive
    drive.setDefaultCommand(new JoystickToDrive(drive, m_driverController, 1, 4));
    m_driverController.povUp().onTrue(new InstantCommand(() -> drive.invertDriveType()));

    m_driverController.povLeft().toggleOnTrue(new StartEndCommand(() -> drive.setDriveMax(0.5), () -> drive.setDriveMax(1.0), drive) );
    // m_driverController.povUp().onFalse(new InstantCommand(() ->
    // drive.setDriveType(NeutralMode.Coast)));
    // Pivot Commands

    //Reset pivot and extend
    m_driverController
        .a()
        .onTrue(
            Commands.runOnce(
                () -> {
                  extend.setGoal(ExtendConstants.in);
                  extend.enable();
                },
                extend).alongWith(
                    new SequentialCommandGroup(new WaitCommand(0.6),
                        Commands.runOnce(
                            () -> {
                              pivot.setGoal(PivotConstants.lowNode);
                              pivot.enable();
                            },
                            pivot))));

    //Substation pivot and extend
    m_driverController
        .b()
        .onTrue(
            Commands.runOnce(
                () -> {
                  pivot.setGoal(PivotConstants.substation);
                  pivot.enable();
                },
                pivot).alongWith(
                    new SequentialCommandGroup(new WaitCommand(0.5),
                        Commands.runOnce(
                            () -> {
                              extend.setGoal(ExtendConstants.sub);
                              extend.enable();
                            },
                            extend))));

    //Middle node pivot and extend
    m_driverController
        .x()
        .onTrue(
            Commands.runOnce(
                () -> {
                  pivot.setGoal(PivotConstants.middleNode);
                  pivot.enable();
                },
                pivot).alongWith(
                    new SequentialCommandGroup(new WaitCommand(.6),
                        Commands.runOnce(
                            () -> {
                              extend.setGoal(ExtendConstants.out);
                              extend.enable();
                            },
                            extend))));

    m_driverController
        .y()
        .onTrue(
            Commands.runOnce(
                () -> {
                  pivot.setGoal(PivotConstants.clearBumpers);
                  pivot.enable();
                },
                pivot));
   //
    m_driverController
        .leftBumper()
        .onTrue(
            Commands.runOnce(
                () -> {
                  extend.setGoal(ExtendConstants.in);
                  extend.enable();
                },
                extend));
    m_driverController
        .povDown()
        .onTrue(
            Commands.runOnce(
                () -> {
                  extend.setGoal(ExtendConstants.sub);
                  extend.enable();
                },
                extend));

    m_driverController
        .rightBumper()
        .onTrue(
            Commands.runOnce(
                () -> {
                  extend.setGoal(ExtendConstants.out);
                  extend.enable();
                },
                extend));

      m_driverController.povRight().onTrue(new JoystickToDriveGyro(drive.getYaw() - 90, drive));
      m_driverController.povLeft().onTrue(new JoystickToDriveGyro(drive.getYaw() + 90, drive));
    // Grabber Commands
    m_driverController.leftTrigger()
        .onTrue(new InstantCommand(() -> intake.setSpeed(IntakeConstants.OUT_SPEED)))
        .onFalse(new InstantCommand(() -> intake.setSpeed(IntakeConstants.STOP_SPEED)));

    m_driverController.rightTrigger()
        .onTrue(new InstantCommand(() -> intake.setSpeed(IntakeConstants.IN_SPEED)))
        .onFalse(new InstantCommand(() -> intake.setSpeed(IntakeConstants.STOP_SPEED)));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}
