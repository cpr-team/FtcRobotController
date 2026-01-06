package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AutoFunctionsLinear;

@Autonomous (name = "LongShootAuto")
public class LongShootAuto extends AutoFunctionsLinear {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        Integer direction = -1;
        waitForStart();
//2100
        LongShoot();
    }}
