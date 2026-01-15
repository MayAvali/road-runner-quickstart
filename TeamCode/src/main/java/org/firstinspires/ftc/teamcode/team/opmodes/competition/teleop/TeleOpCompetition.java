package org.firstinspires.ftc.teamcode.team.opmodes.competition.teleop;

import static org.firstinspires.ftc.teamcode.team.opmodes.competition.teleop.TeleOpCompetition.RobotState.INTAKE;
import static org.firstinspires.ftc.teamcode.team.opmodes.competition.teleop.TeleOpCompetition.RobotState.PRESCORE;
import static org.firstinspires.ftc.teamcode.team.opmodes.competition.teleop.TeleOpCompetition.RobotState.PRIME;
import static org.firstinspires.ftc.teamcode.team.opmodes.competition.teleop.TeleOpCompetition.RobotState.SCORE;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.team.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.team.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.team.subsystems.ScoringSystem;
import org.firstinspires.ftc.teamcode.team.subsystems.ServoGate;

@TeleOp(name = "TeleOpCompetition", group = "Linear OpMode")
public class TeleOpCompetition extends LinearOpMode {

    private Limelight3A limelight;
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
        if (isStopRequested()) return;

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.
         */
        limelight.start();


        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        RobotState robotState = INTAKE;

        boolean precision = false;

        //ElapsedTime timer = new ElapsedTime();
        //timer.reset();

        MecanumDrive drivetrain = new MecanumDrive(
                hardwareMap.dcMotor.get("leftFront"),
                hardwareMap.dcMotor.get("leftBack"),
                hardwareMap.dcMotor.get("rightFront"),
                hardwareMap.dcMotor.get("rightBack"),
                hardwareMap.get(IMU.class, "imu")
        );

        ScoringSystem scoringsystem = new ScoringSystem(
                (DcMotorEx) hardwareMap.dcMotor.get("launcher"),
                (DcMotorEx) hardwareMap.dcMotor.get("intake"),
                (DcMotorEx) hardwareMap.dcMotor.get("turret")
        );

        double last_tx_value = 0;
        boolean last_was_valid = false;
        double last_detection = getRuntime();
        double detection_start = getRuntime();
        double rotCompensation = 0;
        ServoGate ServoGate = new ServoGate(
                hardwareMap.servo.get("gate")
        );

        ServoGate.closeGate();

