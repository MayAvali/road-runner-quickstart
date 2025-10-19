package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class servoGate {
    public static Servo leftGate;
    public static Servo rightGate;

    static boolean isGateClosed = false;

    public servoGate(Servo leftGate, Servo rightGate) {
        servoGate.leftGate = leftGate;
        servoGate.rightGate = rightGate;
        servoGate.leftGate.setPosition(0.5);
        servoGate.rightGate.setPosition(0.5);
    }

    public static void toggleGate() {
        if (isGateClosed) {
            isGateClosed = false;
            leftGate.setPosition(0.18);
            rightGate.setPosition(0.84);
        } else {
            isGateClosed = true;
            leftGate.setPosition(0.5);
            rightGate.setPosition(0.5);
        }
    }
}
