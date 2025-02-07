package org.firstinspires.ftc.teamcode.May.lib.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;

public class PrimarySlidesSubsystem {
    public final DcMotor primarySlideMotor;

    boolean scoreSample = false;
    boolean sliderState = false;
    public PrimarySlidesSubsystem(DcMotor primarySlideMotor) {

        this.primarySlideMotor = primarySlideMotor;

        primarySlideMotor.setDirection(DcMotor.Direction.REVERSE);
        primarySlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        primarySlideMotor.setTargetPosition(scoreSample ? 0 : 200);
        primarySlideMotor.setPower(1);
        primarySlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setSliderTarget(int p) {
        primarySlideMotor.setTargetPosition(p);
    }
    public void toggleMode() {
        scoreSample =! scoreSample;
        if (sliderState) {
            setSliderTarget(scoreSample ? 4400 : 1950); //if scoring sample, set to 1st var, else 2nd var
        } else {
            setSliderTarget(scoreSample ? 0 : 200);
        }
    }
    public void dispenseSpecimen() {
        if (!scoreSample) {
            setSliderTarget(1500);
            sliderState = true;
        }
    }

    public void decline() {
        setSliderTarget(getSlideTargetPosition() - 5);
    }
    public void togglePos() {
        sliderState =! sliderState;
        if (!sliderState) {
            setSliderTarget(scoreSample ? 0 : 200);
        } else {
            setSliderTarget(scoreSample ? 4400 : 1950); //if scoring sample, set to 1st var, else 2nd var
        }
    }
    public int getSlidePosition() {
        return primarySlideMotor.getCurrentPosition();
    }
    public int getSlideTargetPosition() {
        return primarySlideMotor.getTargetPosition();
    }
    public boolean getSlideMode() {
        return scoreSample;
    }
    public boolean getSlideState() {
        return sliderState;
    }
}