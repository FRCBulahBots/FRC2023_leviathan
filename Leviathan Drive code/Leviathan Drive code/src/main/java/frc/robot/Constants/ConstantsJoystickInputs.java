// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
  /**
   * Constants_AN
   */
  public final class ConstantsJoystickInputs {
    public static Button MoveArmTopRow = XboxController.Button.kY;
    public static Button MoveArmMiddleRow = XboxController.Button.kX;
    public static Button MoveArmBottomRow = XboxController.Button.kA;
    
    public static Button MoveToGroundPickupPosition = XboxController.Button.kB;
    public static Button RetractArm = XboxController.Button.kLeftBumper;
    public static Button ExtendArm = XboxController.Button.kRightBumper;
  }

