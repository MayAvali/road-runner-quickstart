package org.firstinspires.ftc.teamcode.May.lib.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
public class SubmersibleSlidesSubsystem {
    public final DcMotor submersibleSlideMotor;
    boolean sliderState = false;
    public SubmersibleSlidesSubsystem(DcMotor submersibleSlideMotor) {
        this.submersibleSlideMotor = submersibleSlideMotor;
    }
    public void togglePos() {
        sliderState = !sliderState;
        setSliderTarget(sliderState ? 1200 : 0);
    }
    private void setSliderTarget(int p) {
        submersibleSlideMotor.setTargetPosition(p);
    }
    public int getSubSlidePosition() {
        return submersibleSlideMotor.getCurrentPosition();
    }
    public int getSubSlideTargetPosition() {
        return submersibleSlideMotor.getTargetPosition();
    }
    public boolean getSubSlideState() {
        return sliderState;
    }
}