package frc.robot.subsystems;

 

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

 

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.CANIDConstants;

 

public class IntakeSubsystem extends SubsystemBase{

    private final CANSparkMax motor = new CANSparkMax(CANIDConstants.INTAKE, MotorType.kBrushless);

    public IntakeSubsystem() {

        motor.setIdleMode(IdleMode.kBrake);

    }

    public void setSpeed(double speed) {

       motor.set(speed);

       SmartDashboard.putNumber("Intake Speed", speed);

      }

 

}

 