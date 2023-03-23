package frc.robot.Constants;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;

  public final class ConstantsJoystickInputs {
    public static Button MoveArmTopRow = XboxController.Button.kY;
    public static Button MoveArmMiddleRow = XboxController.Button.kX;
    public static Button MoveArmBottomRow = XboxController.Button.kA;
    
    public static Button MoveToGroundPickupPosition = XboxController.Button.kB;
    public static Button RetractArm = XboxController.Button.kLeftBumper;
    public static Button ExtendArm = XboxController.Button.kRightBumper;
  }

