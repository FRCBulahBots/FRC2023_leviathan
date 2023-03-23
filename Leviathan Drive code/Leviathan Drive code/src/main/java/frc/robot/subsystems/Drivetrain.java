package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANIDConstants;

//Drivetrain Class written primarily by Ryan Wilks and cleaned up by Rohan Durgum
public class Drivetrain extends SubsystemBase {

  // Instances of Falcons, our Drivesystem, and the Pigeon Gyro:
  public static WPI_TalonFX rightMaster = new WPI_TalonFX(CANIDConstants.DriveRightMaster);
  public static WPI_TalonFX leftMaster = new WPI_TalonFX(CANIDConstants.DriveLeftMaster);

  public static WPI_TalonFX leftFollower = new WPI_TalonFX(CANIDConstants.DriveLeftFollower);
  public static WPI_TalonFX rightFollower = new WPI_TalonFX(CANIDConstants.DriveRightFollower);

  private DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);

  private static Pigeon2 pigeonGyro = new Pigeon2(CANIDConstants.gyro);

  public Drivetrain() {

    leftMaster.configFactoryDefault();
    leftFollower.configFactoryDefault();

    rightMaster.configFactoryDefault();
    rightFollower.configFactoryDefault();

    // Set followers to follow masters
    leftFollower.follow(leftMaster);
    rightFollower.follow(rightMaster);

    leftMaster.setInverted(false);
    leftFollower.setInverted(false);

    // Right side is inverted in hardware, so set according on software
    rightMaster.setInverted(true);
    rightFollower.setInverted(true);

    // make sure we coast.
    setDriveType(NeutralMode.Coast);

  }

  // Method to quickly switch drivetypes from coast to brake, and vice versa:
  public static void setDriveType(NeutralMode driveMode) {
    leftMaster.setNeutralMode(driveMode);
    rightMaster.setNeutralMode(driveMode);
    leftFollower.setNeutralMode(driveMode);
    rightFollower.setNeutralMode(driveMode);
  }

  // Method for making sure motors do not move unless signaled to; helpful in
  // auton!
  public void setAllNeutralOutput() {
    leftMaster.neutralOutput();
    rightMaster.neutralOutput();
    leftFollower.neutralOutput();
    rightFollower.neutralOutput();
  }

  // Main driving method: a simple DifferentialDrive encapsulated method!
  public void arcadeDrive(double speed, double rotation) {
    // double trimAdjustedRot = (rotation + trim.getDouble(-0.044));
    // this.clamp(trimAdjustedRot, -1, 1);
    drive.arcadeDrive(1.0 * speed, 0.7 * rotation);
  }

  // A method to limit our max output.
  public void setMaxOutput(double maxValue) {
    drive.setMaxOutput(maxValue);
  }

  public void resetEncoders() {
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
  }

  // Drive encoder getters
  public double getAverageMasterEncoderValue() {
    return (leftMaster.getSelectedSensorPosition() + rightMaster.getSelectedSensorPosition()) / 2;
  }

  public double getLeftEncoder() {
    return leftMaster.getSelectedSensorPosition();
  }

  public static double getPitch() {
    return Math.IEEEremainder(pigeonGyro.getPitch(), 360);
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("LeftEncoderValue", this.checkTestEncoder());
    SmartDashboard.putNumber("GyroData", pigeonGyro.getYaw());
  }

}
