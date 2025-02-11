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

        double pickTime = 0.5;
        double scoreTime = 0.7;

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(8, -62.7,(Math.toRadians(90))))
                .splineTo(new Vector2d(4, -31), Math.toRadians(90.00))

                //Score Preloaded Specimen
                .waitSeconds(1)
                
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(35,-33, Math.toRadians(-90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(38,-21, Math.toRadians(-90)), Math.toRadians(90))

                .splineToSplineHeading(new Pose2d(46.00, -13.00, Math.toRadians(270)), Math.toRadians(270))
                .splineTo(new Vector2d(46.00, -55.00), Math.toRadians(270))
                .strafeTo(new Vector2d(46.00, -13.00))
                .strafeTo(new Vector2d(56.00, -13.00))
                .strafeTo(new Vector2d(56.00, -55.00))
                .lineToY(-13)
                .strafeTo(new Vector2d(61.30, -13.00))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(61.3, -45, Math.toRadians(-90)), Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(46.3, -62, Math.toRadians(-90)), Math.toRadians(-90))

                //Pick Up Specimen 2
                .waitSeconds(pickTime)

                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(2,-31, Math.toRadians(90)), Math.toRadians(90))

                //Score Specimen 2
                .waitSeconds(scoreTime)

                .setTangent(Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(46.3,-62, Math.toRadians(-90)), Math.toRadians(-90))

                //Pick Up Specimen 3
                .waitSeconds(pickTime)

                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(0,-31, Math.toRadians(90)), Math.toRadians(90))

                //Score Specimen 3
                .waitSeconds(scoreTime)

                .setTangent(Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(46.3,-62, Math.toRadians(-90)), Math.toRadians(-90))

                //Pick Up Specimen 4
                .waitSeconds(pickTime)

                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-2,-31, Math.toRadians(90)), Math.toRadians(90))

                //Score Specimen 4
                .waitSeconds(scoreTime)

                .setTangent(Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(46.3,-62, Math.toRadians(-90)), Math.toRadians(-90))

                //Pick Up Specimen 5
                .waitSeconds(pickTime)

                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-4,-31, Math.toRadians(90)), Math.toRadians(90))

                //Score Specimen 5
                .waitSeconds(scoreTime)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}