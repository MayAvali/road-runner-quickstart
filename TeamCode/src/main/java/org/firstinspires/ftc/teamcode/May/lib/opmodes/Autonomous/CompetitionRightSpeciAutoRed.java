package org.firstinspires.ftc.teamcode.May.lib.opmodes.Autonomous;

// RR-specific imports
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.RoadRunnerUtils.tuning.MecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "CompetitionRightSpeciAutoRed", group = "Autonomous OpMode")
public class CompetitionRightSpeciAutoRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{

        GripperSubsystem objGrippers = new GripperSubsystem(
                hardwareMap.servo.get("gripperServo"),
                hardwareMap.servo.get("rotatorServo")
        );

        SlidesSubsystem objSlides = new SlidesSubsystem(
                hardwareMap.dcMotor.get("slidesMotor")
        );

        Pose2d initialPose = new Pose2d(8, -62.7, Math.toRadians(90));
        Pose2d secondPose = new Pose2d(-8, -32, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder move1 = drive.actionBuilder(initialPose)
                .lineToY(-32);

        TrajectoryActionBuilder move2 = drive.actionBuilder(secondPose)
                .lineToY(-55);
        //.turn(Math.toRadians(90))
        //.lineToX(-50);

        waitForStart();

        objSlides.togglePos();

        Actions.runBlocking(
                new SequentialAction(
                        move1.build()
                )
        );

        objSlides.dispenseSpecimen();

        sleep(500);

        objGrippers.RRtoggleClamp();

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
