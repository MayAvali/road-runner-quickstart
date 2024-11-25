package org.firstinspires.ftc.teamcode.May.lib.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.May.lib.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.ManipulationSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.GripperSubsystem;

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

        SlidesSubsystem slide = new SlidesSubsystem(
                hardwareMap.dcMotor.get("leftSlidesMotor"),
                hardwareMap.dcMotor.get("leftRotatorMotor"),
                hardwareMap.dcMotor.get("rightSlidesMotor"),
                hardwareMap.dcMotor.get("rightRotatorMotor")
        );

        GripperSubsystem grippers = new GripperSubsystem(
                hardwareMap.servo.get("leftGripperServo"),
                hardwareMap.servo.get("leftRotatorServo"),
                hardwareMap.servo.get("rightGripperServo"),
                hardwareMap.servo.get("rightRotatorServo")
        );

        ManipulationSubsystem manipulation = new ManipulationSubsystem(slide, grippers);

        GamepadButton resetIMU = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.START);

//        GamepadButton ManualSlideButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.X);
//        GamepadButton SliderModeButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.Y);
//        GamepadButton DispenseSpecimenButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.RIGHT_BUMPER);
//        GamepadButton DeclineButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.LEFT_BUMPER);
//
//        GamepadButton ManualGripperClampButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.A);
//        GamepadButton ManualGripperRotateButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.B);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (resetIMU.isPressed()) {
                drivetrain.resetIMU();
            }
//            if (ManualSlideButton.isPressed()) {
//                slide.togglePos();
//            }
//            if (SliderModeButton.isPressed()) {
//                slide.toggleMode();
//                gripper.toggleMode();
//            }
//            if (DispenseSpecimenButton.isPressed()) {
//                slide.dispenseSpecimen();
//            }
//            if (DeclineButton.isPressed()) {
//                slide.decline();
//            }
//            if (ManualGripperClampButton.isPressed()) {
//                gripper.toggleClamp();
//            }
//            if (ManualGripperRotateButton.isPressed()) {
//                gripper.toggleRotate();
//            }
            drivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger);

            telemetry.addData("Front Left Motor Power " , drivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power " , drivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power " , drivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power " , drivetrain.getBackRightPower());


            telemetry.addData("Slide L Height ", slide.getSlideLPosition());
            telemetry.addData("Slide L Target ", slide.getSlideLTargetPosition());

            telemetry.addData(" SlideRotator L Position ", slide.getRotatorLPosition());
            telemetry.addData("SlideRotator L Target ", slide.getRotatorLTargetPosition());

            telemetry.addData("Slide R Height ", slide.getSlideRPosition());
            telemetry.addData("Slide R Target ", slide.getSlideRTargetPosition());

            telemetry.addData("SlideRotator R Position ", slide.getRotatorRPosition());
            telemetry.addData("SlideRotator R Target ", slide.getRotatorRTargetPosition());


            telemetry.addData("Gripper L Position", grippers.getLeftGripperPosition());
            telemetry.addData("GripRotator L Position", grippers.getLeftRotatorPosition());

            telemetry.addData("Gripper R Position", grippers.getRightGripperPosition());
            telemetry.addData("GripRotator R Position", grippers.getRightRotatorPosition());

            telemetry.update();

        }
    }
}