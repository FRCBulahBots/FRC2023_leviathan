package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSubsystem;
import frc.robot.subsystems.ExtendSubsystem;

public class ArmSetToPosition extends CommandBase {
    private ExtendSubsystem extend;
    private PivotSubsystem pivot;
    private double goal;

    public ArmSetToPosition(ExtendSubsystem extend, PivotSubsystem pivot, double setGoal){
        this.extend = extend;
        this.pivot = pivot;
        this.goal = goal;
    }


    @Override
    public void initialize() {
        pivot.setGoal(goal);
        pivot.enable();
    }
    
}
