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

@Autonomous(name = "RedAutoAll", group = "Autonomous OpMode")
public class RedAutoAll extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        int littlePause = 400;
        int scorePause = 2250;

        //263124
        //311322
        //94.75 in

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        ScoringSystem scoringSystem = new ScoringSystem(
                (DcMotorEx) hardwareMap.dcMotor.get("launcher"),
                (DcMotorEx) hardwareMap.dcMotor.get("intake"),
                (DcMotorEx) hardwareMap.dcMotor.get("turret"),
                (DcMotorEx) hardwareMap.dcMotor.get("launcher2")
        );
        ServoGate ServoGate = new ServoGate(
                hardwareMap.servo.get("gate")
        );

        Pose2d InitPosition = new Pose2d(-49.4, 47.9, Math.toRadians(125));

        Pose2d ScorePositionPose = new Pose2d(-30, 24, Math.toRadians(125));
        Vector2d ScorePosition = new Vector2d( -30, 24);

        Vector2d CollectAlignPos = new Vector2d(-30, 18);

        Vector2d PPGAlignPos = new Vector2d(-10,18);
        Pose2d PPGAlignPose = new Pose2d(-10,18, Math.toRadians(90));

        Vector2d PPGGrabPos = new Vector2d(-10,52);
        Pose2d PPGGrabPose = new Pose2d(-10,52, Math.toRadians(90));

        Vector2d PGPAlignPos = new Vector2d(15.5,18);
        Pose2d PGPAlignPose = new Pose2d(15.5,18, Math.toRadians(90));

        Vector2d PGPGrabPos = new Vector2d(15.5,57.5);
        Pose2d PGPGrabPose = new Pose2d(15.5,57.5, Math.toRadians(90));

        Vector2d GPPAlignPos = new Vector2d(40.5, 18);
        Pose2d GPPAlignPose = new Pose2d(40.5,18, Math.toRadians(90));

        Vector2d GPPGrabPos = new Vector2d(40.5,58.5);
        Pose2d GPPGrabPose = new Pose2d(40.5,58.5, Math.toRadians(90));

        Vector2d ParkPos = new Vector2d(0, 48);
        Pose2d ParkPose = new Pose2d(0, 48, Math.toRadians(0));


        MecanumDrive drivetrain = new MecanumDrive(hardwareMap, InitPosition);

        TrajectoryActionBuilder auto = drivetrain.actionBuilder(InitPosition)
                //Init
                .afterTime(0, ServoGate.closeGateAction())
                .afterTime(0, scoringSystem.launcherUpdateAction())
                .afterTime(0, scoringSystem.intakeAction(0, 1))

                //Move to Scoring Position
                .strafeToLinearHeading(ScorePosition, Math.toRadians(125))

                //Score
                .afterTime(0, scoringSystem.intakeAction(0, 0))
                .afterTime(0,ServoGate.openGateAction())
                .waitSeconds((double)littlePause/1000)
                .afterTime(0, scoringSystem.intakeAction(0, 1))
                .waitSeconds((double)scorePause/1000)
                .afterTime(0, scoringSystem.intakeAction(0, 0))
                .afterTime(0, ServoGate.closeGateAction())

                //Intake PPG
                .strafeToLinearHeading(CollectAlignPos, Math.toRadians(90))
                .strafeToLinearHeading(PPGAlignPos, Math.toRadians(90))
                .afterTime(0, scoringSystem.intakeAction(0, 1))
                .strafeToLinearHeading(PPGGrabPos, Math.toRadians(90))
                .afterTime(1, scoringSystem.intakeAction(0, 1))

                //Move to Scoring Position
                .strafeToLinearHeading(ScorePosition, Math.toRadians(125))

                //Score
                .afterTime(0, scoringSystem.intakeAction(0, 0))
                .afterTime(0,ServoGate.openGateAction())
                .waitSeconds((double)littlePause/1000)
                .afterTime(0, scoringSystem.intakeAction(0, 1))
                .waitSeconds((double)scorePause/1000)
                .afterTime(0, scoringSystem.intakeAction(0, 0))
                .afterTime(0, ServoGate.closeGateAction())

                //Intake PGP
                .strafeToLinearHeading(PGPAlignPos, Math.toRadians(90))
                .afterTime(0, scoringSystem.intakeAction(0, 1))
                .strafeToLinearHeading(PGPGrabPos, Math.toRadians(90))
                .afterTime(1, scoringSystem.intakeAction(0, 1))

                //Move to scoring Positon
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(ScorePositionPose, Math.toRadians(125))

                //Score
                .afterTime(0, scoringSystem.intakeAction(0, 0))
                .afterTime(0,ServoGate.openGateAction())
                .waitSeconds((double)littlePause/1000)
                .afterTime(0, scoringSystem.intakeAction(0, 1))
                .waitSeconds((double)scorePause/1000)
                .afterTime(0, scoringSystem.intakeAction(0, 0))
                .afterTime(0, ServoGate.closeGateAction())

                //Intake GPP
                .strafeToLinearHeading(GPPAlignPos, Math.toRadians(90))
                .afterTime(0, scoringSystem.intakeAction(0, 1))
                .strafeToLinearHeading(GPPGrabPos, Math.toRadians(90))
                .afterTime(1, scoringSystem.intakeAction(0, 0))
                .afterTime(0, scoringSystem.launcherOffAction());


        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        auto.build()
                )
        );

        while(opModeIsActive()) {
            telemetry.addData("Intake Motor Velocity: ", scoringSystem.getIntakeVel());
            telemetry.addData("Launcher Motor Velocity ", scoringSystem.getLauncherVel());

            telemetry.addData("Launcher Motor Target Vel: ", scoringSystem.LaunchVel);

            dashboardTelemetry.addData("Intake Motor Velocity: ", scoringSystem.getIntakeVel());
            dashboardTelemetry.addData("Launcher Motor Velocity ", scoringSystem.getLauncherVel());

            dashboardTelemetry.addData("Launcher Motor Target Vel: ",scoringSystem.LaunchVel);

            dashboardTelemetry.update();
        }
    }
}
