package org.firstinspires.ftc.teamcode.May.lib.subsystems;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.May.lib.subsystems.SubmersibleGripperSubsystem;
public class SubmersibleSlidesSubsystem {
    public final DcMotor submersibleSlideMotor;
    public boolean sliderState = false;
    public SubmersibleSlidesSubsystem(DcMotor submersibleSlideMotor) {
        this.submersibleSlideMotor = submersibleSlideMotor;

        submersibleSlideMotor.setDirection(DcMotor.Direction.REVERSE);
        submersibleSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        submersibleSlideMotor.setTargetPosition(sliderState ? 1800 : 0);
        submersibleSlideMotor.setPower(1);
        submersibleSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void togglePos() {
        sliderState = !sliderState;
        if (sliderState) {
            setSliderTarget(1800);
        } else {
            setSliderTarget(0);
        }
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