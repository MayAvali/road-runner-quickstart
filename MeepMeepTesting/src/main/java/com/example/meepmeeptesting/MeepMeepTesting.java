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

        Vector2d GateParkPos = new Vector2d(0, -53.5);
        Pose2d GateParkPose = new Pose2d(0, -53.5, Math.toRadians(-180));

        Vector2d ParkPos = new Vector2d(0, -48);
        Pose2d ParkPose = new Pose2d(0, -48, Math.toRadians(0));



        myBot.runAction(myBot.getDrive().actionBuilder(InitPosition)
                        .strafeToLinearHeading(ScorePosition, Math.toRadians(-135))
                        .strafeToLinearHeading(CollectAlignPos, Math.toRadians(-90))
                        .strafeToLinearHeading(PPGAlignPos, Math.toRadians(-90))
                        .strafeToLinearHeading(PPGGrabPos, Math.toRadians(-90))
                        .strafeToLinearHeading(GateParkPos, Math.toRadians(-180))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}