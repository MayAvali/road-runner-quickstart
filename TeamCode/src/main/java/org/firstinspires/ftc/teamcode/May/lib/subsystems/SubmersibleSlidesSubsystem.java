package org.firstinspires.ftc.teamcode.May.lib.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
public class SubmersibleSlidesSubsystem {
    public final DcMotor submersibleSlideMotor;
    boolean sliderState = false;
    public SubmersibleSlidesSubsystem(DcMotor submersibleSlideMotor) {
        this.submersibleSlideMotor = submersibleSlideMotor;
    }
}
