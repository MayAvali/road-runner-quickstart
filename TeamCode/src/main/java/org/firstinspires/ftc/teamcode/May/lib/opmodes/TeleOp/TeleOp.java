package org.firstinspires.ftc.teamcode.May.lib.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.May.lib.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.ManipulationSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.GripperSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "CompetitionTeleOp", group = "Linear OpMode")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumSubsystem objDrivetrain = new MecanumSubsystem(
                hardwareMap.dcMotor.get("leftFront"),
                hardwareMap.dcMotor.get("leftBack"),
                hardwareMap.dcMotor.get("rightFront"),
                hardwareMap.dcMotor.get("rightBack"),
                hardwareMap.get(IMU.class, "imu")
        );

        SlidesSubsystem objSlides = new SlidesSubsystem(
                hardwareMap.dcMotor.get("leftSlidesMotor"),
                hardwareMap.dcMotor.get("leftRotatorMotor"),
                hardwareMap.dcMotor.get("rightSlidesMotor"),
                hardwareMap.dcMotor.get("rightRotatorMotor")
        );

        GripperSubsystem objGrippers = new GripperSubsystem(
                hardwareMap.servo.get("leftGripperServo"),
                hardwareMap.servo.get("leftRotatorServo"),
                hardwareMap.servo.get("rightGripperServo"),
                hardwareMap.servo.get("rightRotatorServo")
        );

        ManipulationSubsystem objManipulationSub = new ManipulationSubsystem(objSlides, objGrippers);

        GamepadButton resetIMU = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.START);

        GamepadButton AscendStageButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.DPAD_UP);
        GamepadButton SpeciModeButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.A);
        GamepadButton SpeciClampButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.B);
        //GamepadButton SampleModeButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.X);
        //GamepadButton SampleClampButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.Y);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (resetIMU.isPressed()) {
                objDrivetrain.resetIMU();
            }
            objDrivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger);


            if (AscendStageButton.isPressed()) {
                objSlides.slidesClimbStages();
            }
            if (SpeciModeButton.isPressed()) {
                objManipulationSub.toggleSpeciSlideRotPosition();
            }
            if (SpeciClampButton.isPressed()) {
                objGrippers.leftGripperClamp();
            }
//            if (SampleModeButton.isPressed()) {
//                objManipulationSub.toggleSubmersibleMode();
//            }
//            if (SampleClampButton.isPressed()) {
//                objGrippers.rightGripperClampCall();
//            }

            telemetry.addData("Front Left Motor Power " , objDrivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power " , objDrivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power " , objDrivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power " , objDrivetrain.getBackRightPower());

            telemetry.addData("Slide L Height ", objSlides.getSlideLPosition());
            telemetry.addData("Slide L Target ", objSlides.getSlideLTargetPosition());
            telemetry.addData("Slide L Power", objSlides.getSlideLPower());

            telemetry.addData("SlideRotator L Position ", objSlides.getRotatorLPosition());
            telemetry.addData("SlideRotator L Target ", objSlides.getRotatorLTargetPosition());
            telemetry.addData("SlideRotator L Power", objSlides.getRotatorLPower());

            telemetry.addData("Slide R Height ", objSlides.getSlideRPosition());
            telemetry.addData("Slide R Target ", objSlides.getSlideRTargetPosition());
            telemetry.addData("Slide R Power ", objSlides.getSlideRPower());

            telemetry.addData("SlideRotator R Position ", objSlides.getRotatorRPosition());
            telemetry.addData("SlideRotator R Target ", objSlides.getRotatorRTargetPosition());
            telemetry.addData("SlideRotator R Power ", objSlides.getRotatorRPower());


            telemetry.addData("Gripper L Position", objGrippers.getLeftGripperPosition());
            telemetry.addData("GripRotator L Position", objGrippers.getLeftRotatorPosition());

            telemetry.addData("Gripper R Position", objGrippers.getRightGripperPosition());
            telemetry.addData("GripRotator R Position", objGrippers.getRightRotatorPosition());

            telemetry.update();
        }
    }
}