package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AutoFunctionsLinear;

@Autonomous (name = "goal")
public class goal extends AutoFunctionsLinear {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        Integer direction = -1;
        waitForStart();
//2100
        drive_distance(-1580);
        sleep(3000);
        shoot();
        sleep(3000);
        rotate_degree(40);
        sleep(3000);
        startIntake();
        sleep(3000);
        drive_distance(1000);
        sleep(3000);
        stopIntake();

//
//        //rotateDegrees(direction * 290);
//        drive_distance(-330);
//
//        //shoot();
//        drive_distance(330);
//
//        rotateDegrees(direction * 290);
//
//        //drive while intake
//        startIntake();
//        drive_distance(860);
//        stopIntake();
//
//        drive_distance(-860);
//
//        //rotateDegrees(direction * -290);
//        drive_distance(-300);

        //shoot();

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
