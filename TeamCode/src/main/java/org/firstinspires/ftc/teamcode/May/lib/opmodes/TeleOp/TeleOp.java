package org.firstinspires.ftc.teamcode.May.lib.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.May.lib.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.PrimarySlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.PrimaryGripperSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.SubmersibleGripperSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.SubmersibleSlidesSubsystem;

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

        PrimarySlidesSubsystem objPrimarySlides = new PrimarySlidesSubsystem(
                hardwareMap.dcMotor.get("primarySlidesMotor")
        );

        PrimaryGripperSubsystem objPrimaryGrippers = new PrimaryGripperSubsystem(
                hardwareMap.servo.get("primaryGripperServo"),
                hardwareMap.servo.get("primaryRotatorServo")
        );

        SubmersibleSlidesSubsystem objSubmersibleSlides = new SubmersibleSlidesSubsystem(
                hardwareMap.dcMotor.get("submersibleSlidesMotor")
        );

        SubmersibleGripperSubsystem objSubmersibleGrippers = new SubmersibleGripperSubsystem(
                hardwareMap.servo.get("submersibleGripperServo"),
                hardwareMap.servo.get("submersibleRotatorServo")
        );
        

        GamepadButton resetIMU = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.START);

        GamepadButton primarySlideButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.X);
        GamepadButton primarySliderModeButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.Y);
        GamepadButton dispenseSpecimenButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.RIGHT_BUMPER);
        GamepadButton declineButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.LEFT_BUMPER);

        GamepadButton primaryClampButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.A);
        GamepadButton primaryRotateButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.B);
        
        GamepadButton SubmersibleSlideButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.DPAD_UP);
        GamepadButton SubmersibleGripperClampButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.DPAD_LEFT);
        GamepadButton SubmersibleGripperRotateButton = new GamepadButton(gamepad1, GamepadButton.GamepadKeys.DPAD_DOWN);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (resetIMU.isPressed()) {
                objDrivetrain.resetIMU();
            }
            if (primarySlideButton.isPressed()) {
                objPrimarySlides.togglePos();
            }
            if (primarySliderModeButton.isPressed()) {
                objPrimarySlides.toggleMode();
                objPrimaryGrippers.toggleMode();
            }
            if (dispenseSpecimenButton.isPressed()) {
                objPrimarySlides.dispenseSpecimen();
            }
            if (declineButton.isPressed()) {
                objPrimarySlides.decline();
            }
            if (primaryClampButton.isPressed()) {
                objPrimaryGrippers.toggleClamp();
            }
            if (primaryRotateButton.isPressed()) {
                objPrimaryGrippers.toggleRotate();
            }
            if (SubmersibleSlideButton.isPressed()) {
                objSubmersibleSlides.togglePos();
            }
            if (SubmersibleGripperClampButton.isPressed()) {
                objSubmersibleGrippers.toggleClamp();
            }
            if (SubmersibleGripperRotateButton.isPressed()) {
                objSubmersibleGrippers.toggleRotate();
            }
            objDrivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger);

            telemetry.addData("Front Left Motor Power " , objDrivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power " , objDrivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power " , objDrivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power " , objDrivetrain.getBackRightPower());

            telemetry.addData("Primary Slides Position ", objPrimarySlides.getSlidePosition());
            telemetry.addData("Primary Slides Target ", objPrimarySlides.getSlideTargetPosition());
            telemetry.addData("Primary Slides Scoring Sample ", objPrimarySlides.getSlideMode());
            telemetry.addData("Primary Slides State ", objPrimarySlides.getSlideState());

            telemetry.addData("Primary Gripper Position ", objPrimaryGrippers.getGripperPosition());
            telemetry.addData("Primary Gripper Scoring Sample ", objPrimaryGrippers.getGripperMode());
            telemetry.addData("Primary Gripper Closed ", objPrimaryGrippers.getGripperState());
            telemetry.addData("Primary Gripper Down ", objPrimaryGrippers.getRotatorState());

            telemetry.addData("Submersible Slides Position ", objSubmersibleSlides.getSubSlidePosition());
            telemetry.addData("Submersible Slides Target ", objSubmersibleSlides.getSubSlideTargetPosition());

            telemetry.addData("Submersible Gripper Position ", objSubmersibleGrippers.getGripperPosition());
            telemetry.addData("Submersible Gripper Closed ", objSubmersibleGrippers.getGripperState());
            telemetry.addData("Submersible Gripper Down ", objSubmersibleGrippers.getRotatorState());

            telemetry.update();

        }
    }
}