package org.firstinspires.ftc.teamcode.May.lib.opmodes.Autonomous;

// RR-specific imports
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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

        long pickTime = 300;
        long scoreTime = 500;
        double initScoreVal = -4;
        double scoreDist = -2.5;

        Pose2d initialPose = new Pose2d(-8, 62.7, Math.toRadians(-90));
        Pose2d secondPose = new Pose2d(initScoreVal, 32, Math.toRadians(-90));
        Pose2d pickupPose = new Pose2d(-46.3, 61, Math.toRadians(90));
        Pose2d pickupPose2 = new Pose2d(-46.3, 57, Math.toRadians(90));
        Pose2d pickupPose3 = new Pose2d(-46.3, 52, Math.toRadians(90));
        Pose2d fourthPose = new Pose2d(initScoreVal-scoreDist, 30, Math.toRadians(-90));
        Pose2d fifthPose = new Pose2d(initScoreVal-(2*scoreDist), 27, Math.toRadians(-90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder initScore = drive.actionBuilder(initialPose)

                .splineTo(new Vector2d(-4, 31), Math.toRadians(-90.00));

        TrajectoryActionBuilder samplePush = drive.actionBuilder(secondPose)
                .setTangent(Math.toRadians(-270))
                .splineToSplineHeading(new Pose2d(-35,32, Math.toRadians(45)), Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-38,21, Math.toRadians(90)), Math.toRadians(-90))

                //Push Sample 1

                .splineToSplineHeading(new Pose2d(-44.00, 13.00, Math.toRadians(-270)), Math.toRadians(-270))

                //Push Sample 2

                .splineTo(new Vector2d(-46.00, 50.00), Math.toRadians(-270))
                .strafeTo(new Vector2d(-46.00, 13.00))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-56.00, 45.00, Math.toRadians(-270)), Math.toRadians(-90))

                .strafeTo(new Vector2d(-56, 18.00))

                // Push Sample 3

                .strafeTo(new Vector2d(-61, 10.00))

                .setTangent(Math.toRadians(-270))
                .splineToLinearHeading(new Pose2d(-61, 45, Math.toRadians(90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-46.3, 62, Math.toRadians(90)), Math.toRadians(90));

        TrajectoryActionBuilder postScore1 = drive.actionBuilder(pickupPose)
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(fourthPose, Math.toRadians(-90));

        TrajectoryActionBuilder postReturn1 = drive.actionBuilder(fourthPose)
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(pickupPose2, Math.toRadians(90));

        TrajectoryActionBuilder postScore2 = drive.actionBuilder(pickupPose2)
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(fifthPose, Math.toRadians(-90));

        TrajectoryActionBuilder postReturn2 = drive.actionBuilder(fifthPose)
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(pickupPose3, Math.toRadians(90));

        waitForStart();

        PrimaryGripperSubsystem objGrippers = new PrimaryGripperSubsystem(
                hardwareMap.servo.get("primaryGripperServo"),
                hardwareMap.servo.get("primaryRotatorServo")
        );

        PrimarySlidesSubsystem objSlides = new PrimarySlidesSubsystem(
                hardwareMap.dcMotor.get("primarySlidesMotor")
        );

        SubmersibleGripperSubsystem objSubmersibleGrippers = new SubmersibleGripperSubsystem(
                hardwareMap.servo.get("submersibleGripperServo"),
                hardwareMap.servo.get("submersibleRotatorServo")
        );

        SubmersibleSlidesSubsystem objSubmersibleSlides = new SubmersibleSlidesSubsystem(
                hardwareMap.dcMotor.get("submersibleSlidesMotor")
        );

        //sleep(300);

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

        objSlides.togglePos();

        Actions.runBlocking(
                new SequentialAction(
                        postScore1.build()
                )
        );

        objSlides.dispenseSpecimen();

        sleep(scoreTime);

        objGrippers.toggleClamp();
        objSlides.togglePos();

        Actions.runBlocking(
                new SequentialAction(
                        postReturn1.build()
                )
        );
        objGrippers.toggleClamp();

        sleep(pickTime);

        objSlides.togglePos();

        Actions.runBlocking(
                new SequentialAction(
                        postScore2.build()
                )
        );

        objSlides.dispenseSpecimen();

        sleep(scoreTime);

        objGrippers.toggleClamp();
        objSlides.setSliderTarget(0);

        Actions.runBlocking(
                new SequentialAction(
                        postReturn2.build()
                )
        );
    }
}