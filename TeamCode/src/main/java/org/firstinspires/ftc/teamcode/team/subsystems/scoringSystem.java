package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class scoringSystem {

    public static DcMotor intake;
    public static DcMotor launcher;
    public scoringSystem(DcMotor intake, DcMotor launcher) {
        scoringSystem.intake = intake;
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        scoringSystem.launcher = launcher;
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

        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        servoGate.toggleGate();

        intake.setTargetPosition(1000);
        intake.setPower(1);
        Thread.sleep(2000);

        servoGate.toggleGate();
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
