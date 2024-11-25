package org.firstinspires.ftc.teamcode.May.lib.subsystems;

import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems.GripperSubsystem;
public class ManipulationSubsystem {
    private final SlidesSubsystem slideSub;
    private final GripperSubsystem gripperSub;
    public ManipulationSubsystem(SlidesSubsystem slideSub, GripperSubsystem gripperSub) {
        this.slideSub = slideSub;
        this.gripperSub = gripperSub;
    }
    public void slidesClimbStage1() {
        slideSub.setDualSliderTarget(400);
        slideSub.setDualRotatorTarget(650);
    }

}
