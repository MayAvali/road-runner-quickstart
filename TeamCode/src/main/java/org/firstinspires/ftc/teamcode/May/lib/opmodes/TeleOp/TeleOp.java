package org.firstinspires.ftc.teamcode.May.lib.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.May.lib.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.LeftSlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.LeftGripperSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "CompetitionTeleOp", group = "Linear OpMode")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumSubsystem drivetrain = new MecanumSubsystem(
                hardwareMap.dcMotor.get("leftFront"),
                hardwareMap.dcMotor.get("leftBack"),
                hardwareMap.dcMotor.get("rightFront"),
                hardwareMap.dcMotor.get("rightBack"),
                hardwareMap.get(IMU.class, "imu")
        );
        LeftSlidesSubsystem slide = new LeftSlidesSubsystem(
                hardwareMap.dcMotor.get("slidesMotorRight")
                //hardwareMap.dcMotor.get("slidesMotorLeft")
        );
        LeftGripperSubsystem gripper = new LeftGripperSubsystem(
                hardwareMap.servo.get("gripperServo"),
                hardwareMap.servo.get("rotatorServo")
        );

        GamepadButton resetIMU = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.START);

        GamepadButton ManualSlideButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.X);
        GamepadButton SliderModeButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.Y);
        GamepadButton DispenseSpecimenButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.RIGHT_BUMPER);
        GamepadButton DeclineButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.LEFT_BUMPER);

        GamepadButton ManualGripperClampButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.A);
        GamepadButton ManualGripperRotateButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.B);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (resetIMU.isPressed()) {
                drivetrain.resetIMU();
            }

            if (ManualSlideButton.isPressed()) {
                slide.togglePos();
            }
            if (SliderModeButton.isPressed()) {
                slide.toggleMode();
                gripper.toggleMode();
            }
            if (DispenseSpecimenButton.isPressed()) {
                slide.dispenseSpecimen();
            }
            if (DeclineButton.isPressed()) {
                slide.decline();
            }
            if (ManualGripperClampButton.isPressed()) {
                gripper.toggleClamp();
            }
            if (ManualGripperRotateButton.isPressed()) {
                gripper.toggleRotate();
            }
            drivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger);

            telemetry.addData("Front Left Motor Power " , drivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power " , drivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power " , drivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power " , drivetrain.getBackRightPower());

            telemetry.addData("Slides Position ", slide.getSlidePosition());
            telemetry.addData("Slides Target ", slide.getSlideTargetPosition());
            telemetry.addData("Slides Scoring Sample ", slide.getSlideMode());
            telemetry.addData("Slides State ", slide.getSlideState());

            telemetry.addData("Gripper Position ", gripper.getGripperPosition());
            telemetry.addData("Gripper Scoring Sample ", gripper.getGripperMode());
            telemetry.addData("Gripper Closed ", gripper.getGripperState());
            telemetry.addData("Gripper Down ", gripper.getRotatorState());

            telemetry.update();

        }
    }
}