package org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class SlidesSubsystem {

    private final DcMotor objLeftSlidesMotor;
    private final DcMotor objLeftRotatorMotor;
    private final DcMotor objRightSlidesMotor;
    private final DcMotor objRightRotatorMotor;
    private final int rotUpConstant = 200;
    private final double slidePowConstant = 0.5;
    private final double rotPowConstant = 0.5;
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

        objLeftSlidesMotor.setTargetPosition(0);
        objLeftSlidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        objLeftSlidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        objLeftSlidesMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        objLeftSlidesMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        objLeftSlidesMotor.setPower(slidePowConstant);


        objLeftRotatorMotor.setTargetPosition(0);
        objLeftRotatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        objLeftRotatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        objLeftRotatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        objLeftRotatorMotor.setPower(rotPowConstant);

        objRightSlidesMotor.setTargetPosition(0);
        objRightSlidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        objRightSlidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        objRightSlidesMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        objRightSlidesMotor.setPower(slidePowConstant);

        objRightRotatorMotor.setTargetPosition(0);
        objRightRotatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        objRightRotatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        objRightRotatorMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        objRightRotatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        objRightRotatorMotor.setPower(rotPowConstant);
    }
    public void setLeftSliderTarget(int p) {
        objLeftSlidesMotor.setTargetPosition(p);
    }
    public void setLeftRotatorTarget(int p) {
        objLeftRotatorMotor.setTargetPosition(p);
    }

    public void setRightSliderTarget(int p) {
        objRightSlidesMotor.setTargetPosition(p);
    }
    public void setRightRotatorTarget(int p) {
        objRightRotatorMotor.setTargetPosition(p);
    }

    public void setDualSliderTarget(int p) {
        objLeftSlidesMotor.setTargetPosition(p);
        objRightSlidesMotor.setTargetPosition(p);
    }

    public void setDualRotatorTarget(int p) {
        objLeftRotatorMotor.setTargetPosition(p);
        objRightRotatorMotor.setTargetPosition(p);
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
                setDualSliderTarget(0);
                setDualRotatorTarget(50);
                break;
            case 1:
                setDualSliderTarget(2000);
                setDualRotatorTarget(220);
                break;
            case 2:
                setDualSliderTarget(0);
                setDualRotatorTarget(0);
                break;
            case 3:
                setDualSliderTarget(2000);
                setDualRotatorTarget(220);
                break;
            case 4:
                setDualSliderTarget(0);
                setDualRotatorTarget(0);
                break;
            case 5:
                setDualSliderTarget(0);
                setDualRotatorTarget(110);
                break;
            default:
                setDualSliderTarget(0);
                setDualRotatorTarget(50);
                break;
        }
    }
    public void ManualControl(double RotPos, double SlidePos) {
        setDualRotatorTarget((int)RotPos*400);
        setDualSliderTarget((int)SlidePos*3000);
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
    public double getSlideLPower() {
        return objLeftSlidesMotor.getPower();
    }
    public double getSlideRPower() {
        return objRightSlidesMotor.getPower();
    }
    public double getRotatorLPower() {
        return objLeftRotatorMotor.getPower();
    }
    public double getRotatorRPower() {
        return objRightRotatorMotor.getPower();
    }
}