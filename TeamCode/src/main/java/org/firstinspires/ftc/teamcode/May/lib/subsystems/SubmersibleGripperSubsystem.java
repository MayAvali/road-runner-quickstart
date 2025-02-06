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
}
