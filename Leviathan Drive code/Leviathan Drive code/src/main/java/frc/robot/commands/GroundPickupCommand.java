package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.ExtendSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.Constants.ExtendConstants;
import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class GroundPickupCommand extends SequentialCommandGroup {
    public GroundPickupCommand(IntakeSubsystem intake, ExtendSubsystem extend, PivotSubsystem pivot) {

        addCommands(
                new PivotCommand(pivot, PivotConstants.groundUp),
                new WaitCommand(1.5),
                new ParallelCommandGroup(
                new PivotCommand(pivot, PivotConstants.clearBumpers),
                new ExtendCommand(extend, ExtendConstants.ground)),
                new RunCommand(() -> intake.setSpeed(IntakeConstants.OUT_SPEED)).withTimeout(4),
                new InstantCommand(() -> intake.setSpeed(IntakeConstants.STOP_SPEED)),
                new WaitCommand(1),
                new ParallelCommandGroup(
                new PivotCommand(pivot, PivotConstants.groundUp),
                new ExtendCommand(extend, ExtendConstants.in)));
    }
}
