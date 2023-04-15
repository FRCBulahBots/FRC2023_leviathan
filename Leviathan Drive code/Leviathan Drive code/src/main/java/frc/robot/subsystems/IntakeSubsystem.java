package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.CANIDConstants;

public class IntakeSubsystem extends SubsystemBase {

    private final CANSparkMax motor = new CANSparkMax(CANIDConstants.INTAKE, MotorType.kBrushless);

    ShuffleboardTab intakeTab = Shuffleboard.getTab("Arm Data");
    GenericEntry intakeSpeed = intakeTab.addPersistent("Intake Speed", 0).getEntry();

    public IntakeSubsystem() {
        motor.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void periodic() {
        intakeSpeed.setDouble(motor.get());
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }

}
