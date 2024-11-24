package org.firstinspires.ftc.teamcode.May.lib.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class LeftSlidesSubsystem {

    //private final DcMotor slidesMotorLeft;
    private final DcMotor slidesMotorRight;

    boolean scoreSample = false;
    boolean sliderState = true;
    public LeftSlidesSubsystem(DcMotor slideR) {

        //slidesMotorLeft = slideL;
        slidesMotorRight = slideR;

        //slidesMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slidesMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //slidesMotorLeft.setTargetPosition(0);
        slidesMotorRight.setTargetPosition(scoreSample ? 0 : 130);
        slidesMotorRight.setPower(-1);
        //slidesMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slidesMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setSliderTarget(int p) {
        //slidesMotorLeft.setTargetPosition(p);
        slidesMotorRight.setTargetPosition(p);
    }
    public void toggleMode() {
        scoreSample =! scoreSample;
        if (!sliderState) {
            setSliderTarget(scoreSample ? 2170 : 1950); //if scoring sample, set to 1st var, else 2nd var
        } else {
            setSliderTarget(scoreSample ? 0 : 130);
        }
    }
    public void dispenseSpecimen() {
        if (!scoreSample) {
            setSliderTarget(1350);
                sliderState = true;
        }
    }
    public void decline() {
        setSliderTarget(getSlideTargetPosition() - 5);
    }
    public void togglePos() {
        sliderState =! sliderState;
        if (!sliderState) {
            setSliderTarget(scoreSample ? 2170 : 1950); //if scoring sample, set to 1st var, else 2nd var
        } else {
            setSliderTarget(scoreSample ? 0 : 170);
        }
    }
    public int getSlidePosition() {
        return slidesMotorRight.getCurrentPosition();
    }
    public int getSlideTargetPosition() {
        return slidesMotorRight.getTargetPosition();
    }
    public boolean getSlideMode() {
        return scoreSample;
    }
    public boolean getSlideState() {
        return sliderState;
    }
}