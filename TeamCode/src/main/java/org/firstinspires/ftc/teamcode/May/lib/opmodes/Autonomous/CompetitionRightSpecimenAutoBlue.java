package org.firstinspires.ftc.teamcode.May.lib.opmodes.Autonomous;

// RR-specific imports
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.May.lib.subsystems.PrimaryGripperSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.PrimarySlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.SubmersibleGripperSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.SubmersibleSlidesSubsystem;
import org.firstinspires.ftc.teamcode.RoadRunnerUtils.tuning.MecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "CompetitionRightSpecimenAutoBlue", group = "Autonomous OpMode")
public class CompetitionRightSpecimenAutoBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{

        long pickTime = 500;
        long scoreTime = 700;
        double initScorePos = -4;
        double scoreDist = -2.5;

        Pose2d initialPose = new Pose2d(-8, 62.7, Math.toRadians(-90));
        Pose2d secondPose = new Pose2d(initScorePos, 31, Math.toRadians(-90));
        Pose2d pickupPose = new Pose2d(-46.3, 62, Math.toRadians(90));
        Pose2d fourthPose = new Pose2d(initScorePos-scoreDist, 31, Math.toRadians(-90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder initScore = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-4, 31), Math.toRadians(-90.00));

        TrajectoryActionBuilder samplePush = drive.actionBuilder(secondPose)
                .setTangent(Math.toRadians(-270))
                .splineToSplineHeading(new Pose2d(-35,33, Math.toRadians(45)), Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-38,21, Math.toRadians(90)), Math.toRadians(-90))

                //Push Sample 1

                .splineToSplineHeading(new Pose2d(-46.00, 13.00, Math.toRadians(-270)), Math.toRadians(-270))

                //Push Sample 2

                .splineTo(new Vector2d(-46.00, 55.00), Math.toRadians(-270))
                .strafeTo(new Vector2d(-49.00, 13.00))
                .strafeTo(new Vector2d(-56.00, 13.00))
                .strafeTo(new Vector2d(-56.00, 55.00))

                .lineToY(13)

                // Push Sample 3

                .strafeTo(new Vector2d(-61.30, 13.00))

                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-61.3, 45, Math.toRadians(90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-46.3, 62, Math.toRadians(90)), Math.toRadians(90));

        TrajectoryActionBuilder postScore1 = drive.actionBuilder(pickupPose)
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(initScorePos-scoreDist,31, Math.toRadians(-90)), Math.toRadians(-90));

        TrajectoryActionBuilder postReturn1 = drive.actionBuilder(fourthPose)
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(pickupPose, Math.toRadians(90));

        TrajectoryActionBuilder postScore2 = drive.actionBuilder(pickupPose)
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(initScorePos-(scoreDist*2),31, Math.toRadians(-90)), Math.toRadians(-90));

        TrajectoryActionBuilder postReturn2 = drive.actionBuilder(fourthPose)
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(pickupPose, Math.toRadians(90));

        waitForStart();

        PrimaryGripperSubsystem objGrippers = new PrimaryGripperSubsystem(
                hardwareMap.servo.get("gripperServo"),
                hardwareMap.servo.get("rotatorServo")
        );

        PrimarySlidesSubsystem objSlides = new PrimarySlidesSubsystem(
                hardwareMap.dcMotor.get("slidesMotor")
        );

        SubmersibleGripperSubsystem objSubmersibleGrippers = new SubmersibleGripperSubsystem(
                hardwareMap.servo.get("submersibleGripperServo"),
                hardwareMap.servo.get("submersibleRotatorServo")
        );

        SubmersibleSlidesSubsystem objSubmersibleSlides = new SubmersibleSlidesSubsystem(
                hardwareMap.dcMotor.get("submersibleSlidesMotor")
        );

        sleep(300);

        objSlides.togglePos();

        Actions.runBlocking(
                new SequentialAction(
                        initScore.build()
                )
        );

        objSlides.dispenseSpecimen();

        sleep(scoreTime);

        objGrippers.toggleClamp();
        objSlides.togglePos();

        Actions.runBlocking(
                new SequentialAction(
                        samplePush.build()
                )
        );

        objGrippers.toggleClamp();

        sleep(pickTime);
    }
}