package org.firstinspires.ftc.teamcode.May.lib.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

//Rotator Range: 0.02(down) - 0.35(front)
//Gripper Range: 0(closed) - 0.075(open)
public class PrimaryGripperSubsystem {
    public final Servo primaryGripperServo;
    public final Servo primaryRotatorServo;

    boolean gripperClosed = true;

    boolean scoreSample = false;

    boolean rotatorDown = false;

    public PrimaryGripperSubsystem(Servo primaryGripperServo, Servo primaryRotatorServo) {
        this.primaryGripperServo = primaryGripperServo;
        this.primaryRotatorServo = primaryRotatorServo;
        primaryRotatorServo.setPosition(0.36);
        primaryGripperServo.setPosition(0.12);
    }

    public void toggleClamp() {
        gripperClosed = !gripperClosed;
        if (!gripperClosed) {
            primaryGripperServo.setPosition(0.22);
        } else {
            primaryGripperServo.setPosition(0.12);
        }

    }
    public void toggleMode() {
        scoreSample  = !scoreSample;
        if (!rotatorDown) {
            primaryRotatorServo.setPosition(scoreSample ? 0.45 : 0.36);
        } else {
            primaryRotatorServo.setPosition(scoreSample ? 0.02 : 0.24);
        }
    }
    public void toggleRotate() {
        rotatorDown = !rotatorDown;
        if (!rotatorDown) {
            primaryRotatorServo.setPosition(scoreSample ? 0.45 : 0.36);
        } else {
            primaryRotatorServo.setPosition(scoreSample ? 0.02 : 0.24);
        }

    }
    public double getGripperPosition() {
        return primaryGripperServo.getPosition();
    }
    public boolean getGripperMode() {
        return scoreSample;
    }
    public boolean getGripperState() {
        return gripperClosed;
    }
    public boolean getRotatorState() {
        return rotatorDown;
    }
}