        GamepadButton stateBack = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.LEFT_BUMPER);
        GamepadButton stateForward = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.RIGHT_BUMPER);
        GamepadButton launcherAccel = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_UP);
        GamepadButton launcherDecel = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_DOWN);
        GamepadButton modeSwitcher = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_LEFT);

        while (opModeIsActive()) {
            switch (robotState) {
                case INTAKE:
                    ServoGate.closeGate();
                    drivetrain.zeroPowerFloat();

                    scoringsystem.launcherOff();

                    drivetrain.botOrientedDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 0);

                    scoringsystem.intake(gamepad1.left_trigger, gamepad1.right_trigger);

                    if (stateForward.isPressed()) {
                        robotState = PRESCORE;
                    }


                    break;

                case PRESCORE:
                    ServoGate.closeGate();
                    drivetrain.zeroPowerFloat();

                    scoringsystem.setLaunchVel(1800);

                    scoringsystem.launcherUpdate();

                    drivetrain.botOrientedDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 0);

                    scoringsystem.intake(gamepad1.left_trigger, gamepad1.right_trigger);

                    if (launcherAccel.isPressed()) {
                        scoringsystem.launchAccel();
                        scoringsystem.launcherUpdate();
                    }
                    if (launcherDecel.isPressed()) {
                        scoringsystem.launchDecel();
                        scoringsystem.launcherUpdate();
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

                    scoringsystem.setLaunchVel(1800);

                    scoringsystem.launcherUpdate();

                    drivetrain.botOrientedDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 0);

                    if(precision){
                        scoringsystem.intake(gamepad1.left_trigger, gamepad1.right_trigger*0.4);
                    } else {
                        scoringsystem.intake(gamepad1.left_trigger, gamepad1.right_trigger*0.6);
                    }

                    if (launcherAccel.isPressed()) {
                        scoringsystem.launchAccel();
                        scoringsystem.launcherUpdate();
                    }
                    if (launcherDecel.isPressed()) {
                        scoringsystem.launchDecel();
                        scoringsystem.launcherUpdate();
                    }

                    if (stateForward.isPressed()) {
                        robotState = INTAKE;
                    }

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + robotState);
            }



            if (modeSwitcher.isPressed()) {
                precision = !precision;
            }


            LLResult result = limelight.getLatestResult();
            if (result != null) {
                Pose3D botpose = result.getBotpose();
                telemetry.addData("tx", result.getTx());
                telemetry.addData("ty", result.getTy());
                telemetry.addData("Botpose", botpose.toString());
            }
            double tx_value = result.getTx();
            double target = 0;
            if (!result.isValid() && ((last_detection - detection_start) > 0.25)) {
                //
               target = ((getRuntime() - last_detection) <= 0.25) ? last_tx_value : 0;
               last_was_valid = false;
                rotCompensation = 0;
            } else if (result.isValid()) {
                target = tx_value;

                if (!last_was_valid) {
                    detection_start = getRuntime();
                }
                last_detection = getRuntime();
                last_was_valid = true;
                last_tx_value = tx_value;
            } else {
                target = 0;
            }
            scoringsystem.setTurretTarget(target, rotCompensation, 2000);






            telemetry.addData("LimelightResultState", result == null ? "null" : (result.isValid() ? "Valid" : "Invalid"));

            telemetry.addData("RobotState", robotState);
            telemetry.addData("RobotIsInPreciseMode", precision);

            telemetry.addData("Front Left Motor Power: ", drivetrain.getFrontLeftPower());
            telemetry.addData("Back Left Motor Power: ", drivetrain.getBackLeftPower());
            telemetry.addData("Front Right Motor Power: ", drivetrain.getFrontRightPower());
            telemetry.addData("Back Right Motor Power: ", drivetrain.getBackRightPower());

            telemetry.addData("Intake Motor Velocity: ", scoringsystem.getIntakeVel());
            telemetry.addData("Launcher Motor Velocity ", scoringsystem.getLauncherVel());

            telemetry.addData("Launcher Motor Target Vel: ", scoringsystem.LaunchVel);

            //telemetry.addData("Launcher Motor Multiplier: ", ScoringSystem.LaunchMult);

            telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
            telemetry.addData("Left Stick Y: ", gamepad1.left_stick_y);
            telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
            telemetry.addData("Left Trigger: ", gamepad1.left_trigger);
            telemetry.addData("Right Trigger: ", gamepad1.right_trigger);

            telemetry.addData("Turret Positon: ", scoringsystem.getTurretPos());
            telemetry.addData("Turret Target Position", scoringsystem.getTurretTargetPos());
            telemetry.addData("Turret Power",  scoringsystem.getTurretTargetPos());
            telemetry.update();

            dashboardTelemetry.addData("Front Left Motor Power: ", drivetrain.getFrontLeftPower());
            dashboardTelemetry.addData("Back Left Motor Power: ", drivetrain.getBackLeftPower());
            dashboardTelemetry.addData("Front Right Motor Power: ", drivetrain.getFrontRightPower());
            dashboardTelemetry.addData("Back Right Motor Power: ", drivetrain.getBackRightPower());

            dashboardTelemetry.addData("Intake Motor Velocity: ", scoringsystem.getIntakeVel());
            dashboardTelemetry.addData("Launcher Motor Velocity ", scoringsystem.getLauncherVel());

            dashboardTelemetry.addData("Launcher Motor Target Vel: ", scoringsystem.LaunchVel);

            dashboardTelemetry.addData("Turret Position: ", scoringsystem.getTurretPos());

            dashboardTelemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
            dashboardTelemetry.addData("Left Stick Y: ", gamepad1.left_stick_y);
            dashboardTelemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
            dashboardTelemetry.addData("Left Trigger: ", gamepad1.left_trigger);
            dashboardTelemetry.addData("Right Trigger: ", gamepad1.right_trigger);

            dashboardTelemetry.update();
        }
    }
    enum RobotState {
        INTAKE,
        PRESCORE,
        PRIME,
        SCORE
    }
}