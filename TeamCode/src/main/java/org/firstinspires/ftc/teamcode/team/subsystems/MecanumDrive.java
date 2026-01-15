package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

public class MecanumDrive {
    public final DcMotor leftFront;
    public final DcMotor leftBack;
    public final DcMotor rightFront;
    public final DcMotor rightBack;
    //DcMotor.ZeroPowerBehavior zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
    public final IMU imu;
    public MecanumDrive(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront, DcMotor rightBack, IMU imu) {
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        this.imu = imu;
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB
        // forward
        imu.initialize(parameters);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void resetIMU() {
        imu.resetYaw();
    }

    public void botOrientedDrive(double y, double x, double rx, double sp) {
        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rx) + Math.abs(x) + Math.abs(y), 1);
        double frontLeftPower = ((-rx - x - y) / denominator) * (1 - (0.6 * sp));
        double backLeftPower = ((-rx + x - y) / denominator) * (1 - (0.6 * sp));
        double frontRightPower = ((-rx - x + y) / denominator) * (1 - (0.6 * sp));
        double backRightPower = ((-rx + x + y) / denominator) * (1 - (0.6 * sp));

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);
    }

    public void zeroPowerBrake() {
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void zeroPowerFloat() {
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public double getFrontLeftPower() {
        return leftFront.getPower();
    }
    public double getBackLeftPower() {
        return leftBack.getPower();
    }
    public double getFrontRightPower() {
        return rightFront.getPower();
    }
    public double getBackRightPower() {
        return rightBack.getPower();
    }

}
