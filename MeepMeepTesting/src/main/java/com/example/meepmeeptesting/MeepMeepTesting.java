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

        Pose2d InitPosition = new Pose2d(-52.6, -52.8, Math.toRadians(-127.6));

        Pose2d ScorePositionPose = new Pose2d(-30, -24, Math.toRadians(-120));
        Vector2d ScorePosition = new Vector2d( -30, -24);

        Vector2d CollectAlignPos = new Vector2d(-30, -18);

        Vector2d PPGAlignPos = new Vector2d(-12,-18);
        Pose2d PPGAlignPose = new Pose2d(-12,-18, Math.toRadians(-90));

        Vector2d PPGGrabPos = new Vector2d(-12,-52);
        Pose2d PPGGrabPose = new Pose2d(-12,-52, Math.toRadians(-90));

        Vector2d PGPAlignPos = new Vector2d(12.5,-18);
        Pose2d PGPAlignPose = new Pose2d(12.5,-18, Math.toRadians(-90));

        Vector2d PGPGrabPos = new Vector2d(12.5,-59.5);
        Pose2d PGPGrabPose = new Pose2d(12.5,-59.5, Math.toRadians(-90));

        Vector2d GPPAlignPos = new Vector2d(36, -18);
        Pose2d GPPAlignPose = new Pose2d(36,-18, Math.toRadians(-90));

        Vector2d GPPGrabPos = new Vector2d(36,-58.5);
        Pose2d GPPGrabPose = new Pose2d(36,-58.5, Math.toRadians(-90));

        Vector2d ParkPos = new Vector2d(0, -48);
        Pose2d ParkPose = new Pose2d(0, -48, Math.toRadians(0));



        myBot.runAction(myBot.getDrive().actionBuilder(InitPosition)
                .strafeToLinearHeading(ScorePosition, Math.toRadians(135))

                .strafeToLinearHeading(CollectAlignPos, Math.toRadians(90))
                .strafeToLinearHeading(PPGAlignPos, Math.toRadians(90))
                .strafeToLinearHeading(PPGGrabPos, Math.toRadians(90))

                .strafeToLinearHeading(ScorePosition, Math.toRadians(135))

                .strafeToLinearHeading(PGPAlignPos, Math.toRadians(90))
                .strafeToLinearHeading(PGPGrabPos, Math.toRadians(90))

                .setTangent(Math.toRadians(90))
                .splineTo(ScorePosition, Math.toRadians(135))

                .strafeToLinearHeading(GPPAlignPos, Math.toRadians(90))
                .strafeToLinearHeading(GPPGrabPos, Math.toRadians(90))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}