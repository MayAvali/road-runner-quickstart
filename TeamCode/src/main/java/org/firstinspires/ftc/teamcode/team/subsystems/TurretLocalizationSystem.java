package org.firstinspires.ftc.teamcode.team.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.R;

public class TurretLocalizationSystem {
    public static double getDistance(Pose2D Robot, Pose2D Target) {
        double RobotX = Robot.getX(DistanceUnit.CM);
        double RobotY = Robot.getY(DistanceUnit.CM);
        double TargetX = Target.getX(DistanceUnit.CM);
        double TargetY = Target.getY(DistanceUnit.CM);

        return Math.sqrt((TargetX - RobotX) * (TargetX - RobotX) + (TargetY - RobotY) * (TargetY - RobotY));
    }

    public static double getAngle(Pose2D Robot, Pose2D Target) {
        double RobotX = Robot.getX(DistanceUnit.CM);
        double RobotY = Robot.getY(DistanceUnit.CM);
        double TargetX = Target.getX(DistanceUnit.CM);
        double TargetY = Target.getY(DistanceUnit.CM);

        double RobotHeading = (Math.toDegrees(Robot.getHeading(AngleUnit.RADIANS)));

        double BotAngle = Math.toDegrees(Math.atan2((TargetY - RobotY), (TargetX - RobotX)));

        return modulo(BotAngle - RobotHeading,180);
    }
    public static double modulo(double dividend, double divisor) {
        double remainder = dividend % divisor;
        if ((remainder < 0 && divisor > 0) || (remainder > 0 && divisor < 0)) {
            remainder += divisor;
        }
        return remainder;
    }
}
