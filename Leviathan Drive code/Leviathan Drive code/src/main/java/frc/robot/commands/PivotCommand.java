package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSubsystem;

public class PivotCommand extends CommandBase {
    private PivotSubsystem pivot;
    private double pivotGoal;

    public PivotCommand(PivotSubsystem pivot, double pivotGoal){
        this.pivot = pivot;
        this.pivotGoal = pivotGoal;
        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        pivot.setGoal(pivotGoal);
        pivot.enable();
    }
    
}
