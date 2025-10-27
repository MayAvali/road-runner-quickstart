package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoGate {
    public Servo leftGate;
    public Servo rightGate;

    static boolean isGateOpen = false;

    public ServoGate(Servo leftGate, Servo rightGate) {
        this.leftGate = leftGate;
        this.rightGate = rightGate;
        leftGate.setPosition(0.5);
        rightGate.setPosition(0.5);
    }

    public void toggleGate() {
        if (isGateOpen) {
            isGateOpen = false;
            leftGate.setPosition(0.18);
            rightGate.setPosition(0.84);
        } else {
            isGateOpen = true;
            leftGate.setPosition(0.5);
            rightGate.setPosition(0.5);
        }
    }
    public void openGate() {
        isGateOpen = true;
        leftGate.setPosition(0.4);
        rightGate.setPosition(0.6);
    }
    public void closeGate() {
        isGateOpen = false;
        leftGate.setPosition(0.22);
        rightGate.setPosition(0.80);
    }
}
