package org.firstinspires.ftc.teamcode.May.lib.opmodes.Autonomous;

// RR-specific imports

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.May.lib.subsystems.PrimaryGripperSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.PrimarySlidesSubsystem;
import org.firstinspires.ftc.teamcode.RoadRunnerUtils.tuning.MecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "CompetitionRightSpeciAutoBlue", group = "Autonomous OpMode")
public class CompetitionRightSpeciAutoBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Pose2d initialPose = new Pose2d(-8, 62.7, Math.toRadians(270));
        Pose2d secondPose = new Pose2d(-8, 32, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder move1 = drive.actionBuilder(initialPose)
                .lineToY(32);

        TrajectoryActionBuilder move2 = drive.actionBuilder(secondPose)
                .lineToY(60);
                //.turn(Math.toRadians(90))
                //.lineToX(-50);

        waitForStart();

        PrimaryGripperSubsystem objGrippers = new PrimaryGripperSubsystem(
                hardwareMap.servo.get("gripperServo"),
                hardwareMap.servo.get("rotatorServo")
        );

        PrimarySlidesSubsystem objSlides = new PrimarySlidesSubsystem(
                hardwareMap.dcMotor.get("slidesMotor")
        );

        sleep(500);

        objSlides.togglePos();

        Actions.runBlocking(
                new SequentialAction(
                        move1.build()
                )
        );

        objSlides.dispenseSpecimen();

        sleep(500);

        objGrippers.toggleClamp();

        Actions.runBlocking(
                new SequentialAction(
                        move2.build()
                )
        );

        objSlides.toggleMode();
        objSlides.togglePos();
        objSlides.togglePos();

        sleep(1000);
    }
}
