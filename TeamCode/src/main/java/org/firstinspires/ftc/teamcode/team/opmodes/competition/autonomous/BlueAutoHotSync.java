package org.firstinspires.ftc.teamcode.team.opmodes.competition.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
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

@Autonomous(name = "BlueAutoHotSync", group = "Autonomous OpMode")
public class BlueAutoHotSync extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        int tinyPause = 200;
        int littlePause = 400;
        int bigPause = 500;
        int scorePause = 1500;

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();


        Pose2d InitPosition = new Pose2d(-52.24, -51.375, Math.toRadians(51.4));

        Pose2d ScorePositionPose = new Pose2d(-35, -25, Math.toRadians(-135));
        Vector2d ScorePosition = new Vector2d( -37, -37);

        Vector2d CollectAlignPos = new Vector2d(-37, -15);

        Vector2d PPGAlignPos = new Vector2d(-12,-15);
        Pose2d PPGAlignPose = new Pose2d(-12,-15, Math.toRadians(-90));

        Vector2d PPGGrabPos = new Vector2d(-12,-51);
        Pose2d PPGGrabPose = new Pose2d(-12,-51, Math.toRadians(-90));

        Vector2d PGPAlignPos = new Vector2d(11.5,-15);
        Pose2d PGPAlignPose = new Pose2d(11.5,-15, Math.toRadians(-90));

        Vector2d PGPGrabPos = new Vector2d(11.5,-57.5);
        Pose2d PGPGrabPose = new Pose2d(11.5,-57.5, Math.toRadians(-90));

        Vector2d GPPAlignPos = new Vector2d(35, -15);
        Pose2d GPPAlignPose = new Pose2d(35,-15, Math.toRadians(-90));

        Vector2d GPPGrabPos = new Vector2d(35,-57.5);
        Pose2d GPPGrabPose = new Pose2d(35,-57.5, Math.toRadians(-90));

        Vector2d GateAlignPos = new Vector2d(0, -15);
        Pose2d GateAlignPose = new Pose2d(0, -15,Math.toRadians(0));

        Vector2d GateParkPos = new Vector2d(0, -55.5);
        Pose2d GateParkPose = new Pose2d(0, -55.5, Math.toRadians(-180));

        Vector2d ParkPos = new Vector2d(5, -48);
        Pose2d ParkPose = new Pose2d(5, -48, Math.toRadians(0));


        MecanumDrive drivetrain = new MecanumDrive(hardwareMap, InitPosition);

        TrajectoryActionBuilder initScore = drivetrain.actionBuilder(InitPosition)
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToIntakePPG = drivetrain.actionBuilder(ScorePositionPose)
                .strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(PPGAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakePPG = drivetrain.actionBuilder(PPGAlignPose)
                .strafeToLinearHeading(PPGGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder PPGToLauncher = drivetrain.actionBuilder(GateParkPose)
                .strafeTo(ScorePosition)
                .turnTo(Math.toRadians(-135));

        TrajectoryActionBuilder moveToIntakePGP = drivetrain.actionBuilder(ScorePositionPose)
                //.strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(PGPAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakePGP = drivetrain.actionBuilder(PGPAlignPose)
                .strafeToLinearHeading(PGPGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder PGPToLauncher = drivetrain.actionBuilder(GateParkPose)
                //.strafeToLinearHeading(GateAlignPos, Math.toRadians(0))
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToIntakeGPP = drivetrain.actionBuilder(ScorePositionPose)
                //.strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(GPPAlignPos, Math.toRadians(-90));

        TrajectoryActionBuilder IntakeGPP = drivetrain.actionBuilder(GPPAlignPose)
                .strafeToLinearHeading(GPPGrabPos, Math.toRadians(-90));

        TrajectoryActionBuilder GPPToLauncher = drivetrain.actionBuilder(GPPGrabPose)
                .strafeToLinearHeading(GPPAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-135));

        TrajectoryActionBuilder moveToGate = drivetrain.actionBuilder(PPGGrabPose)
                .strafeToLinearHeading(GateParkPos, Math.toRadians(-180));

        TrajectoryActionBuilder moveToPark = drivetrain.actionBuilder(ScorePositionPose)
                .strafeToLinearHeading(GateAlignPos, Math.toRadians(0));



        waitForStart();
        if (isStopRequested()) return;

        ScoringSystem ScoringSystem = new ScoringSystem(
                (DcMotorEx) hardwareMap.dcMotor.get("intake"),
                (DcMotorEx) hardwareMap.dcMotor.get("launcher")
        );
        ServoGate ServoGate = new ServoGate(
                hardwareMap.servo.get("leftGate"),
                hardwareMap.servo.get("rightGate")
        );

        ServoGate.closeGate();
        ScoringSystem.launcherUpdate();

        sleep(2000);

        ScoringSystem.intake(0,1);

        Actions.runBlocking(new SequentialAction(initScore.build()));

        ScoringSystem.intake(0,0);

        sleep(littlePause);

        ServoGate.openGate();

        ScoringSystem.intake(0,0.4);

        sleep(scorePause);

        ScoringSystem.intake(0,0);
        ServoGate.closeGate();
        //ScoringSystem.launcherOff();

        Actions.runBlocking(new SequentialAction(moveToIntakePPG.build()));

        ScoringSystem.intake(0,1);

        Actions.runBlocking(new SequentialAction(IntakePPG.build()));

        ScoringSystem.intake(0,1);
        ScoringSystem.launcherUpdate();

        Actions.runBlocking(new SequentialAction(moveToGate.build()));

        sleep(2000);

        Actions.runBlocking(new SequentialAction(PGPToLauncher.build()));

        ScoringSystem.intake(0,0);

        sleep(littlePause);

        ServoGate.openGate();

        ScoringSystem.intake(0,0.4);

        sleep(scorePause);

        sleep(scorePause);

        ScoringSystem.intake(0,0);

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