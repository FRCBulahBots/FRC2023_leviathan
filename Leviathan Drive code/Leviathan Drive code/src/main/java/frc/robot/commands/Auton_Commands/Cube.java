package frc.robot.commands.Auton_Commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.PivotConstants;
import frc.robot.commands.ExtendCommand;
import frc.robot.commands.PivotCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExtendSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.Constants.ExtendConstants;
import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Cube extends SequentialCommandGroup {
    public Cube(IntakeSubsystem intake, DrivetrainSubsystem drive, ExtendSubsystem extend, PivotSubsystem pivot) {
        // addCommands(new RunCommand(() -> drive.arcadeDrive(0.6, 0),
        // drive).withTimeout(2));

        addCommands(
            new InstantCommand(() -> drive.setDriveType(NeutralMode.Brake)),
            new PivotCommand(pivot, PivotConstants.middleNode).alongWith(
                    new SequentialCommandGroup(new WaitCommand(.6),
                            new ExtendCommand(extend, ExtendConstants.out))),
            new WaitCommand(1.7),
            new RunCommand(() -> intake.setSpeed(IntakeConstants.OUT_SPEED)).withTimeout(2.5),
            new InstantCommand(() -> intake.setSpeed(IntakeConstants.STOP_SPEED)),
            new WaitCommand(1),
            new ParallelCommandGroup(
                    new ExtendCommand(extend, ExtendConstants.in).alongWith(
                            new SequentialCommandGroup(new WaitCommand(2),
                                    new PivotCommand(pivot, PivotConstants.home)
                                            .andThen(new RunCommand(() -> drive.arcadeDrive(0.0, 0), drive)
                                            .withTimeout(2).andThen(new InstantCommand(() -> drive.setDriveType(NeutralMode.Coast))))))));
    }
}
