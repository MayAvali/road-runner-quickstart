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

@Autonomous(name = "RedCornerAuto", group = "Autonomous OpMode")
public class RedCornerAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        int tinyPause = 200;
        int littlePause = 250;
        int bigPause = 500;



        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();


        Pose2d InitPosition = new Pose2d(-62.9, -31.2, 0);

        Vector2d ScorePosition = new Vector2d( -35, -35);

        Vector2d CollectAlignPos = new Vector2d(-35, -25);

        Vector2d PPGAlignPos = new Vector2d(-12,-25);
        Vector2d PPGGrabPos = new Vector2d(-12,-50);
        Vector2d PGPAlignPos = new Vector2d(11.5,-25);
        Vector2d PGPGrabPos = new Vector2d(11.5,-50);
        Vector2d GPPAlignPos = new Vector2d(35, -25);
        Vector2d GPPGrabPos = new Vector2d(35,-50);

        MecanumDrive drivetrain = new MecanumDrive(hardwareMap, InitPosition);

        TrajectoryActionBuilder initScore = drivetrain.actionBuilder(InitPosition)
                .strafeToLinearHeading(ScorePosition, Math.toRadians(135));

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

        sleep(littlePause);

        ScoringSystem.intake(0,0);

        sleep(littlePause);

        ServoGate.openGate();

        sleep(littlePause);

        ScoringSystem.intake(0,0.5);

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