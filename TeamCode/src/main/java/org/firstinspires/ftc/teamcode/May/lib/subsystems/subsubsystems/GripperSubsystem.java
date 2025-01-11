package org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems;

import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.Servo;
//Rotator Range: 0.02(down) - 0.35(front)
//Gripper Range: 0(closed) - 0.075(open)
public class GripperSubsystem {
    public final Servo gripperServo;
    public final Servo rotatorServo;

    boolean gripperClosed = true;

    boolean scoreSample = false;

    boolean rotatorDown = false;

    public GripperSubsystem(Servo gServ, Servo rServ) {
        gripperServo = gServ;
        rotatorServo = rServ;
        rotatorServo.setPosition(0.36);
        gripperServo.setPosition(0.00);
    }

    public void toggleClamp() {
        gripperClosed = !gripperClosed;
        if (!gripperClosed) {
            gripperServo.setPosition(0.14);
        } else {
            gripperServo.setPosition(0.00);
        }
    }

    public void RRtoggleClamp() {
        gripperClosed = !gripperClosed;
        if (!gripperClosed) {
            gripperServo.setPosition(0.14);
        } else {
            gripperServo.setPosition(0.00);
        }
    }

    public void toggleMode() {
        scoreSample = !scoreSample;
        if (!rotatorDown) {
            rotatorServo.setPosition(scoreSample ? 0.45 : 0.36);
        } else if (gripperClosed) {
            rotatorServo.setPosition(0.02);
        }
    }

    public void toggleRotate() {
        rotatorDown = !rotatorDown;
        if (!rotatorDown && gripperClosed) {
            rotatorServo.setPosition(scoreSample ? 0.45 : 0.36);
        } else if (gripperClosed) {
            rotatorServo.setPosition(0.02);
        }
    }

    public double getGripperPosition() {
        return gripperServo.getPosition();
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