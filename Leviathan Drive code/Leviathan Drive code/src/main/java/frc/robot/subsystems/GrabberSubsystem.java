package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.CANIDConstants;
import frc.robot.Constants.GrabberConstants;

public class GrabberSubsystem extends ProfiledPIDSubsystem {
  private final WPI_TalonSRX motor = new WPI_TalonSRX(CANIDConstants.GRAB);

  public GrabberSubsystem() {
    // We MUST call the super to supply the configured ProfiledPIDController to the
    // base class.
    super(
        new ProfiledPIDController(
            GrabberConstants.PID_P,
            GrabberConstants.PID_I,
            GrabberConstants.PID_D,
            new TrapezoidProfile.Constraints(
                GrabberConstants.MAX_VEL,
                GrabberConstants.MAX_ACCEL)),
        0);

    motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    motor.setNeutralMode(NeutralMode.Brake);

    // Start arm at rest in neutral position
    // TODO: figure out what our neutral pos should be
    // setGoal(GrabberConstants.kArmOffsetRads);
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // TODO: Add a feedforward to the PID output potentially
    motor.set(output);
  }

  @Override
  public double getMeasurement() {
    return motor.getSelectedSensorPosition();
  }
}