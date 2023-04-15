package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExtendSubsystem;

public class ExtendCommand extends CommandBase {
    private ExtendSubsystem extend;
    private double extendGoal;

    public ExtendCommand(ExtendSubsystem extend, double extendGoal){
        this.extend = extend;
        this.extendGoal = extendGoal;
        addRequirements(extend);
    }

    @Override
    public void initialize() {
        extend.setGoal(extendGoal);
        extend.enable();
    }
    
}
