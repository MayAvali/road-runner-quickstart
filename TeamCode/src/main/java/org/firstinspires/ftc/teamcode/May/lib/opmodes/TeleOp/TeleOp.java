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
                hardwareMap.dcMotor.get("submersibleSlideMotor")
        );

        SubmersibleGripperSubsystem objSubmersibleGrippers = new SubmersibleGripperSubsystem(
                hardwareMap.servo.get("submersibleGripperServo"),
                hardwareMap.servo.get("submersibleRotatorServo")
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
                objDrivetrain.resetIMU();
            }

            if (ManualSlideButton.isPressed()) {
                objPrimarySlides.togglePos();
            }
            if (SliderModeButton.isPressed()) {
                objPrimarySlides.toggleMode();
                objPrimaryGrippers.toggleMode();
            }
            if (DispenseSpecimenButton.isPressed()) {
                objPrimarySlides.dispenseSpecimen();
            }
            if (DeclineButton.isPressed()) {
                objPrimarySlides.decline();
            }
            if (ManualGripperClampButton.isPressed()) {
                objPrimaryGrippers.toggleClamp();
            }
            if (ManualGripperRotateButton.isPressed()) {
                objPrimaryGrippers.toggleRotate();
            }
            objDrivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger);

            telemetry.addData("Front Left Motor Power " , objDrivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power " , objDrivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power " , objDrivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power " , objDrivetrain.getBackRightPower());

            telemetry.addData("Slides Position ", objPrimarySlides.getSlidePosition());
            telemetry.addData("Slides Target ", objPrimarySlides.getSlideTargetPosition());
            telemetry.addData("Slides Scoring Sample ", objPrimarySlides.getSlideMode());
            telemetry.addData("Slides State ", objPrimarySlides.getSlideState());

            telemetry.addData("Gripper Position ", objPrimaryGrippers.getGripperPosition());
            telemetry.addData("Gripper Scoring Sample ", objPrimaryGrippers.getGripperMode());
            telemetry.addData("Gripper Closed ", objPrimaryGrippers.getGripperState());
            telemetry.addData("Gripper Down ", objPrimaryGrippers.getRotatorState());

            telemetry.update();

        }
    }
}