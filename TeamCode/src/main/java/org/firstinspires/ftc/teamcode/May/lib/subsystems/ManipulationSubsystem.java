package org.firstinspires.ftc.teamcode.May.lib.subsystems;

import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.GripperSubsystem;
public class ManipulationSubsystem {
    private final SlidesSubsystem objSlideSub;
    private final GripperSubsystem objGripperSub;
    private final int retractConstant = 120;
    public boolean varSpecimenRotState = false;
    public boolean varSpeciExtendState = false;
    public boolean varSampleState = false;
    public ManipulationSubsystem(SlidesSubsystem slideSub, GripperSubsystem gripperSub) {
        this.objSlideSub = slideSub;
        this.objGripperSub = gripperSub;
    }
    public void toggleSpeciSlideRotPosition() {
        varSpecimenRotState = !varSpecimenRotState;
        if (varSpecimenRotState)
        {
            objSlideSub.setLeftRotatorTarget(900);
            objGripperSub.setLeftRotatorTarget(0.1);
        } else {
            objSlideSub.setLeftRotatorTarget(retractConstant);
            objGripperSub.setLeftRotatorTarget(0);
            toggleSpeciSlideExtendPosition();
        }
    }
    public void toggleSpeciSlideExtendPosition() {
        varSpeciExtendState = !varSpeciExtendState;
        if (varSpeciExtendState && varSpecimenRotState)
        {
            objSlideSub.setLeftSliderTarget(1650); // Needs tweaking.
        } else {
            objSlideSub.setLeftSliderTarget(0);
        }
    }

    public void toggleSubmersibleMode() {
        varSampleState = !varSampleState;
        if (!varSampleState)
        {
            objSlideSub.setRightSliderTarget(1200);
            objSlideSub.setRightRotatorTarget(1400);
        } else {
            objSlideSub.setRightSliderTarget(0);
            objSlideSub.setRightRotatorTarget(retractConstant);
        }
    }
}