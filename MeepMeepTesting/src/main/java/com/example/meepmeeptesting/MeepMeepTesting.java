package com.example.meepmeeptesting;

import static com.example.meepmeeptesting.AutoMap.poseAngle;
import static com.example.meepmeeptesting.AutoMap.trunc;

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

        final Pose2d InitPosition = AutoMap.RedGoalInitPosition;

        final Pose2d ScorePosition = AutoMap.RedScorePosition;

        final Pose2d HumanAlign = AutoMap.RedHumanAlign;

        final Pose2d HumanGrab = AutoMap.RedHumanGrab;

        final Pose2d GPPAlign = AutoMap.RedGPPAlign;

        final Pose2d GPPGrab = AutoMap.RedGPPGrab;

        final Pose2d Park = AutoMap.RedParkFar;

        int scoreAngle = -130;


        myBot.runAction(myBot.getDrive().actionBuilder(InitPosition)

                .splineToSplineHeading(GPPAlign, poseAngle(GPPAlign))
                .lineToYSplineHeading(trunc(GPPGrab).y, poseAngle(GPPGrab))

                //Move to Scoring Position
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading((ScorePosition), poseAngle(ScorePosition))

                        .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}