package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "ChanceAuto")
public class ChanceAuto extends PracticeAFL{

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        waitForStart();
       drive_distance(5000);
       sleep(5000);




    }
}
