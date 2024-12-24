package org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
public class SlidesSubsystem {

    private final DcMotor objLeftSlidesMotor;
    private final DcMotor objLeftRotatorMotor;
    private final DcMotor objRightSlidesMotor;
    private final DcMotor objRightRotatorMotor;
    private final int rotUpConstant = 3;
    public boolean varRightSliderState = false;
    public boolean varRightRotatorState = false;
    public boolean varLeftSliderState = false;
    public boolean varLeftRotatorState = false;
    public int varSlidesClimbState = 0;
    public SlidesSubsystem(DcMotor slideL, DcMotor rotatorL, DcMotor slideR, DcMotor rotatorR) {

        objLeftSlidesMotor = slideL;
        objLeftRotatorMotor = rotatorL;
        objRightSlidesMotor = slideR;
        objRightRotatorMotor = rotatorR;

        objLeftSlidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        objLeftSlidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        objLeftRotatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        objLeftRotatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        objRightSlidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        objRightSlidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        objRightRotatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        objRightRotatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setLeftSliderTarget(int p) {
        objLeftSlidesMotor.setTargetPosition(p);
    }
    public void setLeftRotatorTarget(int p) {
        objLeftRotatorMotor.setTargetPosition(p);
    }

    public void setRightSliderTarget(int p) {
        objRightSlidesMotor.setTargetPosition(-p);
    }
    public void setRightRotatorTarget(int p) {
        objRightRotatorMotor.setTargetPosition(-p);
    }

    public void setDualSliderTarget(int p) {
        objLeftSlidesMotor.setTargetPosition(p);
        objRightSlidesMotor.setTargetPosition(-p);
    }

    public void setDualRotatorTarget(int p) {
        objLeftRotatorMotor.setTargetPosition(p);
        objRightRotatorMotor.setTargetPosition(-p);
    }
    public void toggleRSliderMode() {
        varRightSliderState =! varRightSliderState;
        if (!varRightSliderState) {
            setRightSliderTarget(600); //(scoreSample ? 2170 : 1950); if scoring sample, set to 1st var, else 2nd var
        } else {
            setRightSliderTarget(0);
        }
    }
    public void toggleRRotatorMode() {
        varRightRotatorState =! varRightRotatorState;
        if (!varRightRotatorState) {
            setRightRotatorTarget(rotUpConstant*2);
        } else {
           setRightRotatorTarget(rotUpConstant);
        }
    }
    public void toggleLSliderMode() {
        varLeftSliderState =! varLeftSliderState;
        if (!varLeftSliderState) {
            setLeftSliderTarget(1500); //(scoreSample ? 2170 : 1950); if scoring sample, set to 1st var, else 2nd var
        } else {
            setLeftSliderTarget(0);
        }
    }
    public void toggleLRotatorMode() {
        varLeftRotatorState =! varLeftRotatorState;
        if (!varLeftRotatorState) {
            setLeftRotatorTarget(rotUpConstant*2);
        } else {
            setLeftRotatorTarget(rotUpConstant);
        }
    }
    public void slidesClimbStages() {

        int maxSlidesClimbState = 5;

        varSlidesClimbState++;

        if (varSlidesClimbState == maxSlidesClimbState + 1) {
            varSlidesClimbState = 0;
        }

        switch (varSlidesClimbState) {
            case 0:
                setDualSliderTarget(1500);
                setDualRotatorTarget(5);
                break;
            case 1:
                setDualSliderTarget(0);
                setDualRotatorTarget(11);
                break;
            case 2:
                setDualSliderTarget(0);
                setDualRotatorTarget(5);
                break;
            case 3:
                setDualSliderTarget(400);
                setDualRotatorTarget(11);
                break;
            case 4:
                setDualSliderTarget(1500);
                setDualRotatorTarget(5);
                break;
            case 5:
                setDualSliderTarget(0);
                setDualRotatorTarget(11);
                break;
            default:
                setDualSliderTarget(0);
                setDualRotatorTarget(5);
                break;
        }
    }
    public void ManualControl(double leftRotPower, double rightRotPower, double leftSlidePower, double rightSlidePower) {
        objLeftRotatorMotor.setPower(leftRotPower);
        objRightRotatorMotor.setPower(rightRotPower);
        objLeftSlidesMotor.setPower(leftSlidePower);
        objRightSlidesMotor.setPower(rightSlidePower);
    }
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
    public int getSlideLPosition() {
        return objLeftSlidesMotor.getCurrentPosition();
    }
    public int getSlideLTargetPosition() {
        return objLeftSlidesMotor.getTargetPosition();
    }
    public int getSlideRPosition() {
        return objRightSlidesMotor.getCurrentPosition();
    }
    public int getSlideRTargetPosition() {
        return objRightSlidesMotor.getTargetPosition();
    }
    public int getRotatorLPosition() {
        return objLeftRotatorMotor.getCurrentPosition();
    }
    public int getRotatorLTargetPosition() {
        return objLeftRotatorMotor.getTargetPosition();
    }
    public int getRotatorRPosition() {
        return objRightRotatorMotor.getCurrentPosition();
    }
    public int getRotatorRTargetPosition() {
        return objRightRotatorMotor.getTargetPosition();
    }
}