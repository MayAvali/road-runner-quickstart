package org.firstinspires.ftc.teamcode.May.lib.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.GripperSubsystem;
public class ManipulationSubsystem {
    private final SlidesSubsystem objSlideSub;
    private final GripperSubsystem objGripperSub;
    private final int rotUpConstant = 30;
    public boolean varSpecimenState = false;
    public boolean varSampleState = false;
    public ManipulationSubsystem(SlidesSubsystem slideSub, GripperSubsystem gripperSub) {
        this.objSlideSub = slideSub;
        this.objGripperSub = gripperSub;
    }
    public void toggleSpeciMode() {
        varSpecimenState = !varSpecimenState;
        if (!varSpecimenState)
        {
            objSlideSub.setLeftSliderTarget(0);
            objSlideSub.setLeftRotatorTarget(120);
            objGripperSub.setLeftRotatorTarget(0);
        } else {
            objSlideSub.setLeftSliderTarget(0);
            objSlideSub.setLeftRotatorTarget(rotUpConstant);
            objGripperSub.setLeftRotatorTarget(0.15);
        }
    }
    public void toggleSampleMode() {
        varSampleState = !varSampleState;
        if (!varSampleState)
        {
            objSlideSub.setRightSliderTarget(0);
            objSlideSub.setRightRotatorTarget(220);
        } else {
            objSlideSub.setRightSliderTarget(0);
            objSlideSub.setRightRotatorTarget(rotUpConstant);
        }
    }
}