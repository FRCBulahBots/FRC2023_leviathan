// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.ExtendSubsystem;

// public class ResetExtend extends CommandBase{
//     private ExtendSubsystem extend;
//     public ResetExtend(ExtendSubsystem extend){
//         this.extend = extend;
//         addRequirements(extend);
//     }

//     @Override
//     public void execute() {
//         if(!extend.getLimitSwitch()){
//             System.out.println("WOAH");
//             return;
//         }
//         // extend.setExtendSpeed(0);
//     }

//     @Override
//     public void end(boolean interrupted) {
//         extend.setExtendEncoder(0);
//         extend.setExtendSpeed(0);
//         extend.enable();
//     }

//     @Override
//     public boolean isFinished() {
//     return extend.getLimitSwitch();
//     }
    
// }
