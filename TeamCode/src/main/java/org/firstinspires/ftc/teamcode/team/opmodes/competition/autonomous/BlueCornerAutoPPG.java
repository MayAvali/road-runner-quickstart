package org.firstinspires.ftc.teamcode.team.opmodes.competition.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.team.subsystems.ScoringSystem;
import org.firstinspires.ftc.teamcode.team.subsystems.ServoGate;

@Autonomous(name = "BlueCornerAutoPPG", group = "Autonomous OpMode")
public class BlueCornerAutoPPG extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        int tinyPause = 200;
        int littlePause = 250;
        int bigPause = 500;
        int scorePause = 2000;

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();


        Pose2d InitPosition = new Pose2d(-62.9, -31.2, 0);

        Pose2d ScorePositionPose = new Pose2d(-37, -37, Math.toRadians(-135));
        Vector2d ScorePosition = new Vector2d( -37, -37);

        Vector2d CollectAlignPos = new Vector2d(-37, -25);

        Vector2d PPGAlignPos = new Vector2d(-12,-25);
        Pose2d PPGAlignPose = new Pose2d(-12,-25, Math.toRadians(-90));

        Vector2d PPGGrabPos = new Vector2d(-12,-50);
        Pose2d PPGGrabPose = new Pose2d(-12,-50, Math.toRadians(-90));

        Vector2d PGPAlignPos = new Vector2d(11.5,-25);
        Pose2d PGPAlignPose = new Pose2d(11.5,-25, Math.toRadians(-90));

        Vector2d PGPGrabPos = new Vector2d(11.5,-50);
        Pose2d PGPGrabPose = new Pose2d(11.5,-50, Math.toRadians(-90));

        Vector2d GPPAlignPos = new Vector2d(35, -25);
        Pose2d GPPAlignPose = new Pose2d(35,-25, Math.toRadians(-90));

        Vector2d GPPGrabPos = new Vector2d(35,-50);
        Pose2d GPPGrabPose = new Pose2d(35,-50, Math.toRadians(-90));

        Vector2d ParkPos = new Vector2d(-30, -52);
        Pose2d ParkPose = new Pose2d(-30, -52, Math.toRadians(-90));


        MecanumDrive drivetrain = new MecanumDrive(hardwareMap, InitPosition);

        TrajectoryActionBuilder initScore = drivetrain.actionBuilder(InitPosition)
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToIntakePPG = drivetrain.actionBuilder(ScorePositionPose)
                .strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(PPGAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakePPG = drivetrain.actionBuilder(PPGAlignPose)
                .strafeToLinearHeading(PPGGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder PPGToLauncher = drivetrain.actionBuilder(PPGGrabPose)
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToIntakePGP = drivetrain.actionBuilder(ScorePositionPose)
                .strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(PGPAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakePGP = drivetrain.actionBuilder(PGPAlignPose)
                .strafeToLinearHeading(PGPGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder PGPToLauncher = drivetrain.actionBuilder(PGPGrabPose)
                .strafeToLinearHeading(PGPAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToIntakeGPP = drivetrain.actionBuilder(ScorePositionPose)
                .strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(GPPAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakeGPP = drivetrain.actionBuilder(GPPAlignPose)
                .strafeToLinearHeading(GPPGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder GPPToLauncher = drivetrain.actionBuilder(GPPGrabPose)
                .strafeToLinearHeading(GPPAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToPark = drivetrain.actionBuilder(ScorePositionPose)
                .strafeToLinearHeading(ParkPos, Math.toRadians(-90));



        waitForStart();
        if (isStopRequested()) return;

        ScoringSystem ScoringSystem = new ScoringSystem(
                (DcMotorEx) hardwareMap.dcMotor.get("intake"),
                (DcMotorEx) hardwareMap.dcMotor.get("intake2"),
                (DcMotorEx) hardwareMap.dcMotor.get("launcher"),
                hardwareMap.voltageSensor.iterator().next()
        );
        ServoGate ServoGate = new ServoGate(
                hardwareMap.servo.get("leftGate"),
                hardwareMap.servo.get("rightGate")
        );

        ServoGate.closeGate();
        ScoringSystem.launcherUpdate();
        ScoringSystem.intake(0,1);

        Actions.runBlocking(new SequentialAction(initScore.build()));

        ScoringSystem.intake(0,0);

        sleep(littlePause);

        ServoGate.openGate();

        ScoringSystem.intake(0,0.5);

        sleep(scorePause);

        ScoringSystem.intake(0,0);
        ServoGate.closeGate();
        ScoringSystem.launcherOff();

        Actions.runBlocking(new SequentialAction(moveToIntakePPG.build()));

        ScoringSystem.intake(0,1);

        Actions.runBlocking(new SequentialAction(IntakePPG.build()));

        ScoringSystem.intake(0,0.8);
        ScoringSystem.launcherUpdate();

        Actions.runBlocking(new SequentialAction(PPGToLauncher.build()));

        ScoringSystem.intake(0,0);

        sleep(littlePause);

        ServoGate.openGate();

        ScoringSystem.intake(0,0.5);

        sleep(scorePause);

        ScoringSystem.intake(0,0);
        ServoGate.closeGate();

        Actions.runBlocking(new SequentialAction(moveToPark.build()));

        while(opModeIsActive()) {
            telemetry.addData("Intake Motor Velocity: ", ScoringSystem.getIntakeVel());
            telemetry.addData("Launcher Motor Velocity ", ScoringSystem.getLauncherVel());

            telemetry.addData("Launcher Motor Target Vel: ", ScoringSystem.LaunchVel);

            dashboardTelemetry.addData("Intake Motor Velocity: ", ScoringSystem.getIntakeVel());
            dashboardTelemetry.addData("Launcher Motor Velocity ", ScoringSystem.getLauncherVel());

            dashboardTelemetry.addData("Launcher Motor Target Vel: ", ScoringSystem.LaunchVel);

            dashboardTelemetry.update();
        }
    }
}