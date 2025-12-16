package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AutoFunctionsLinear;

@Autonomous (name = "AutoRed")
public class AutoRed extends AutoFunctionsLinear {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        Integer direction = 1;
        waitForStart();

        drive_distance(2100);

        rotateDegrees(direction * 305);
        drive_distance(-130);

        shoot();
        drive_distance(130);

        rotateDegrees(direction * 305);

        //drive while intake
        startIntake();
        drive_distance(890);
        stopIntake();

        drive_distance(-890);

        rotateDegrees(direction * -305);
        drive_distance(-100);

        shoot();

        // rotate_degree(2000);

        // drive_distance(3200);

        // rotate_degree(-950);

        // startIntake();
        // drive_distance(1470);
        // stopIntake();

        // drive_distance(-1470);

        // rotate_degree(950);

        // drive_distance(-3200);

        // rotate_degree(-2000);

        // shoot();

        // rotate_degree(2500);

        // drive_distance(4800);

        // rotate_degree(-1450);

        // startIntake();
        // drive_distance(1800);
        // stopIntake();

        // drive_distance(-1800);

        // rotate_degree(1450);

        // drive_distance(-4800);

        // rotate_degree(-2500);

        // shoot();

        // rotate_degree(-1050);

        // drive_distance(-6600);
    }
}
