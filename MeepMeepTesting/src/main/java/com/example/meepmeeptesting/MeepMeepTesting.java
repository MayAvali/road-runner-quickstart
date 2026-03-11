package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(90, 90, Math.toRadians(180), Math.toRadians(180), 12)
                .setDimensions(15.375, 14.4)
                .build();

        double scorePause = 1200.0;
        double prescorePause = 1200.0;
        double smallPause = 250.0;
        double gateIntakePause = 1500.0;

        Pose2d InitPosition = new Pose2d(-49.4, -47.9, Math.toRadians(-125));

        Pose2d PreScorePositionPose = new Pose2d(-30, -24, Math.toRadians(-125));
        Vector2d PreScorePosition = new Vector2d( -30, -24);

        Pose2d ScorePositionPose = new Pose2d(-12, -12.5, Math.toRadians(-132.5));
        Vector2d ScorePosition = new Vector2d(-12, -12.5);

        Vector2d CollectAlignPos = new Vector2d(-30, -18);

        Vector2d GPPAlignPos = new Vector2d(-10,-40);
        Pose2d GPPAlignPose = new Pose2d(-10,-40, Math.toRadians(-90));

        Vector2d GPPGrabPos = new Vector2d(-10,-52);
        Pose2d GPPGrabPose = new Pose2d(-10,-52, Math.toRadians(-90));

        Vector2d PGPAlignPos = new Vector2d(15.5,-40);
        Pose2d PGPAlignPose = new Pose2d(15.5,-40, Math.toRadians(-90));

        Vector2d PGPGrabPos = new Vector2d(15.5,-59.5);
        Pose2d PGPGrabPose = new Pose2d(15.5,-59.5, Math.toRadians(-90));

        Vector2d PGPGatePos = new Vector2d(15.5, -52);
        Pose2d PGPGatePose = new Pose2d(15.5, -52, Math.toRadians(-90));

        Vector2d GateParkPos = new Vector2d(3, -55.5);
        Pose2d GateParkPose = new Pose2d(3, -55.5, Math.toRadians(0));

        Vector2d GateIntakePos = new Vector2d(12, -59.2);
        Pose2d GateIntakePose = new Pose2d(12, -59.2, Math.toRadians(240));

        Vector2d GateLeavePos = new Vector2d(3,-25);
        Pose2d GateLeavePose = new Pose2d(3, -25, Math.toRadians(0));

        Vector2d PPGAlignPos = new Vector2d(40, -40);
        Pose2d PPGAlignPose = new Pose2d(40, -40, Math.toRadians(-90));

        Vector2d PPGGrabPos = new Vector2d(40, -58.5);
        Pose2d PPGGrabPose = new Pose2d(40, -58.5, Math.toRadians(-90));

        Vector2d ParkPos = new Vector2d(-0, -40);
        Pose2d ParkPose = new Pose2d(-0, -40, Math.toRadians(-90));



        myBot.runAction(myBot.getDrive().actionBuilder(InitPosition)
                //score preloaded
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-132.5))
                .waitSeconds(scorePause/1000)

                //grab pgp artifacts for scoring
                .setTangent(Math.toRadians(45))
                .splineToSplineHeading(PGPAlignPose, Math.toRadians(-90))
                .lineToYSplineHeading(PGPGrabPos.y, Math.toRadians(-90))

                //go score
                .lineToYSplineHeading(PGPAlignPos.y, Math.toRadians(-90))
                .splineToLinearHeading(ScorePositionPose, Math.toRadians(-132.5))
                .waitSeconds(scorePause/1000)

                //open gate and intake ONE
                .strafeToLinearHeading(GateIntakePos, Math.toRadians(-120))
                .waitSeconds(gateIntakePause/1000)


                //go score
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-132.5))
                .waitSeconds(scorePause/1000)

                //open gate and intake TWO
                .strafeToLinearHeading(GateIntakePos, Math.toRadians(-120))
                .waitSeconds(gateIntakePause/1000)

                //go score
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-132.5))
                .waitSeconds(scorePause/1000)

                //get gpp artifacts
                .setTangent(Math.toRadians(210))
                .splineToSplineHeading(GPPAlignPose, Math.toRadians(-90))
                .lineToYSplineHeading(GPPGrabPos.y, Math.toRadians(-90))

                //go score
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-132.5))
                .waitSeconds(scorePause/1000)

                //get ppg artifacts
                .setTangent(Math.toRadians(0))
                .splineToSplineHeading(PPGAlignPose, Math.toRadians(-90))
                .lineToYSplineHeading(PPGGrabPos.y, Math.toRadians(-90))

                //go score
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-132.5))
                .waitSeconds(scorePause/1000)

                //go park
                .strafeToLinearHeading(ParkPos, Math.toRadians(-180))

                //build !
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}