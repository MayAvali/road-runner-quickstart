package org.firstinspires.ftc.teamcode.May.lib.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.May.lib.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.PrimarySlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.PrimaryGripperSubsystem;

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

        PrimarySlidesSubsystem objSlides = new PrimarySlidesSubsystem(
                hardwareMap.dcMotor.get("primarySlidesMotor")
        );

        PrimaryGripperSubsystem objGrippers = new PrimaryGripperSubsystem(
                hardwareMap.servo.get("primaryGripperServo"),
                hardwareMap.servo.get("primaryRotatorServo")
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
                objSlides.togglePos();
            }
            if (SliderModeButton.isPressed()) {
                objSlides.toggleMode();
                objGrippers.toggleMode();
            }
            if (DispenseSpecimenButton.isPressed()) {
                objSlides.dispenseSpecimen();
            }
            if (DeclineButton.isPressed()) {
                objSlides.decline();
            }
            if (ManualGripperClampButton.isPressed()) {
                objGrippers.toggleClamp();
            }
            if (ManualGripperRotateButton.isPressed()) {
                objGrippers.toggleRotate();
            }
            objDrivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_trigger);

            telemetry.addData("Front Left Motor Power " , objDrivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power " , objDrivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power " , objDrivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power " , objDrivetrain.getBackRightPower());

            telemetry.addData("Slides Position ", objSlides.getSlidePosition());
            telemetry.addData("Slides Target ", objSlides.getSlideTargetPosition());
            telemetry.addData("Slides Scoring Sample ", objSlides.getSlideMode());
            telemetry.addData("Slides State ", objSlides.getSlideState());

            telemetry.addData("Gripper Position ", objGrippers.getGripperPosition());
            telemetry.addData("Gripper Scoring Sample ", objGrippers.getGripperMode());
            telemetry.addData("Gripper Closed ", objGrippers.getGripperState());
            telemetry.addData("Gripper Down ", objGrippers.getRotatorState());

            telemetry.update();

        }
    }
}