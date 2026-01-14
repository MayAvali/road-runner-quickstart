package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoGate {
    public Servo gate;

    static boolean isGateOpen = false;

    public ServoGate(Servo gate) {
        this.gate = gate;
        gate.setPosition(0.5);
    }

    public void toggleGate() {
        if (isGateOpen) {
            isGateOpen = false;
            gate.setPosition(0.18);
        } else {
            isGateOpen = true;
            gate.setPosition(0.5);
        }
    }
    public void openGate() {
        isGateOpen = true;
        gate.setPosition(0.22);
    }
    public void closeGate() {
        isGateOpen = false;
        gate.setPosition(0.4);
    }
}
