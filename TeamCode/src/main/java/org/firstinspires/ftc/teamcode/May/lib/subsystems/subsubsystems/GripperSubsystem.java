package org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class GripperSubsystem {
    private final Servo objLeftGripperServo;
    private final Servo objLeftRotatorServo;
    private final Servo objRightGripperServo;
    private final Servo objRightRotatorServo;
    boolean leftGripperClosed = false;
    boolean leftGripperFront = false;
    enum ThreeStateRightGripper {STOWED, OPEN, CLOSED}
    ThreeStateRightGripper varRightGripperState = ThreeStateRightGripper.STOWED;
    int varRightRotatorState = 1;
    public GripperSubsystem(Servo leftGServ, Servo leftRServ, Servo rightGServ, Servo rightRServ) {
        objLeftGripperServo = leftGServ;
        objLeftRotatorServo = leftRServ;
        objRightGripperServo = rightGServ;
        objRightRotatorServo = rightRServ;

        objLeftGripperServo.setPosition(0.1);
        objLeftRotatorServo.setPosition(0.0);
        objRightGripperServo.setPosition(0.0);
        objRightRotatorServo.setPosition(0.0);
    }
    public void rightGripperClampCall() {
        if((varRightGripperState == ThreeStateRightGripper.STOWED)||(varRightGripperState == ThreeStateRightGripper.CLOSED))
        {
            varRightGripperState = ThreeStateRightGripper.OPEN;
            rightGripperClampMove();
        } else {
            varRightGripperState = ThreeStateRightGripper.CLOSED;
            rightGripperClampMove();
        }
    }
    public void rightGripperRotateCall() {
        if (varRightRotatorState == 3) {
            varRightRotatorState = 1;
            rightGripperRotateMove();
        } else {
            varRightRotatorState++;
            rightGripperRotateMove();
        }
    }
    public void rightGripperClampMove() {
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
    public void rightGripperRotateMove() {
        switch (varRightRotatorState) {
            case 1:
                objRightRotatorServo.setPosition(0.0);
                break;
            case 2:
                objRightRotatorServo.setPosition(0.5);
                break;
            case 3:
                objRightRotatorServo.setPosition(1);
                break;
        }
    }

    public void leftGripperClamp(){
        leftGripperClosed = !leftGripperClosed;
        if (!leftGripperClosed) {
            objLeftGripperServo.setPosition(0.14);
        } else {
            objLeftGripperServo.setPosition(0.00);
        }
    }
    public void leftGripperRotate() {
        leftGripperFront = !leftGripperFront;
        if (!leftGripperFront) {
            objLeftRotatorServo.setPosition(1);
        } else {
            objLeftRotatorServo.setPosition(0.85);
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
    public void setLeftRotatorTarget(double target) {
        objLeftRotatorServo.setPosition(target);
    }
    public void setRightRotatorTarget(double target) {
        objRightRotatorServo.setPosition(target);
    }
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
//Huzzington.start;