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

        Pose2d InitPosition = new Pose2d(-62.9, -31.2, 45);

        Vector2d PreloadScorePosition = new Vector2d( -35, 35);

        Vector2d CollectAlignPos = new Vector2d(-35, 25);

        Vector2d PPGAlignPos = new Vector2d(-12,25);
        Vector2d PPGGrabPos = new Vector2d(-12,50);
        Vector2d PGPAlignPos = new Vector2d(11.5,25);
        Vector2d PGPGrabPos = new Vector2d(11.5,50);
        Vector2d GPPAlignPos = new Vector2d(35, 25);
        Vector2d GPPGrabPos = new Vector2d(35,50);



        myBot.runAction(myBot.getDrive().actionBuilder(InitPosition)
                .strafeToLinearHeading(PreloadScorePosition, Math.toRadians(135))
                .strafeToLinearHeading(CollectAlignPos, Math.toRadians(100))
                .strafeToLinearHeading(PPGAlignPos, Math.toRadians(90))
                .strafeToLinearHeading(PPGGrabPos, Math.toRadians(110))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}