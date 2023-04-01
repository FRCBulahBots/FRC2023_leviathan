package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.CANIDConstants;
import frc.robot.Constants.ExtendConstants;

public class ExtendSubsystem extends ProfiledPIDSubsystem {
  private final CANSparkMax motor = new CANSparkMax(CANIDConstants.EXTEND_RETRACT, MotorType.kBrushless);

  public ExtendSubsystem() {
    // We MUST call the super to supply the configured ProfiledPIDController to the
    // base class.
    super(
        new ProfiledPIDController(
            ExtendConstants.PID_P,
            ExtendConstants.PID_I,
            ExtendConstants.PID_D,
            new TrapezoidProfile.Constraints(
                ExtendConstants.MAX_VEL,
                ExtendConstants.MAX_ACCEL)),
        0);

    // Brake mode is a safer here than Coast, as it reduces strain on control loop
    // to maintain constant position,
    // and in the event the robot is disabled, the arm wont come crashing down so
    // fast.
    motor.setIdleMode(IdleMode.kBrake);
    // Start arm at rest in neutral position
    // TODO: Should we have a defaulted position we should set as goal? we probably
    // need a real encoder on the pivot point before this.
    // setGoal(ArmConstants.kArmOffsetRads);
  }
  // @Override
  // public void periodic() {
  //   SmartDashboard.putNumber("Extend Encoder Value", motor.getEncoder().getPosition());
  // }

  // @Override
  // public void periodic() {
  //     // TODO Auto-generated method stub
  //     super.periodic();
  //     SmartDashboard.putNumber("Extend Encoder Value", motor.getEncoder().getPosition()); 
  // }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // TODO: Add a feedforward to the PID output potentially
    motor.set(output);
    SmartDashboard.putNumber("Extend Output Value", output);
  }

  @Override
  public double getMeasurement() {
    double extendPosition = motor.getEncoder().getPosition();
    SmartDashboard.putNumber("Extend Encoder Value", extendPosition);
    return extendPosition;
  }
}