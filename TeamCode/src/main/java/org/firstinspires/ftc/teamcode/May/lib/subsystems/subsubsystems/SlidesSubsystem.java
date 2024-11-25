package org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
public class SlidesSubsystem {

    private final DcMotor leftSlidesMotor;
    private final DcMotor leftRotatorMotor;
    private final DcMotor rightSlidesMotor;
    private final DcMotor rightRotatorMotor;
    public SlidesSubsystem(DcMotor slideL, DcMotor rotatorL, DcMotor slideR, DcMotor rotatorR) {

        leftSlidesMotor = slideL;
        leftRotatorMotor = rotatorL;
        rightSlidesMotor = slideR;
        rightRotatorMotor = rotatorR;

        leftSlidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftRotatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRotatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightSlidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightRotatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRotatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setLeftSliderTarget(int p) {
        //leftSlidesMotor.setTargetPosition(p);
        leftSlidesMotor.setTargetPosition(p);
    }
    private void setLeftRotatorTarget(int p) {
        leftRotatorMotor.setTargetPosition(p);
    }

    private void setRightSliderTarget(int p) {
        //rightSlidesMotor.setTargetPosition(p);
        rightSlidesMotor.setTargetPosition(p);
    }
    private void setRightRotatorTarget(int p) {
        rightRotatorMotor.setTargetPosition(p);
    }

//    public void toggleMode() {
//        scoreSample =! scoreSample;
//        if (!sliderState) {
//            setSliderTarget(scoreSample ? 2170 : 1950); //if scoring sample, set to 1st var, else 2nd var
//        } else {
//            setSliderTarget(scoreSample ? 0 : 130);
//        }
//    }
//    public void dispenseSpecimen() {
//        if (!scoreSample) {
//            setSliderTarget(1350);
//                sliderState = true;
//        }
//    }
//    public void decline() {
//        setSliderTarget(getSlideTargetPosition() - 5);
//    }
//    public void togglePos() {
//        sliderState =! sliderState;
//        if (!sliderState) {
//            setSliderTarget(scoreSample ? 2170 : 1950); //if scoring sample, set to 1st var, else 2nd var
//        } else {
//            setSliderTarget(scoreSample ? 0 : 170);
//        }
//    }
//    public int getSlidePosition() {
//        return leftSlidesMotor.getCurrentPosition();
//    }
//    public int getSlideTargetPosition() {
//        return leftSlidesMotor.getTargetPosition();
//    }
//    public boolean getSlideMode() {
//        return scoreSample;
//    }
//    public boolean getSlideState() {
//        return sliderState;
//    }
}