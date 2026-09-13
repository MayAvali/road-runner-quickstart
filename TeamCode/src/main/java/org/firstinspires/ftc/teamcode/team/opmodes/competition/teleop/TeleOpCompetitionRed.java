package org.firstinspires.ftc.teamcode.team.opmodes.competition.teleop;

import static org.firstinspires.ftc.teamcode.team.opmodes.competition.teleop.TeleOpCompetitionRed.RobotState.INTAKE;
// hehe line
//hehe line 2

//adb connect 192.168.43.1:5555

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.team.libraries.GamepadButton;
import org.firstinspires.ftc.teamcode.team.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.team.subsystems.ScoringSystem;
import org.firstinspires.ftc.teamcode.team.subsystems.ServoGate;
import org.firstinspires.ftc.teamcode.team.internalLib.AuxiliaryLocalizationSystem;

import java.util.List;
import java.util.Locale;

@TeleOp(name = "2. TeleOp RED", group = "Linear OpMode")
public class TeleOpCompetitionRed extends LinearOpMode {
    private String infoIMU = "";
    public GoBildaPinpointDriver pinpoint;

    @Override
    public void runOpMode() throws InterruptedException {

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        DigitalChannel dist;

        dist = hardwareMap.get(DigitalChannel.class, "dist");
        dist.setMode(DigitalChannel.Mode.INPUT);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

//        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
//
//        for (LynxModule hub : allHubs) {
//            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
//        }

        pinpoint.setOffsets(53,-101, DistanceUnit.MM);

        pinpoint.recalibrateIMU();

        sleep(750);
//
        pinpoint.setPosition(AuxiliaryLocalizationSystem.ConvertRRPoseToDriverPose((Pose2d) blackboard.get("BotPoseRR")));

        Pose2D TargetPose = new Pose2D(DistanceUnit.MM,1650,-1450,AngleUnit.DEGREES,0.0);
        Pose2D InitPose = new Pose2D(DistanceUnit.MM,-752.313,-1360.717,AngleUnit.DEGREES, -90);
        Pose2D ResetPose = new Pose2D(DistanceUnit.MM, 1530, -770, AngleUnit.DEGREES, 0); //1250, -975
        Pose2D ResetPoseHuman = new Pose2D(DistanceUnit.MM,-1494.382,1349.533,AngleUnit.DEGREES,90);

        int SmallManualSpeedAdjustment = 5;
        int ManualSpeedAdjustment = 25;

        telemetry.addData("Status", "Initialized");
        telemetry.addData("X offset", pinpoint.getXOffset(DistanceUnit.MM));
        telemetry.addData("Y offset", pinpoint.getYOffset(DistanceUnit.MM));
        telemetry.addData("Device Version Number:", pinpoint.getDeviceVersion());
        telemetry.addData("Heading Scalar", pinpoint.getYawScalar());
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        telemetry.setMsTransmissionInterval(11);

        //limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.
         */

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        RobotState robotState = INTAKE;

        //ElapsedTime timer = new ElapsedTime();
        //timer.reset();

        MecanumDrive drivetrain = new MecanumDrive(
                (DcMotorEx) hardwareMap.dcMotor.get("leftFront"),
                (DcMotorEx) hardwareMap.dcMotor.get("leftBack"),
                (DcMotorEx) hardwareMap.dcMotor.get("rightFront"),
                (DcMotorEx) hardwareMap.dcMotor.get("rightBack"),
                hardwareMap.get(IMU.class, "imu")
        );

        ScoringSystem scoringsystem = new ScoringSystem(
                (DcMotorEx) hardwareMap.dcMotor.get("launcher"),
                (DcMotorEx) hardwareMap.dcMotor.get("intake"),
                (DcMotorEx) hardwareMap.dcMotor.get("turret"),
                (DcMotorEx) hardwareMap.dcMotor.get("launcher2")
        );

        double last_tx_value = 0;
        boolean last_was_valid = false;
        double last_detection = getRuntime();
        double detection_start = getRuntime();
        ServoGate ServoGate = new ServoGate(
                hardwareMap.servo.get("gate")
        );

        Servo IndicatorLED = hardwareMap.get(Servo.class, "IndicatorLED");

        ServoGate.closeGate();

        GamepadButton stateBack = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.LEFT_BUMPER);
        GamepadButton stateForward = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.RIGHT_BUMPER);
        GamepadButton UpAccel = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_UP);
        GamepadButton RightAccelSmol = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_RIGHT);
        GamepadButton DownDecel = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_DOWN);
        GamepadButton LeftDecelSmol = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.DPAD_LEFT);
        GamepadButton ManualSpeedToggle = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.triangle);
        GamepadButton ManualTurretToggle = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.circle);
        GamepadButton PinpointReset = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.START);
        GamepadButton TargetReset = new GamepadButton(gamepad1, GamepadButton.gamepadKeys.SHARE);


        boolean ManualSpeedOn = false;
        boolean ManualTurretOn = false;

        boolean ArtifactPresent = false;

        infoIMU += "IMU - " + pinpoint.getDeviceName();
        infoIMU += " | ID: " + pinpoint.getDeviceID();
        infoIMU += " | ver" + pinpoint.getDeviceVersion();
        infoIMU += " | yawS: " + pinpoint.getYawScalar();

        double target = 0;
        double frequency = 0;
        long time = 0;
        long oldTime = 0;

        boolean targetResetIdle = false;

        drivetrain.zeroPowerBrake();

        while (opModeIsActive()) {

            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }

            frequency = (float) 1000 /(time-oldTime);;

            if (dist.getState() & scoringsystem.getIntakeCurrent() > 6){
                IndicatorLED.setPosition(1);
            } else {
                IndicatorLED.setPosition(0);
            }

            pinpoint.update();

            Pose2D  pinpointPose = pinpoint.getPosition();

            target = AuxiliaryLocalizationSystem.getAngle(pinpoint.getPosition(), TargetPose);

            switch (robotState) {
                case INTAKE:
                    ServoGate.closeGate();

                    drivetrain.botOrientedDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 0);

                    scoringsystem.setTurretTarget(0,2000);

                    if (stateForward.isPressed()) {
                        robotState = TeleOpCompetitionRed.RobotState.PRESCORE;
                    }
                    break;

                case PRESCORE:
                    ServoGate.closeGate();
                    drivetrain.botOrientedDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 0);

                    if(ManualTurretOn){
                        scoringsystem.setTurretTarget(0,2000);
                    }else{
                        scoringsystem.setTurretTarget(target,2000);
                    }

                    if (stateForward.isPressed()) {
                        robotState = TeleOpCompetitionRed.RobotState.SCORE;
                    }
                    if (stateBack.isPressed()) {
                        robotState = TeleOpCompetitionRed.RobotState.INTAKE;
                    }

                    break;

                case SCORE:
                    ServoGate.openGate();
                    drivetrain.botOrientedDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 0);

                    if(ManualTurretOn){
                        scoringsystem.setTurretTarget(0,2000);
                    }else{
                        scoringsystem.setTurretTarget(target,2000);
                    }

                    if (stateForward.isPressed()) {
                        robotState = TeleOpCompetitionRed.RobotState.INTAKE;
                    }

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + robotState);
            }

            if(ManualSpeedToggle.isPressed()){
                ManualSpeedOn = !ManualSpeedOn;
            }
            if(ManualTurretToggle.isPressed()){
                ManualTurretOn = !ManualTurretOn;
            }

            if(TargetReset.isPressed()){
                TargetPose = new Pose2D(DistanceUnit.MM,1700,-1450,AngleUnit.DEGREES,0.0);
            }

            if (PinpointReset.isPressed()) {
                if (!targetResetIdle) {
                    targetResetIdle = true;
                } else {
                    targetResetIdle = false;
                    pinpoint.recalibrateIMU();
                    sleep(500);
                    pinpoint.setPosition(ResetPose);
                }
            }

            if(!ManualSpeedOn){
                if(!targetResetIdle) {
                    scoringsystem.setLaunchVel( (int)  ScoringSystem.TurretDistToFlywheelVelocity(AuxiliaryLocalizationSystem.getDistance(pinpointPose, TargetPose)));
                } else {
                    scoringsystem.setLaunchVel(0);
                }

                //up = increase y
                //down = decrease y
                //left = increase x
                //right = decrease x
                if (UpAccel.isPressed()) {
                    TargetPose = new Pose2D(DistanceUnit.MM, TargetPose.getX(DistanceUnit.MM),TargetPose.getY(DistanceUnit.MM)+50,AngleUnit.DEGREES, TargetPose.getHeading(AngleUnit.DEGREES));
                }
                if (RightAccelSmol.isPressed()){
                    TargetPose = new Pose2D(DistanceUnit.MM, TargetPose.getX(DistanceUnit.MM)-50,TargetPose.getY(DistanceUnit.MM),AngleUnit.DEGREES, TargetPose.getHeading(AngleUnit.DEGREES));
                }
                if (DownDecel.isPressed()) {
                    TargetPose = new Pose2D(DistanceUnit.MM, TargetPose.getX(DistanceUnit.MM),TargetPose.getY(DistanceUnit.MM)-50,AngleUnit.DEGREES, TargetPose.getHeading(AngleUnit.DEGREES));
                }
                if (LeftDecelSmol.isPressed()){
                    TargetPose = new Pose2D(DistanceUnit.MM, TargetPose.getX(DistanceUnit.MM)+50,TargetPose.getY(DistanceUnit.MM),AngleUnit.DEGREES, TargetPose.getHeading(AngleUnit.DEGREES));
                }

            } else {
                if (UpAccel.isPressed()) {
                    scoringsystem.launchVelAdjust(ManualSpeedAdjustment);
                }
                if (RightAccelSmol.isPressed()){
                    scoringsystem.launchVelAdjust(SmallManualSpeedAdjustment);
                }
                if (DownDecel.isPressed()) {
                    scoringsystem.launchVelAdjust(-ManualSpeedAdjustment);
                }
                if (LeftDecelSmol.isPressed()){
                    scoringsystem.launchVelAdjust(-SmallManualSpeedAdjustment);
                }
                //scoringsystem.setLaunchVel(1365);
            }

            scoringsystem.launcherUpdate();

            scoringsystem.intake(gamepad1.left_trigger, gamepad1.right_trigger);

            oldTime = time;
            time = System.currentTimeMillis();

