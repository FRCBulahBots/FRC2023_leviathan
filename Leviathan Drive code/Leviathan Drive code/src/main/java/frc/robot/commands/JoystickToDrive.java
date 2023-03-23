/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

//A simple drive command for... ya know, driving.
public class JoystickToDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drive;
  private double move;
  private double turn;
  private final Joystick joyStick;
  private final int axis1;
  private final int axis2;

  
  public JoystickToDrive(Drivetrain drive, Joystick joyStick, int axis1, int axis2) {
    this.drive = drive;
    this.joyStick = joyStick;
    this.axis1 = axis1;
    this.axis2 = axis2;

    addRequirements(drive);
  }

  @Override
  public void execute() {  
    if (DriverStation.isAutonomous()){
      return;
    }
    move = -joyStick.getRawAxis(axis1);
    turn = -joyStick.getRawAxis(axis2);
    drive.arcadeDrive(-move, -turn);
  }

}
