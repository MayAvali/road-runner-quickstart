package org.firstinspires.ftc.teamcode.team.internalLib;

import static com.qualcomm.robotcore.eventloop.opmode.OpMode.blackboard;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;

public class LocalizationMemory {
    static double CorrectionConstant = 1.04198639247;
    public static Action WritePosToMemory(Pose2d input) {
        return new InstantAction( () -> {
                double EndX = input.position.x * CorrectionConstant;
                double EndY = input.position.y * CorrectionConstant;
                double EndH = input.heading.toDouble();
                blackboard.put("AutoEndX", EndX);
                blackboard.put("AutoEndY", EndY);
                blackboard.put("AutoEndH", EndH);
            }
        );
    }
}
