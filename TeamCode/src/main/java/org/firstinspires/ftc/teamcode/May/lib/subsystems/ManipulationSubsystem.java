package org.firstinspires.ftc.teamcode.May.lib.subsystems;

import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.GripperSubsystem;
public class ManipulationSubsystem {
    private final SlidesSubsystem objSlideSub;
    private final GripperSubsystem objGripperSub;
    private final int rotUpConstant = 120;
    public boolean varSpecimenRotState = false;
    public boolean varSampleState = false;
    public ManipulationSubsystem(SlidesSubsystem slideSub, GripperSubsystem gripperSub) {
        this.objSlideSub = slideSub;
        this.objGripperSub = gripperSub;
    }
    public void toggleSpeciSlideRotPosition() {
        varSpecimenRotState = !varSpecimenRotState;
        if (!varSpecimenRotState)
        {
            objSlideSub.setLeftRotatorTarget(900);
            objGripperSub.setLeftRotatorTarget(0.05);
        } else {
            objSlideSub.setLeftRotatorTarget(rotUpConstant);
            objGripperSub.setLeftRotatorTarget(0);
        }
    }
    public void toggleSpeciSlideExtendPosition() {

    }

    public void toggleSubmersibleMode() {
        varSampleState = !varSampleState;
        if (!varSampleState)
        {
            objSlideSub.setRightSliderTarget(1200);
            objSlideSub.setRightRotatorTarget(1400);
        } else {
            objSlideSub.setRightSliderTarget(0);
            objSlideSub.setRightRotatorTarget(rotUpConstant);
        }
    }
}