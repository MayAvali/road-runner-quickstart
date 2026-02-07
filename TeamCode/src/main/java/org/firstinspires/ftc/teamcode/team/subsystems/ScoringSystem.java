package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.acmerobotics.dashboard.config.Config;


public class ScoringSystem {
    private final DcMotorEx launcher;
    //private final DcMotorEx intake2;
    private final DcMotorEx intake;
    //private final VoltageSensor voltageSensor;
    private final DcMotorEx turret;

    private final DcMotorEx launcher2;

    private double TurretTargetPos = 0;


    //REPLACE INTAKE PIDF SOON.

    @Config
    public static class intakePIDF {
        public static double P = 16;
        public static double I = 0;
        public static double D = 0;
        public static double F = 12;
    }
    @Config
    public static class launcherPIDF {
        public static double P = 135;
        public static double I = 8;
        public static double D = 0;
        public static double F = 18;
    }
    public static class turretPIDF {
        public static double P = 12;

        public static double I = 5;
        public static double D = 1;
        public static double F = 0;
    }
    public static launcherPIDF LauncherPIDF = new launcherPIDF();
    public static intakePIDF IntakePIDF = new intakePIDF();

    public static turretPIDF TurretPIDF = new turretPIDF();


    public double LaunchVel = 1300;
    public double LaunchMult = 0.88;

    public ScoringSystem(DcMotorEx launcher, DcMotorEx intake, DcMotorEx turret, DcMotorEx launcher2) {
        this.launcher = launcher;
        this.launcher2 = launcher2;
        launcher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcher2.setDirection(DcMotorSimple.Direction.REVERSE);
        launcher.setVelocityPIDFCoefficients(launcherPIDF.P, launcherPIDF.I, launcherPIDF.D, launcherPIDF.F);
        //this.voltageSensor = voltageSensor;

        this.intake = intake;

        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setVelocityPIDFCoefficients(intakePIDF.P, intakePIDF.I, intakePIDF.D, intakePIDF.F);

//        this.intake2 = intake2;
//        intake2.setDirection(DcMotorSimple.Direction.REVERSE);
//        intake2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        this.turret = turret;
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setTargetPosition(0);
        turret.setTargetPositionTolerance(1);
        turret.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setPower(1);
        turret.setPositionPIDFCoefficients(10);
        turret.setVelocityPIDFCoefficients(turretPIDF.P,turretPIDF.I,turretPIDF.D,turretPIDF.F);
    }


    public void launcherToggle(){
        if(launcher.getPower() != 0) {
            launcher.setVelocity(0);
            launcher2.setPower(launcher.getPower());
            //launcher.setPower(0);
        }else{
            launcher.setVelocity(LaunchVel);
            launcher2.setPower(launcher.getPower());
            //launcher.setPower(LaunchMult*(12/(voltageSensor.getVoltage())));
        }
    }
    public void launcherOn(){
        launcher.setVelocity(LaunchVel);
        launcher2.setPower(launcher.getPower());
        //launcher.setPower(LaunchMult*(12/(voltageSensor.getVoltage())))
    }
    public void launcherOff(){
        launcher.setVelocity(0);
        launcher2.setPower(launcher.getPower());
    }
    public void launcherUpdate(){
        launcher.setVelocity(LaunchVel);
        launcher.setVelocityPIDFCoefficients(launcherPIDF.P, launcherPIDF.I, launcherPIDF.D, launcherPIDF.F);
        launcher2.setPower(launcher.getPower());
        //launcher.setPower(LaunchMult*(12/(voltageSensor.getVoltage())));
    }
    public static double TurretDistToFlywheelVelocity (double distance) {
        return -0.000053592*Math.pow(distance, 2) + 0.569318*distance + 623.58077;
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
        intake.setVelocity((2800*out)-(3600*in));
        //intake2.setVelocity((1200*out)-(1600*in));
    }

    public void setTurretTarget(double inputDegrees, double totalTicks) {
        double ticksPerDegree = totalTicks / 360.0;
        double targetTicks = inputDegrees * ticksPerDegree;
        double limit = 0.25 * totalTicks;
        double clampedPosition = Math.max(-limit, Math.min(targetTicks, limit));
        turret.setTargetPosition((int) clampedPosition);
    }


    public double getTurretPos() {
        return turret.getCurrentPosition();
    }
    public double getTurretTargetPos(){
        return turret.getTargetPosition();
    }
    public double getTurretPower(){
        return turret.getPower();
    }
    public double getIntakeVel() {
        return intake.getVelocity();
    }
    public double getLauncherVel() {
        return launcher.getVelocity();
    }
}
