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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .setDimensions(15.375, 14.4)
                .build();

        Pose2d InitPosition = new Pose2d(-49.4, -47.9, Math.toRadians(-125));

        Pose2d ScorePositionPose = new Pose2d(-30, -24, Math.toRadians(-125));
        Vector2d ScorePosition = new Vector2d( -30, -24);

        Vector2d CollectAlignPos = new Vector2d(-30, -18);

        Vector2d PPGAlignPos = new Vector2d(-10,-18);
        Pose2d PPGAlignPose = new Pose2d(-10,-18, Math.toRadians(-90));

        Vector2d PPGGrabPos = new Vector2d(-10,-52);
        Pose2d PPGGrabPose = new Pose2d(-10,-52, Math.toRadians(-90));

        Vector2d PGPAlignPos = new Vector2d(15.5,-18);
        Pose2d PGPAlignPose = new Pose2d(15.5,-18, Math.toRadians(-90));

        Vector2d PGPGrabPos = new Vector2d(15.5,-59.5);
        Pose2d PGPGrabPose = new Pose2d(15.5,-59.5, Math.toRadians(-90));

        Vector2d PGPGatePos = new Vector2d(15.5, -52);
        Pose2d PGPGatePose = new Pose2d(15.5, -52, Math.toRadians(-90));

        Vector2d GateParkPos = new Vector2d(3, -55.5);
        Pose2d GateParkPose = new Pose2d(3, -55.5, Math.toRadians(0));

        Vector2d GateLeavePos = new Vector2d(3,-25);
        Pose2d GateLeavePose = new Pose2d(3, -25, Math.toRadians(0));

        Vector2d ParkPos = new Vector2d(-16, -48);
        Pose2d ParkPose = new Pose2d(-16, -48, Math.toRadians(0));



        myBot.runAction(myBot.getDrive().actionBuilder(InitPosition)
                .strafeToLinearHeading(ScorePosition, Math.toRadians(-125))

                .strafeToLinearHeading(PGPAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(PGPGrabPos, Math.toRadians(-90))

                //Move back, then hit gate
                .setTangent(Math.toRadians(-270))
                .splineToLinearHeading(GateParkPose, Math.toRadians(-90))
                //.waitSeconds(5)

                .setTangent(Math.toRadians(-270))
                .lineToYSplineHeading(GateLeavePos.y, Math.toRadians(-45))
                .splineToLinearHeading(ScorePositionPose, Math.toRadians(-125))

                .strafeToLinearHeading(PPGAlignPos, Math.toRadians(-90))
                .strafeToLinearHeading(PPGGrabPos, Math.toRadians(-90))

                .strafeToLinearHeading(ScorePosition, Math.toRadians(-125))

                .strafeToLinearHeading(ParkPos, Math.toRadians(0))



        //Park

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}