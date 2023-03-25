package frc.robot.Constants;

public final class GrabberConstants {

    //#region Trapezoidal Profile Constraints
    public static int MAX_VEL = 1000;
    public static int MAX_ACCEL = 400;
    //#endregion

    //#region PID Values
    public static double PID_P = 0.002f;
    public static double PID_I = 0.003f;
    public static double PID_D = 0.0f;
    //#endregion

    //#region Positions
    public static int fullCrushCone = -880;
    public static int prepGrabCone = -1190;
    public static int fullCrushCube = -1200;
    public static int prepGrabCube = -1960;
    //#endregion
}