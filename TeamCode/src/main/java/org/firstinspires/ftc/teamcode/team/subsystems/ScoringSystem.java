package org.firstinspires.ftc.teamcode.team.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.acmerobotics.dashboard.config.Config;


public class ScoringSystem {
    private final DcMotorEx intake;
    private final DcMotorEx launcher;
    private final VoltageSensor voltageSensor;


    //REPLACE INTAKE PIDF SOON.

    //it is currently designed around the encoder NOT being plugged in which defeats the purpose. Once that is fixed it needs to be re-adjusted ASAP.
    public static class intakePIDF {
        public double P = 250;
        public double I = 0;
        public double D = 16;
        public double F = 0;
    }
    public static class launcherPIDF {
        public double P = 150;
        public double I = 5;
        public double D = 10;
        public double F = 0;
    }
    public static intakePIDF IntakePIDF = new intakePIDF();
    public static launcherPIDF LauncherPIDF = new launcherPIDF();


    public double LaunchVel = 2100;
    public double LaunchMult = 0.88;

    public ScoringSystem(DcMotorEx intake, DcMotorEx launcher, VoltageSensor voltageSensor) {
        this.intake = intake;
        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake.setVelocityPIDFCoefficients(IntakePIDF.P, IntakePIDF.I, IntakePIDF.D, IntakePIDF.F);

        this.launcher = launcher;
        launcher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcher.setVelocityPIDFCoefficients(LauncherPIDF.P, LauncherPIDF.I, LauncherPIDF.D, LauncherPIDF.F);
        this.voltageSensor = voltageSensor;
    }
    public void launcherToggle(){
        if(launcher.getPower() != 0)
            launcher.setVelocity(0);
            //launcher.setPower(0);
        else{
            launcher.setVelocity(LaunchVel);
            //launcher.setPower(LaunchMult*(12/(voltageSensor.getVoltage())));
        }
    }
    public void launcherOn(){
        launcher.setVelocity(LaunchVel);
        //launcher.setPower(LaunchMult*(12/(voltageSensor.getVoltage())));
    }
    public void launcherOff(){
        launcher.setVelocity(0);
    }
    public void launcherUpdate(){
        launcher.setVelocity(LaunchVel);
        //launcher.setPower(LaunchMult*(12/(voltageSensor.getVoltage())));
    }
    public void launchAccel(){
        LaunchVel += 25;
        //LaunchMult += 0.01;

    }
    public void launchDecel(){
        LaunchVel -= 25;
        //LaunchMult -= 0.01;
    }
    public void intake(double out, double in){
        intake.setVelocity((90*out)-(110*in));
    }

    public double getIntakeVel() {
        return intake.getVelocity();
    }
    public double getLauncherVel() {
        return launcher.getVelocity();
    }
}
