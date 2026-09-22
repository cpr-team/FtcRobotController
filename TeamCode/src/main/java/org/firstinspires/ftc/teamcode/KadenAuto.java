package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "KadenAuto")
public class KadenAuto extends PracticeAFL {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        waitForStart();

        drive_distance(2700);
        rotate_degree(-1550);
        drive_distance(3500);
        rotate_degree(1250);
        drive_distance(3500);
        rotate_degree(-1450);
        drive_distance(4200);
        rotate_degree(-900);
        drive_distance(4000);
        rotate_degree(-900);
        drive_distance(2000);

        //dance, dance revolution
    }
}
