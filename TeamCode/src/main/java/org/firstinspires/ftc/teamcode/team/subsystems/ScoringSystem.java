package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.acmerobotics.dashboard.config.Config;


public class ScoringSystem {
    private final DcMotorEx intake;
    //private final DcMotorEx intake2;
    private final DcMotorEx launcher;
    //private final VoltageSensor voltageSensor;


    //REPLACE INTAKE PIDF SOON.

    //it is currently designed around the encoder NOT being plugged in which defeats the purpose. Once that is fixed it needs to be re-adjusted ASAP.
    @Config
    public static class intakePIDF {
        public static double P = 0;
        public static double I = 0;
        public static double D = 4;
        public static double F = 12;
    }
    @Config
    public static class launcherPIDF {
        public static double P = 150;
        public static double I = 8;
        public static double D = 12;
        public static double F = 0;
    }
    public static intakePIDF IntakePIDF = new intakePIDF();
    public static launcherPIDF LauncherPIDF = new launcherPIDF();


    public double LaunchVel = 1800;
    public double LaunchMult = 0.88;

    public ScoringSystem(DcMotorEx intake, DcMotorEx launcher) {
        this.intake = intake;
        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake.setVelocityPIDFCoefficients(intakePIDF.P, intakePIDF.I, intakePIDF.D, intakePIDF.F);

//        this.intake2 = intake2;
//        intake2.setDirection(DcMotorSimple.Direction.REVERSE);
//        intake2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        this.launcher = launcher;
        launcher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcher.setVelocityPIDFCoefficients(launcherPIDF.P, launcherPIDF.I, launcherPIDF.D, launcherPIDF.F);
        //this.voltageSensor = voltageSensor;
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
        launcher.setVelocityPIDFCoefficients(launcherPIDF.P, launcherPIDF.I, launcherPIDF.D, launcherPIDF.F);
        //launcher.setPower(LaunchMult*(12/(voltageSensor.getVoltage())));
    }
    public void launchAccel(){
        LaunchVel += 25;
        //LaunchMult += 0.01;

    }

    public void setLaunchVel(int velocity){
        LaunchVel = velocity;
    }
    public void launchDecel(){
        LaunchVel -= 25;
        //LaunchMult -= 0.01;
    }
    public void intake(double out, double in){
        intake.setVelocityPIDFCoefficients(intakePIDF.P, intakePIDF.I, intakePIDF.D, intakePIDF.F);
        //intake2.setVelocityPIDFCoefficients(intakePIDF.P, intakePIDF.I, intakePIDF.D, intakePIDF.F);
        intake.setVelocity((2000*out)-(2800*in));
        //intake2.setVelocity((1200*out)-(1600*in));
    }

    public void intakeShiftStrong() {
        intakePIDF.I = 8;
    }
    public void intakeShiftWeak() {
        intakePIDF.I = 2;
    }


    public double getIntakeVel() {
        return intake.getVelocity();
    }
    public double getLauncherVel() {
        return launcher.getVelocity();
    }
}
