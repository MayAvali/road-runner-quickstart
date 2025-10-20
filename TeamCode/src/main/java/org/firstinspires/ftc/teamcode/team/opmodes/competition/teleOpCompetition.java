package org.firstinspires.ftc.teamcode.team.opmodes.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.team.libraries.gamepadButton;
import org.firstinspires.ftc.teamcode.team.subsystems.mecanumDrive;
import org.firstinspires.ftc.teamcode.team.subsystems.scoringSystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOpDrivetrain", group = "Linear OpMode")
public class teleOpCompetition extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        gamepadButton launchButton = new gamepadButton(gamepad1, gamepadButton.gamepadKeys.RIGHT_BUMPER);

        waitForStart();
        if (isStopRequested()) return;

        mecanumDrive drivetrain = new mecanumDrive(
                hardwareMap.dcMotor.get("leftFront"),
                hardwareMap.dcMotor.get("leftBack"),
                hardwareMap.dcMotor.get("rightFront"),
                hardwareMap.dcMotor.get("rightBack"),
                hardwareMap.get(IMU.class, "imu")
        );

        while (opModeIsActive())
        {
            scoringSystem.launcherOn();

            drivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger);

            scoringSystem.intake(gamepad1.right_trigger, gamepad1.left_trigger);

            if (launchButton.isPressed()) {
                scoringSystem.score();
            }

            telemetry.addData("RightBumper", gamepad1.right_bumper);

            telemetry.addData("Front Left Motor Power " , drivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power " , drivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power " , drivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power " , drivetrain.getBackRightPower());

            telemetry.addData("Left Stick X " , gamepad1.left_stick_x);
            telemetry.addData("Left Stick Y " , gamepad1.left_stick_y);
            telemetry.addData("Right Stick X " , gamepad1.right_stick_x);
            telemetry.addData("Right Trigger " , gamepad1.right_trigger);
            telemetry.update();
        }
    }
}