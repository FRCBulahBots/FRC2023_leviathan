import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.CANIDConstants;

public class PivotSubsystem extends ProfiledPIDSubsystem {
  private final CANSparkMax motor = new CANSparkMax(CANIDConstants.ARM_PIVOT, MotorType.kBrushless);

  public PivotSubsystem() {
    // We MUST call the super to supply the configured ProfiledPIDController to the base class.
    super(
        new ProfiledPIDController(
            PivotConstants.PID_P,
            PivotConstants.PID_I,
            PivotConstants.PID_D,
            new TrapezoidProfile.Constraints(
                PivotConstants.MAX_VEL,
                PivotConstants.MAX_ACCEL)),
        0);

    // Brake mode is a safer here than Coast, as it reduces strain on control loop to maintain constant position,
    // and in the event the robot is disabled, the arm wont come crashing down so fast.
    motor.setIdleMode(IdleMode.kBrake);
    // Start arm at rest in neutral position
    // TODO: Should we have a defaulted position we should set as goal? we probably need a real encoder on the pivot point before this.
    //setGoal(ArmConstants.kArmOffsetRads);
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // TODO: Add a feedforward to the PID output potentially
    motor.set(output);
  }

  @Override
  public double getMeasurement() {
    return motor.getEncoder().getPosition();
  }
}