package org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class GripperSubsystem {
    private final Servo objLeftGripperServo;
    private final Servo objLeftRotatorServo;
    private final Servo objRightGripperServo;
    private final Servo objRightRotatorServo;

    boolean leftGripperClosed = false;

    enum threeState {STOWED, OPEN, CLOSED}
    threeState varRightGripperState = threeState.STOWED;


    public GripperSubsystem(Servo leftGServ, Servo leftRServ, Servo rightGServ, Servo rightRServ) {
        objLeftGripperServo = leftGServ;
        objLeftRotatorServo = leftRServ;
        objRightGripperServo = rightGServ;
        objRightRotatorServo = rightRServ;

        objLeftGripperServo.setPosition(0.0);
        objLeftRotatorServo.setPosition(0.0);
        objRightGripperServo.setPosition(0.0);
        objRightRotatorServo.setPosition(0.0);
    }

    public void rightGripperMode() {
        if((varRightGripperState == threeState.STOWED)||(varRightGripperState == threeState.CLOSED))
        {
            varRightGripperState = threeState.OPEN;
        } else {
            varRightGripperState = threeState.CLOSED;
        }
    }
    public void rightGripperPosMove() {
        switch (varRightGripperState) {
            case STOWED:
                objRightGripperServo.setPosition(0.0);
                break;
            case OPEN:
                objRightGripperServo.setPosition(0.1);
                break;
            case CLOSED:
                objRightGripperServo.setPosition(0.15);
                break;
        }
    }
//    public void toggleClamp() {
//        gripperClosed = !gripperClosed;
//        if (!gripperClosed) {
//            gripperServo.setPosition(0.14);
//        } else {
//            gripperServo.setPosition(0.00);
//        }
//    }
//    public void toggleMode() {
//        scoreSample =! scoreSample;
//        if (!rotatorDown) {
//            rotatorServo.setPosition(scoreSample ? 0.45 : 0.36);
//        } else if (gripperClosed) {
//            rotatorServo.setPosition(0.02);
//        }
//    }
//    public void toggleRotate() {
//        rotatorDown = !rotatorDown;
//        if (!rotatorDown && gripperClosed) {
//            rotatorServo.setPosition(scoreSample ? 0.45 : 0.36);
//        } else if (gripperClosed) {
//            rotatorServo.setPosition(0.02);
//        }
//    }
//    public double getGripperPosition() {
//        return gripperServo.getPosition();
//    }
//    public boolean getGripperMode() {
//        return scoreSample;
//    }
//    public boolean getGripperState() {
//        return gripperClosed;
//    }
//    public boolean getRotatorState() {
//        return rotatorDown;
//    }
    public double getLeftGripperPosition() {
        return objLeftGripperServo.getPosition();
    }
    public double getLeftRotatorPosition() {
        return objLeftRotatorServo.getPosition();
    }
    public double getRightGripperPosition() {
        return objRightGripperServo.getPosition();
    }
    public double getRightRotatorPosition() {
        return objRightRotatorServo.getPosition();

    }

}