//            doTelemetry(
//                    telemetry,
//                    dashboardTelemetry,
//                    drivetrain,
//                    scoringsystem,
//                    TargetPose,
//                    result,
//                    robotState,
//                    gamepad1,
//                    frequency
//            );

            telemetry.addData("Refresh Rate Hz", frequency);
            telemetry.addData("Angle To Target", AuxiliaryLocalizationSystem.getAngle(pinpointPose, (TargetPose)));
            telemetry.update();
        }
    }



    private void doTelemetry(
            Telemetry telemetry,
            Telemetry dashboardTelementry,
            MecanumDrive drivetrain,
            ScoringSystem scoringSystem,
            Pose2D TargetPose,
            LLResult result,
            TeleOpCompetitionRed.RobotState state,
            Gamepad gamepad1,
            double frequency
    ){
        Pose2D pos = pinpoint.getPosition();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
        String velocity = String.format(Locale.US,"{XVel: %.3f, YVel: %.3f, HVel: %.3f}", pinpoint.getVelX(DistanceUnit.MM), pinpoint.getVelY(DistanceUnit.MM), pinpoint.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES));
        publishDashboard(dashboardTelementry, drivetrain, scoringSystem, gamepad1, data, velocity, frequency, time);
        publishTelemetry(state, drivetrain, scoringSystem, TargetPose, result, data, velocity, frequency);
    }

    private void publishDashboard(Telemetry dashboardTelemetry, MecanumDrive drivetrain, ScoringSystem scoringsystem, Gamepad gamepad1, String data, String velocity, double frequency, double time) {
        dashboardTelemetry.addData("Front Left Motor Power: ", drivetrain.getFrontLeftPower());
        dashboardTelemetry.addData("Back Left Motor Power: ", drivetrain.getBackLeftPower());
        dashboardTelemetry.addData("Front Right Motor Power: ", drivetrain.getFrontRightPower());
        dashboardTelemetry.addData("Back Right Motor Power: ", drivetrain.getBackRightPower());

        dashboardTelemetry.addData("Intake Motor Velocity: ", scoringsystem.getIntakeVel());
        dashboardTelemetry.addData("Launcher Motor Velocity ", scoringsystem.getLauncherVel());

        dashboardTelemetry.addData("Launcher Motor TargetPose Vel: ", scoringsystem.LaunchVel);

        dashboardTelemetry.addData("Turret Position: ", scoringsystem.getTurretPos());

        dashboardTelemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
        dashboardTelemetry.addData("Left Stick Y: ", gamepad1.left_stick_y);
        dashboardTelemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
        dashboardTelemetry.addData("Left Trigger: ", gamepad1.left_trigger);
        dashboardTelemetry.addData("Right Trigger: ", gamepad1.right_trigger);

        dashboardTelemetry.addData("Position", data);
        dashboardTelemetry.addData("Velocity", velocity);
        dashboardTelemetry.addData("REV Hub Frequency: ", frequency); //prints the control system refresh rate
        dashboardTelemetry.update();
    }
    private void publishTelemetry(TeleOpCompetitionRed.RobotState robotState, MecanumDrive drivetrain, ScoringSystem scoringsystem, Pose2D TargetPose, LLResult result, String data, String velocity, double frequency) {


        telemetry.addData("BotInitPoseRR",(Pose2d) blackboard.get("BotPoseRR"));
        telemetry.addData("BotInitPoseConverted", AuxiliaryLocalizationSystem.ConvertRRPoseToDriverPose((Pose2d) blackboard.get("BotPoseRR")));

        telemetry.addData("LimelightResultState", result == null ? "null" : (result.isValid() ? "Valid" : "Invalid"));

        telemetry.addData("RobotState", robotState);

        telemetry.addData("Front Left Motor Power: ", drivetrain.getFrontLeftPower());
        telemetry.addData("Back Left Motor Power: ", drivetrain.getBackLeftPower());
        telemetry.addData("Front Right Motor Power: ", drivetrain.getFrontRightPower());
        telemetry.addData("Back Right Motor Power: ", drivetrain.getBackRightPower());

        telemetry.addData("Intake Motor Velocity: ", scoringsystem.getIntakeVel());
        telemetry.addData("Launcher Motor Velocity ", scoringsystem.getLauncherVel());

        telemetry.addData("Launcher Motor TargetPose Vel: ", scoringsystem.LaunchVel);

        telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
        telemetry.addData("Left Stick Y: ", gamepad1.left_stick_y);
        telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
        telemetry.addData("Left Trigger: ", gamepad1.left_trigger);
        telemetry.addData("Right Trigger: ", gamepad1.right_trigger);

        telemetry.addData("Turret Positon: ", scoringsystem.getTurretPos());
        telemetry.addData("Turret TargetPose Position", scoringsystem.getTurretTargetPos());
        telemetry.addData("Robot Heading", Math.toDegrees(pinpoint.getHeading(AngleUnit.RADIANS)));

        telemetry.addData("IMU Status", pinpoint.getDeviceStatus());
        telemetry.addData(" IMU Info", infoIMU);
        telemetry.addData("Camera Calculated Distance", AuxiliaryLocalizationSystem.getDistancefromAngle(result.getTy(), 750));
        telemetry.addData("OdoCalculatedDistance", AuxiliaryLocalizationSystem.getDistance(pinpoint.getPosition(), TargetPose));

        telemetry.addData("TargetPose X", TargetPose.getX(DistanceUnit.MM));
        telemetry.addData("TargetPose Y", TargetPose.getY(DistanceUnit.MM));

        telemetry.addData("Velocity", velocity);

        telemetry.addData("Status", pinpoint.getDeviceStatus());

        telemetry.addData("Pinpoint Frequency", pinpoint.getFrequency()); //prints/gets the current refresh rate of the Pinpoint

        telemetry.addData("Position", data);

        telemetry.addData("REV Hub Frequency: ", frequency); //prints the control system refresh rate


        telemetry.update();
    }
    enum RobotState {
        INTAKE,
        PRESCORE,
        PRIME,
        SCORE
    }
}