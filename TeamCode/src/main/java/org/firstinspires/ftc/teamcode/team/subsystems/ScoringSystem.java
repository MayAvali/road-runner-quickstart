package org.firstinspires.ftc.teamcode.team.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;


public class ScoringSystem {
    private final DcMotor intake;
    private final DcMotor launcher;
    private final VoltageSensor voltageSensor;


    double LaunchMult = 0.925;

    public ScoringSystem(DcMotor intake, DcMotor launcher, VoltageSensor voltageSensor) {
        this.intake = intake;
        //intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.launcher = launcher;
        //launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.voltageSensor = voltageSensor;
    }
    public void launcherToggle(){
        if(launcher.getPower() != 0)
            launcher.setPower(0);
        else{
            launcher.setPower(LaunchMult);
        }
    }
    public void launcherOn(){
        launcher.setPower(LaunchMult);
    }
    public void launcherOff(){
        launcher.setPower(0);
    }
    public void launcherUpdate(){
        launcher.setPower(LaunchMult*(12/(voltageSensor.getVoltage())));
    }
    public void launchAccel(){
        LaunchMult += 0.02;
    }
    public void launchDecel(){
        LaunchMult -= 0.02;
    }
    public void intake(double out, double in){
        intake.setPower((0.4*out)-(0.6*in)*(12/(voltageSensor.getVoltage())));
    }

    public String getIntakePower() {
        return String.valueOf(intake.getPower());
    }
    public String getLauncherPower() {
        return String.valueOf(launcher.getPower());
    }
}
