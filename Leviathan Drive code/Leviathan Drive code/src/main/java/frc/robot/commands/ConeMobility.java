package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExtendSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.Constants.ExtendConstants;
import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ConeMobility extends SequentialCommandGroup {
    public ConeMobility(IntakeSubsystem intake, DrivetrainSubsystem drive, ExtendSubsystem extend, PivotSubsystem pivot) {
        // addCommands(new RunCommand(() -> drive.arcadeDrive(0.6, 0),
        // drive).withTimeout(2));

        addCommands(new InstantCommand(() -> drive.setDriveType(NeutralMode.Brake)), Commands.runOnce(
                () -> {
                    pivot.setGoal(PivotConstants.middleNode);
                    pivot.enable();
                },
                pivot).alongWith(
                        new SequentialCommandGroup(new WaitCommand(.6),
                                Commands.runOnce(
                                    () -> {
                                            extend.setGoal(ExtendConstants.out);
                                            extend.enable();
                                        },
                                        extend))),
                new WaitCommand(1.7),
                new RunCommand(() -> intake.setSpeed(IntakeConstants.IN_SPEED)).withTimeout(2),
                new InstantCommand(() -> intake.setSpeed(IntakeConstants.STOP_SPEED)),
                new WaitCommand(1),
                new ParallelCommandGroup(
                        Commands.runOnce(
                                () -> {
                                    extend.setGoal(ExtendConstants.in);
                                    extend.enable();
                                },
                                extend).alongWith(
                                        new SequentialCommandGroup(new WaitCommand(2),
                                                Commands.runOnce(
                                                        () -> {
                                                            pivot.setGoal(PivotConstants.lowNode);
                                                            pivot.enable();
                                                        },
                                                        pivot)
                                                        .andThen(new RunCommand(() -> drive.arcadeDrive(0.7, 0), drive)
                                                                .withTimeout(2).andThen(new InstantCommand(() -> drive
                                                                        .setDriveType(NeutralMode.Coast))))))));

        // this.robotContainer = robotContainer;
        // timer = Timer.getFPGATimestamp();

        // Commands.runOnce(
        // () -> {
        // robotContainer.pivot.setGoal(PivotConstants.middleNode);
        // robotContainer.pivot.enable();
        // },
        // robotContainer.pivot).alongWith(
        // new SequentialCommandGroup(new WaitCommand(.6),
        // Commands.runOnce(
        // () -> {
        // robotContainer.extend.setGoal(ExtendConstants.out);
        // robotContainer.extend.enable();
        // },
        // robotContainer.extend)));
        // if(timer > 3 && timer < 5){
        // new InstantCommand(() ->
        // robotContainer.intake.setSpeed(IntakeConstants.IN_SPEED));
        // } else {
        // new InstantCommand(() ->
        // robotContainer.intake.setSpeed(IntakeConstants.STOP_SPEED));
        // Commands.runOnce(
        // () -> {
        // robotContainer.extend.setGoal(ExtendConstants.in);
        // robotContainer.extend.enable();
        // },
        // robotContainer.extend).alongWith(
        // new SequentialCommandGroup(new WaitCommand(0.6),
        // Commands.runOnce(
        // () -> {
        // robotContainer.pivot.setGoal(PivotConstants.lowNode);
        // robotContainer.pivot.enable();
        // },
        // robotContainer.pivot)));
        // if(timer > 7 && timer < 9){
        // robotContainer.drive.arcadeDrive(.5, 0);
        // } else if(timer > 10){
        // robotContainer.drive.setDriveType(NeutralMode.Coast);
        // robotContainer.drive.arcadeDrive(0, 0);
        // } else{
        // robotContainer.drive.arcadeDrive(0, 0);
        // }
        // }

    }
}
