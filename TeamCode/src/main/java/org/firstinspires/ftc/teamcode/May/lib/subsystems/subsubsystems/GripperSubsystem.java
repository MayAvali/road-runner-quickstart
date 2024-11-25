package org.firstinspires.ftc.teamcode.May.lib.subsystems.subsubsystems;

import com.qualcomm.robotcore.hardware.Servo;
public class GripperSubsystem {
    private final Servo leftGripperServo;
    private final Servo leftRotatorServo;
    private final Servo rightGripperServo;
    private final Servo rightRotatorServo;

    boolean gripperClosed = false;

    public GripperSubsystem(Servo leftGServ, Servo leftRServ, Servo rightGServ, Servo rightRServ) {
        leftGripperServo = leftGServ;
        leftRotatorServo = leftRServ;
        rightGripperServo = rightGServ;
        rightRotatorServo = rightRServ;

        leftGripperServo.setPosition(0.0);
        leftRotatorServo.setPosition(0.0);
        rightGripperServo.setPosition(0.0);
        rightRotatorServo.setPosition(0.0);
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
        return leftGripperServo.getPosition();
    }
    public double getLeftRotatorPosition() {
        return leftRotatorServo.getPosition();
    }
    public double getRightGripperPosition() {
        return rightGripperServo.getPosition();
    }
    public double getRightRotatorPosition() {
        return rightRotatorServo.getPosition();

    }

}