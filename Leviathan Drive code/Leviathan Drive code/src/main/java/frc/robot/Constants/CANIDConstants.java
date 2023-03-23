// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Constants;

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
  public final class CANIDConstants {
    // #region Drive Motors
    // Foremost Left Falcon
    public static int DriveLeftMaster = 1;
    // Rearmost Left Falcon
    public static int DriveLeftFollower = 2;
    // Foremost Right Falcon
    public static int DriveRightMaster = 3;
    // Rearmost Right Falcon
    public static int DriveRightFollower = 4;
    // #endregion

    // #region Arm Subsystem
    public static int pivot = 7;
    public static int extend = 12;
    public static int grab = 10;
    // #endregion

    // #region Misc
    public static int gyro = 8;
    // #endregion
  }

