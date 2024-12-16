package org.firstinspires.ftc.teamcode.May.lib.subsystems;

import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.GripperSubsystem;
public class ManipulationSubsystem {
    private final SlidesSubsystem objSlideSub;
    private final GripperSubsystem objGripperSub;

    int varSlidesClimbState = 0;
    public ManipulationSubsystem(SlidesSubsystem slideSub, GripperSubsystem gripperSub) {
        this.objSlideSub = slideSub;
        this.objGripperSub = gripperSub;
    }
    public void slidesClimbStages() {

        int maxSlidesClimbState = 2;

        varSlidesClimbState++;

        if (varSlidesClimbState == maxSlidesClimbState + 1) {
            varSlidesClimbState = 0;
        }

        switch (varSlidesClimbState) {
            case 0:
                objSlideSub.setDualSliderTarget(1500);
                objSlideSub.setDualRotatorTarget(50);
                break;
            case 1:
                objSlideSub.setDualSliderTarget(0);
                objSlideSub.setDualRotatorTarget(110);
                break;
            case 2:
                objSlideSub.setDualSliderTarget(0);
                objSlideSub.setDualRotatorTarget(50);
                break;
            default:
                objSlideSub.setDualSliderTarget(0);
                objSlideSub.setDualRotatorTarget(0);
                break;
        }
    }
}
