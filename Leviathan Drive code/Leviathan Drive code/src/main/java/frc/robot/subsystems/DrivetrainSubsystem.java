package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANIDConstants;
import frc.robot.Constants.DriveTrainConstants;

public class DrivetrainSubsystem extends SubsystemBase {

  // Instances of Falcons, our Drivesystem, and the Pigeon Gyro:
  public static WPI_TalonFX rightMaster = new WPI_TalonFX(CANIDConstants.DRIVE_RIGHT_MASTER);
  public static WPI_TalonFX leftMaster = new WPI_TalonFX(CANIDConstants.DRIVE_LEFT_MASTER);

  public static WPI_TalonFX leftFollower = new WPI_TalonFX(CANIDConstants.DRIVE_LEFT_FOLLOWER);
  public static WPI_TalonFX rightFollower = new WPI_TalonFX(CANIDConstants.DRIVE_RIGHT_FOLLOWER);
  private NeutralMode mode;
  private DifferentialDrive drive = new DifferentialDrive(rightMaster, leftMaster);

  private static Pigeon2 pigeonGyro = new Pigeon2(CANIDConstants.GYRO);
  ShuffleboardTab driveTab = Shuffleboard.getTab("Drive Data");
  GenericEntry gyro = driveTab.addPersistent("Gyro Data", 0).getEntry();
  GenericEntry driveEncoders = driveTab.addPersistent("Drive Encoder Values", 0).getEntry();

  public DrivetrainSubsystem() {

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

    setDriveType(NeutralMode.Coast);

  }

  // Method for limiting out arcadeDrive's calculations
  public void setDriveMax(double maxValue) {
    drive.setMaxOutput(maxValue);
  }

  // Method to quickly switch drivetypes from coast to brake, and vice versa:
  public void setDriveType(NeutralMode driveMode) {
    mode = driveMode;
    leftMaster.setNeutralMode(driveMode);
    rightMaster.setNeutralMode(driveMode);
    leftFollower.setNeutralMode(driveMode);
    rightFollower.setNeutralMode(driveMode);
  }

  //
  public void invertDriveType() {
    if (mode == NeutralMode.Coast)
      setDriveType(NeutralMode.Brake);
    else
      setDriveType(NeutralMode.Coast);

  }

  // methods for getting yaw and tilt of the bot
  public double getYaw() {
    return pigeonGyro.getYaw();
  }

  public double getTilt() {
    return Math.IEEEremainder(getPitch(), 360);
  }

  // methods for getting pitch and heading of the bot
  public double getPitch() {
    return pigeonGyro.getPitch();
  }

  public double getHeading() {
    return Math.IEEEremainder(getYaw(), 360);
  }

  // Main driving method: a simple DifferentialDrive encapsulated method
  public void arcadeDrive(double speed, double rotation) {
    drive.arcadeDrive(DriveTrainConstants.FWD_REV_MULT * speed, rotation * DriveTrainConstants.ROT_MULT);
  }

  // getting Drive encoder values
  public double getDriveEncodersAverage() {
    return (leftMaster.getSelectedSensorPosition() + rightMaster.getSelectedSensorPosition()) / 2;
  }

  public void neutralOutputAll() {
    leftMaster.neutralOutput();
    leftFollower.neutralOutput();
    rightMaster.neutralOutput();
    rightFollower.neutralOutput();
  }

  public void resetEncoders() {
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Gyro Raw Data", getHeading());
    // SmartDashboard.putNumber("Gyro Heading", getHeading());
    SmartDashboard.putBoolean("Is drive on brake?", mode == NeutralMode.Brake);

    gyro.setDouble(getYaw());
    driveEncoders.setDouble(getDriveEncodersAverage());

  }

}
