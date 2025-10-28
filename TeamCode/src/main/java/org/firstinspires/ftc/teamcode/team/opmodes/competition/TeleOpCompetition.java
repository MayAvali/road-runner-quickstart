package org.firstinspires.ftc.teamcode.team.opmodes.competition;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.team.opmodes.competition.TeleOpCompetition.RobotState.INTAKE;
import static org.firstinspires.ftc.teamcode.team.opmodes.competition.TeleOpCompetition.RobotState.PRESCORE;
import static org.firstinspires.ftc.teamcode.team.opmodes.competition.TeleOpCompetition.RobotState.SCORE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.team.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.team.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.team.subsystems.ScoringSystem;
import org.firstinspires.ftc.teamcode.team.subsystems.ServoGate;

@TeleOp(name = "TeleOpDrivetrain", group = "Linear OpMode")
public class TeleOpCompetition extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
        if (isStopRequested()) return;

        RobotState robotState = INTAKE;

        //ElapsedTime timer = new ElapsedTime();
        //timer.reset();

        MecanumDrive drivetrain = new MecanumDrive(
                hardwareMap.dcMotor.get("leftFront"),
                hardwareMap.dcMotor.get("leftBack"),
                hardwareMap.dcMotor.get("rightFront"),
                hardwareMap.dcMotor.get("rightBack"),
                hardwareMap.get(IMU.class, "imu")
        );

        ScoringSystem ScoringSystem = new ScoringSystem(
                (DcMotorEx) hardwareMap.dcMotor.get("intake"),
                (DcMotorEx) hardwareMap.dcMotor.get("launcher"),
                hardwareMap.voltageSensor.iterator().next()
        );

        ServoGate ServoGate = new ServoGate(
                hardwareMap.servo.get("leftGate"),
                hardwareMap.servo.get("rightGate")
        );

        ServoGate.closeGate();

        GamepadButton stateBack = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.LEFT_BUMPER);
        GamepadButton stateForward = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.RIGHT_BUMPER);
        GamepadButton launcherAccel = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_UP);
        GamepadButton launcherDecel = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_DOWN);

        while (opModeIsActive()) {
            switch (robotState) {
                case INTAKE:
                    ServoGate.closeGate();
                    drivetrain.zeroPowerFloat();

                    ScoringSystem.launcherOff();

                    drivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0);

                    ScoringSystem.intake(gamepad1.left_trigger, gamepad1.right_trigger);

                    if (stateForward.isPressed()) {
                        robotState = PRESCORE;
                    }


                    break;

                case PRESCORE:
                    ServoGate.closeGate();
                    drivetrain.zeroPowerFloat();

                    ScoringSystem.launcherUpdate();

                    drivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0);

                    ScoringSystem.intake(gamepad1.left_trigger, gamepad1.right_trigger);

                    if (launcherAccel.isPressed()) {
                        ScoringSystem.launchAccel();
                        ScoringSystem.launcherUpdate();
                    }
                    if (launcherDecel.isPressed()) {
                        ScoringSystem.launchDecel();
                        ScoringSystem.launcherUpdate();
                    }

                    if (stateForward.isPressed()) {
                        robotState = SCORE;
                    }
                    if (stateBack.isPressed()) {
                        robotState = INTAKE;
                    }

                    break;

                case SCORE:
                    ServoGate.openGate();
                    drivetrain.zeroPowerBrake();

                    ScoringSystem.launcherUpdate();

                    drivetrain.botOrientedDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0);

                    ScoringSystem.intake(gamepad1.left_trigger, gamepad1.right_trigger);

                    if (launcherAccel.isPressed()) {
                        ScoringSystem.launchAccel();
                        ScoringSystem.launcherUpdate();
                    }
                    if (launcherDecel.isPressed()) {
                        ScoringSystem.launchDecel();
                        ScoringSystem.launcherUpdate();
                    }

                    if (stateForward.isPressed()) {
                        robotState = INTAKE;
                    }

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + robotState);
            }
            telemetry.addData("RobotState", robotState);

            telemetry.addData("Front Left Motor Power: ", drivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power: ", drivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power: ", drivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power: ", drivetrain.getBackRightPower());

            telemetry.addData("Intake Motor Velocity: ", ScoringSystem.getIntakeVel());
            telemetry.addData("Launcher Motor Velocity ", ScoringSystem.getLauncherVel());

            telemetry.addData("Launcher Motor Target Vel: ", ScoringSystem.LaunchVel);

            //telemetry.addData("Launcher Motor Multiplier: ", ScoringSystem.LaunchMult);

            telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
            telemetry.addData("Left Stick Y: ", gamepad1.left_stick_y);
            telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
            telemetry.addData("Left Trigger: ", gamepad1.left_trigger);
            telemetry.addData("Right Trigger: ", gamepad1.right_trigger);
            telemetry.update();
        }
    }
    enum RobotState {
        INTAKE,
        PRESCORE,
        SCORE
    }
}