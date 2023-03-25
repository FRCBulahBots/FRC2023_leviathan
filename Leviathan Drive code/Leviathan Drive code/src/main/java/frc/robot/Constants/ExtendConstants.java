package frc.robot.Constants;

public final class ExtendConstants {
    // #region Trapezoidal Profile Constraints
    public static int MAX_VEL = 40;
    public static int MAX_ACCEL = 15;
    // #endregion

    // #region PID Values
    public static double PID_P = 0.16f;
    public static double PID_I = 0.025f;
    public static double PID_D = 0.0f;
    // #endregion

    // #region Positions
    public static double countsPerInch = 1.77;
    public static double in = 15 * countsPerInch;
    public static double out = 0 * countsPerInch;
    // #endregion
}
