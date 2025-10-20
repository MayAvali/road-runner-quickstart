package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ScoringSystem {

    public static DcMotor intake;
    public static DcMotor launcher;

    static ElapsedTime launchTimer = new ElapsedTime();

    public ScoringSystem(DcMotor intake, DcMotor launcher) {
        ScoringSystem.intake = intake;
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ScoringSystem.launcher = launcher;
        launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public static void launcherOn(){
        launcher.setPower(1);
    }
    public static void launcherOff(){
        launcher.setPower(0);
    }
    public static void intake(double in, double out){
        intake.setPower(in-out);
    }
    public static void score() throws InterruptedException {

        launchTimer.reset();

        ServoGate.toggleGate();

        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake.setTargetPosition(1000);

        while (launchTimer.time() < 2000) {
            intake.setPower(1);
        }

        intake.setPower(0);

        ServoGate.toggleGate();
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}
