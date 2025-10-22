package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class ScoringSystem {

    public DcMotor intake;
    public DcMotor launcher;

    double LaunchMult = 0.9;

    public ScoringSystem(DcMotor intake, DcMotor launcher) {
        this.intake = intake;
        //intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.launcher = launcher;
        //launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void launcherToggle(){
        if(launcher.getPower() != 0)
            launcher.setPower(0);
        else{
            launcher.setPower(LaunchMult);
        }
    }
    public void launchAccel(){
        LaunchMult += 0.02;
    }
    public void launchDecel(){
        LaunchMult -= 0.02;
    }
    public void intake(double in, double out){
        intake.setPower((0.5*in)-(0.4*out));
    }

    public String getIntakePower() {
        return String.valueOf(intake.getPower());
    }
    public String getLauncherPower() {
        return String.valueOf(launcher.getPower());
    }
}
