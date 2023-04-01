package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

    // make sure we coast.
    setDriveType(NeutralMode.Coast);
  }

  public void setDriveMax(double maxValue){
    drive.setMaxOutput(maxValue);
  }

  // Method to quickly switch drivetypes from coast to brake, and vice versa:
  public void setDriveType(NeutralMode driveMode) {
    mode = driveMode;
    leftMaster.setNeutralMode(driveMode);
    rightMaster.setNeutralMode(driveMode);
    // leftFollower.setNeutralMode(driveMode);
    // rightFollower.setNeutralMode(driveMode);
  }

  public void invertDriveType(){
    if(mode == NeutralMode.Coast)
      setDriveType(NeutralMode.Brake);
    else
      setDriveType(NeutralMode.Coast);
  }

  public double getPitch(){
    return Math.IEEEremainder(pigeonGyro.getPitch(), 360);
  }

  // Main driving method: a simple DifferentialDrive encapsulated method
  public void arcadeDrive(double speed, double rotation) {
    drive.arcadeDrive(DriveTrainConstants.FWD_REV_MULT * speed, rotation * DriveTrainConstants.ROT_MULT);
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("GyroData", pigeonGyro.getYaw());
  }

}
