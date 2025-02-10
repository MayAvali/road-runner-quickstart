package com.example.meepmeeptesting;

import com.acmerobotics.dashboard.canvas.Spline;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 80, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(8, -62.7,(Math.toRadians(90))))
                .splineTo(new Vector2d(3, -31), Math.toRadians(90.00))
                .lineToY(-50)
                .splineTo(new Vector2d(35.00, -20.00), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(46.00, -15.00), Math.toRadians(270))
                .splineTo(new Vector2d(46.00, -55.00), Math.toRadians(270))
                .strafeTo(new Vector2d(46.00, -15.00))
                .strafeTo(new Vector2d(56.00, -15.00))
                .strafeTo(new Vector2d(56.00, -55.00))
                .strafeTo(new Vector2d(56.00, -15.00))
                .strafeTo(new Vector2d(61.30, -15.00))
                .setTangent(Math.toRadians(270))
                .lineToY(-50)
                .splineToConstantHeading(new Vector2d(46.3, -62), Math.toRadians(270))
//                .strafeTo(new Vector2d(61.30, -45.00))
//                .strafeTo(new Vector2d(46.00, -45.00))
                //.strafeTo(new Vector2d(46.30, -45.00))
                //.turn(Math.toRadians(-90.00))

                .strafeTo(new Vector2d(46.3, -52))
                .splineTo(new Vector2d(24, -48.00), Math.toRadians(-90))
                .strafeTo(new Vector2d(0, -31))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}