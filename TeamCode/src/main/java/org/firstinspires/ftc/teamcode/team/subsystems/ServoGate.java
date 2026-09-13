package org.firstinspires.ftc.teamcode.team.subsystems;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.InstantFunction;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoGate {
    public Servo gate;

    static boolean isGateOpen = false;

    public ServoGate(Servo gate) {
        this.gate = gate;
    }
    private double lastPosition = 0.0;

    public void openGate() {
        if (!isGateOpen){
            isGateOpen = true;
            gate.setPosition(0.22);
        }
    }

    public Action openGateAction() {
        return new InstantAction(
                this::openGate
        );
    }
    public void closeGate() {
        if (isGateOpen) {
            isGateOpen = false;
            gate.setPosition(0.4);
        }
    }

    public Action closeGateAction() {
        return new InstantAction(
                this::closeGate
        );
    }
}
