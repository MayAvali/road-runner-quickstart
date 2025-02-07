package org.firstinspires.ftc.teamcode.May.lib.subsystems;
import com.qualcomm.robotcore.hardware.Servo;
public class SubmersibleGripperSubsystem {
    public final Servo submersibleGripperServo;
    public final Servo submersibleRotatorServo;
    boolean gripperClosed = true;
    boolean rotatorDown = false;
    public SubmersibleGripperSubsystem(Servo submersibleGripperServo, Servo submersibleRotatorServo) {
        this.submersibleGripperServo = submersibleGripperServo;
        this.submersibleRotatorServo = submersibleRotatorServo;
    }
    public void toggleClamp() {
        gripperClosed = !gripperClosed;
        if (!gripperClosed) {
            submersibleGripperServo.setPosition(0.2);
        } else {
            submersibleGripperServo.setPosition(0);
        }

    }
    public void toggleRotate() {
        rotatorDown = !rotatorDown;
        if (!rotatorDown) {
            submersibleRotatorServo.setPosition(0);
        } else {
            submersibleRotatorServo.setPosition(0.36);
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
