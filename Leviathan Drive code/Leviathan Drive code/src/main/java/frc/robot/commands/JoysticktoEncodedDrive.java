
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DrivetrainSubsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.CommandBase;



public class JoysticktoEncodedDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final DrivetrainSubsystem drive;
  private double avgEncoderPos;
  private double targetEncoderCount;
  private double speed;



  public JoysticktoEncodedDrive(DrivetrainSubsystem drive, double distanceToTravelInInches, boolean forward) {
    this.drive = drive;
    speed = forward ? -0.4: 0.4;
    targetEncoderCount = 668 * distanceToTravelInInches;
    addRequirements(drive);

  }


  @Override
  public void initialize() {
    drive.setDriveType(NeutralMode.Brake);
    drive.resetEncoders();
  }


  @Override
  public void execute() {
    double encoderVal = drive.getDriveEncodersAverage();
    avgEncoderPos = encoderVal;

    drive.arcadeDrive(speed, 0);
  }

  @Override
  public void end(boolean interrupted) {
    drive.arcadeDrive(0, 0);
    //drive.setDriveType(NeutralMode.Brake);
  }
  @Override
  public boolean isFinished() {
    return Math.abs(avgEncoderPos) >= targetEncoderCount;
  }

}