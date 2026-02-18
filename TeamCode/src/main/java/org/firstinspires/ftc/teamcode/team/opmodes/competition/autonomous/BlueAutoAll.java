package org.firstinspires.ftc.teamcode.team.opmodes.competition.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.InstantFunction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.team.subsystems.ScoringSystem;
import org.firstinspires.ftc.teamcode.team.subsystems.ServoGate;

@Autonomous(name = "BlueCornerAutoAll", group = "Autonomous OpMode")
public class BlueAutoAll extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        int tinyPause = 200;
        int littlePause = 400;
        int bigPause = 500;
        int scorePause = 2250;

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        ScoringSystem scoringSystem = new ScoringSystem(
                (DcMotorEx) hardwareMap.dcMotor.get("launcher"),
                (DcMotorEx) hardwareMap.dcMotor.get("intake"),
                (DcMotorEx) hardwareMap.dcMotor.get("turret"),
                (DcMotorEx) hardwareMap.dcMotor.get("launcher2")
        );

        Pose2d InitPosition = new Pose2d(-52.6, -52.8, Math.toRadians(-127.6));

        Pose2d ScorePositionPose = new Pose2d(-22, -25, Math.toRadians(-120));
        Vector2d ScorePosition = new Vector2d( -34, -24);

        Vector2d CollectAlignPos = new Vector2d(-37, -15);

        Vector2d PPGAlignPos = new Vector2d(-12,-15);
        Pose2d PPGAlignPose = new Pose2d(-12,-15, Math.toRadians(-90));

        Vector2d PPGGrabPos = new Vector2d(-12,-52);
        Pose2d PPGGrabPose = new Pose2d(-12,-52, Math.toRadians(-90));

        Vector2d PGPAlignPos = new Vector2d(13.5,-15);
        Pose2d PGPAlignPose = new Pose2d(12.5,-15, Math.toRadians(-90));

        Vector2d PGPGrabPos = new Vector2d(13.5,-57.5);
        Pose2d PGPGrabPose = new Pose2d(12.5,-57.5, Math.toRadians(-90));

        Vector2d GPPAlignPos = new Vector2d(35, -15);
        Pose2d GPPAlignPose = new Pose2d(35,-15, Math.toRadians(-90));

        Vector2d GPPGrabPos = new Vector2d(35,-57.5);
        Pose2d GPPGrabPose = new Pose2d(35,-57.5, Math.toRadians(-90));

        Vector2d ParkPos = new Vector2d(0, -48);
        Pose2d ParkPose = new Pose2d(0, -48, Math.toRadians(0));


        MecanumDrive drivetrain = new MecanumDrive(hardwareMap, InitPosition);

        TrajectoryActionBuilder initScore = drivetrain.actionBuilder(InitPosition)
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135))
                .afterTime(0, scoringSystem.intakeAction(0, 1));

        TrajectoryActionBuilder moveToIntakePPG = drivetrain.actionBuilder(ScorePositionPose)
                .strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(PPGAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakePPG = drivetrain.actionBuilder(PPGAlignPose)
                .strafeToLinearHeading(PPGGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder PPGToLauncher = drivetrain.actionBuilder(PPGGrabPose)
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToIntakePGP = drivetrain.actionBuilder(ScorePositionPose)
                //.strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(PGPAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakePGP = drivetrain.actionBuilder(PGPAlignPose)
                .strafeToLinearHeading(PGPGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder PGPToLauncher = drivetrain.actionBuilder(PGPGrabPose)
                .strafeToLinearHeading(PGPAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToIntakeGPP = drivetrain.actionBuilder(ScorePositionPose)
                //.strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(GPPAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakeGPP = drivetrain.actionBuilder(GPPAlignPose)
                .strafeToLinearHeading(GPPGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder GPPToLauncher = drivetrain.actionBuilder(GPPGrabPose)
                .strafeToLinearHeading(GPPAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToPark = drivetrain.actionBuilder(GPPGrabPose)
                .strafeToLinearHeading(ParkPos, Math.toRadians(0));
        TrajectoryActionBuilder blueAuto = drivetrain.actionBuilder(InitPosition);




        waitForStart();
        if (isStopRequested()) return;


        ServoGate ServoGate = new ServoGate(
                hardwareMap.servo.get("gate")
        );

        ServoGate.closeGate();
        scoringSystem.launcherUpdate();
        scoringSystem.intake(0,1);

        Actions.runBlocking(
                new SequentialAction(
                        initScore.build()
                )
        );

        scoringSystem.intake(0,0);

        sleep(littlePause);

        ServoGate.openGate();

        scoringSystem.intake(0,1);

        sleep(scorePause);

        scoringSystem.intake(0,0);
        ServoGate.closeGate();
        //ScoringSystem.launcherOff();

        Actions.runBlocking(new SequentialAction(moveToIntakePPG.build()));

        scoringSystem.intake(0,1);

        Actions.runBlocking(new SequentialAction(IntakePPG.build()));

        scoringSystem.launcherUpdate();

        scoringSystem.intake(0,0);

        Actions.runBlocking(new SequentialAction(PPGToLauncher.build()));

        sleep(littlePause);

        ServoGate.openGate();

        scoringSystem.intake(0,1);

        sleep(scorePause);

        scoringSystem.intake(0,0);
        ServoGate.closeGate();

        Actions.runBlocking(new SequentialAction(moveToIntakePGP.build()));

        scoringSystem.intake(0,1);

        Actions.runBlocking(new SequentialAction(IntakePGP.build()));

        scoringSystem.intake(0,1);
        scoringSystem.launcherUpdate();

        Actions.runBlocking(new SequentialAction(PGPToLauncher.build()));

        scoringSystem.intake(0,0);

        sleep(littlePause);

        ServoGate.openGate();

        scoringSystem.intake(0,1);

        sleep(scorePause);

        Action intakeAction = new InstantAction(
                new InstantFunction() {
                    @Override
                    public void run() {
                        scoringSystem.intake(0,1);
                    }
                }
        );

        ServoGate.closeGate();

        Actions.runBlocking(new SequentialAction(moveToIntakeGPP.build()));

        scoringSystem.intake(0,1);

        Actions.runBlocking(new SequentialAction(IntakeGPP.build()));

        scoringSystem.intake(0,0);
        scoringSystem.launcherOff();

        while(opModeIsActive()) {
            telemetry.addData("Intake Motor Velocity: ", scoringSystem.getIntakeVel());
            telemetry.addData("Launcher Motor Velocity ", scoringSystem.getLauncherVel());

            telemetry.addData("Launcher Motor Target Vel: ", scoringSystem.LaunchVel);

            dashboardTelemetry.addData("Intake Motor Velocity: ", scoringSystem.getIntakeVel());
            dashboardTelemetry.addData("Launcher Motor Velocity ", scoringSystem.getLauncherVel());

            dashboardTelemetry.addData("Launcher Motor Target Vel: ", scoringSystem.LaunchVel);

            dashboardTelemetry.update();
        }
    }
}