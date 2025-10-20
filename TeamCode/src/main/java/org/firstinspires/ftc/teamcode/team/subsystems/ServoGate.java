package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoGate {
    public static Servo leftGate;
    public static Servo rightGate;

    static boolean isGateClosed = false;

    public ServoGate(Servo leftGate, Servo rightGate) {
        ServoGate.leftGate = leftGate;
        ServoGate.rightGate = rightGate;
        ServoGate.leftGate.setPosition(0.5);
        ServoGate.rightGate.setPosition(0.5);
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
