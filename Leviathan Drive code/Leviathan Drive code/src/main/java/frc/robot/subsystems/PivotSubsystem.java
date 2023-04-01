package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.CANIDConstants;
import frc.robot.Constants.PivotConstants;

public class PivotSubsystem extends ProfiledPIDSubsystem {
  private final CANSparkMax motor = new CANSparkMax(CANIDConstants.ARM_PIVOT, MotorType.kBrushless);
  double squaredOutput, finalOutput;

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

    //exponential output for the arm
    squaredOutput = Math.pow(output, 2);
    if (output < 0){
      squaredOutput = -squaredOutput;
    }
    finalOutput = clamp(squaredOutput,-.5,.5);
    
    motor.set(output);

    // motor.set(output);

    SmartDashboard.putNumber("Pivot Motor Output", motor.get());
    SmartDashboard.putNumber("Pivot Encoder Value", motor.getEncoder().getPosition());

  }

  @Override
  public double getMeasurement() {
    double pivotPosition = motor.getEncoder().getPosition();
    return pivotPosition;
  }

  private static double clamp(double val, double min, double max) {
    return Math.max(min, Math.min(max, val));
  }
}