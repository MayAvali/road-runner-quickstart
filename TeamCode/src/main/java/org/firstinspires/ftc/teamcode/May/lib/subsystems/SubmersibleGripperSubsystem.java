package org.firstinspires.ftc.teamcode.May.lib.subsystems;
import com.qualcomm.robotcore.hardware.Servo;
public class SubmersibleGripperSubsystem {
    public Servo submersibleGripperServo;
    public Servo submersibleRotatorServo;
    boolean gripperClosed = true;
    boolean rotatorDown = false;
    public SubmersibleGripperSubsystem(Servo submersibleGripperServo, Servo submersibleRotatorServo) {
        this.submersibleGripperServo = submersibleGripperServo;
        this.submersibleRotatorServo = submersibleRotatorServo;
        submersibleRotatorServo.setPosition(0.05);
        submersibleGripperServo.setPosition(0.12);
    }
    public void toggleClamp() {
        gripperClosed = !gripperClosed;
        if (!gripperClosed) {
            submersibleGripperServo.setPosition(0.3);
        } else {
            submersibleGripperServo.setPosition(0.12);
        }

    }
    public void toggleRotate() {
        rotatorDown = !rotatorDown;
        if (rotatorDown) {
            submersibleRotatorServo.setPosition(0.85);
        } else {
            submersibleRotatorServo.setPosition(0.05);
        }
    }
    public double getGripperPosition() {
        return submersibleGripperServo.getPosition();
    }
    public boolean getGripperState() {
        return gripperClosed;
    }
    public double getRotatorPosition() {
        return submersibleRotatorServo.getPosition();
    }
    public boolean getRotatorState() {
        return rotatorDown;
    }
}
