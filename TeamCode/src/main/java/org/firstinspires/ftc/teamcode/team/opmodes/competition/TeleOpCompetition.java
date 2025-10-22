package org.firstinspires.ftc.teamcode.team.opmodes.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.team.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.team.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.team.subsystems.ScoringSystem;
import org.firstinspires.ftc.teamcode.team.subsystems.ServoGate;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOpDrivetrain", group = "Linear OpMode")
public class TeleOpCompetition extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
        if (isStopRequested()) return;

        MecanumDrive drivetrain = new MecanumDrive(
                hardwareMap.dcMotor.get("leftFront"),
                hardwareMap.dcMotor.get("leftBack"),
                hardwareMap.dcMotor.get("rightFront"),
                hardwareMap.dcMotor.get("rightBack"),
                hardwareMap.get(IMU.class, "imu")
        );

        ScoringSystem ScoringSystem = new ScoringSystem(
                hardwareMap.dcMotor.get("intake"),
                hardwareMap.dcMotor.get("launcher")
        );

        ServoGate ServoGate = new ServoGate(
                hardwareMap.servo.get("leftGate"),
                hardwareMap.servo.get("rightGate")
        );

        GamepadButton gateButton = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.LEFT_BUMPER);
        GamepadButton launcherToggle = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.RIGHT_BUMPER);
        GamepadButton launcherAccel = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_UP);
        GamepadButton launcherDecel = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_DOWN);

        while (opModeIsActive())
        {

            drivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 1);

            ScoringSystem.intake(gamepad1.left_trigger, gamepad1.right_trigger);

            if (gateButton.isPressed()) {
                ServoGate.toggleGate();
            }
            if (launcherToggle.isPressed()) {
                ScoringSystem.launcherToggle();
            }
            if (launcherAccel.isPressed()) {
                ScoringSystem.launchAccel();
            }
            if (launcherDecel.isPressed()) {
                ScoringSystem.launchDecel();
            }

            telemetry.addData("RightBumper", gamepad1.right_bumper);

            telemetry.addData("Front Left Motor Power " , drivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power " , drivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power " , drivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power " , drivetrain.getBackRightPower());

            telemetry.addData("Intake Motor Power " , ScoringSystem.getIntakePower());
            telemetry.addData("Launcher Motor Power " , ScoringSystem.getLauncherPower());

            telemetry.addData("Left Stick X " , gamepad1.left_stick_x);
            telemetry.addData("Left Stick Y " , gamepad1.left_stick_y);
            telemetry.addData("Right Stick X " , gamepad1.right_stick_x);
            telemetry.addData("Left Trigger " , gamepad1.left_trigger);
            telemetry.addData("Right Trigger " , gamepad1.right_trigger);
            telemetry.update();
        }
    }
}