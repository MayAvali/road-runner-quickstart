package org.firstinspires.ftc.teamcode.team.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class servoGate {
    public final Servo leftGate;
    public final Servo rightGate;

    boolean isGateClosed = false;

    public servoGate(Servo leftGate, Servo rightGate) {
        this.leftGate = leftGate;
        this.rightGate = rightGate;
        this.leftGate.setPosition(0.5);
        this.rightGate.setPosition(0.5);
    }

    public void toggleGate() {
        if (isGateClosed) {
            isGateClosed = false;
            this.leftGate.setPosition(0.18);
            this.rightGate.setPosition(0.84);
        } else {
            isGateClosed = true;
            this.leftGate.setPosition(0.5);
            this.rightGate.setPosition(0.5);
        }
    }
}
