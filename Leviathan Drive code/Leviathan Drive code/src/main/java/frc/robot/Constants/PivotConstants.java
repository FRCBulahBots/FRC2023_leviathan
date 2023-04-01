package frc.robot.Constants;

public final class PivotConstants {
    // #region Trapezoidal Profile Constraints
    public static int MAX_VEL = 120;
    public static int MAX_ACCEL = 50; //previoisly 60, but test exponential first
    // #endregion

    // #region PID Values
    public static double PID_P = 0.3f;
    public static double PID_I = 0.0f;
    public static double PID_D = 0.0f;
    // #endregion

    // #region Positions
    public static double clearBumpers = -77;
    public static int substation = -57;
    public static double middleNode = -50;
    public static int lowNode = 0;
    // #endregion
}
