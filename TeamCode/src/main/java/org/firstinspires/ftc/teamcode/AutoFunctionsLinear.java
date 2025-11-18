package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.Map;
import java.util.HashMap;
//import com.qualcomm.robotcore.hardware.ColorSensor;

public abstract class AutoFunctionsLinear extends LinearOpMode {
    protected DcMotor back_left;
    protected DcMotor back_right;
    protected DcMotor front_left;
    protected DcMotor front_right;

    protected Map<Integer, String> patterns;
    protected DcMotor intake;
    protected DcMotorEx intake2;
    protected DcMotorEx shooter;

    //protected ColorSensor color_sensor;
    Limelight3A limelight;
    @Override
    public void runOpMode() throws InterruptedException {

        patterns = new HashMap<>();
        patterns.put(21,"gpp");
        patterns.put(22,"pgp");
        patterns.put(23,"ppg");
        patterns.put(20,"blue");
        patterns.put(24,"red");


        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        limelight = hardwareMap.get(Limelight3A.class, "limeLight");
        limelight.pipelineSwitch(0);

        back_left = hardwareMap.get(DcMotor.class, "back_left_motor") ;
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        back_right = hardwareMap.get(DcMotor.class, "back_right_motor") ;
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        front_left = hardwareMap.get(DcMotor.class, "front_left_motor") ;
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        front_right = hardwareMap.get(DcMotor.class, "front_right_motor") ;
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setDirection(DcMotor.Direction.REVERSE);

        intake = hardwareMap.get(DcMotor.class, "intake") ;
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake2 = hardwareMap.get(DcMotorEx.class, "intake2") ;
        intake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooter = hardwareMap.get(DcMotorEx.class, "shooter") ;
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //color_sensor = hardwareMap.get(ColorSensor.class, "color_sensor");
    }

    protected void drive(float back_left_power, float back_right_power, float front_left_power, float front_right_power) {
        back_left.setPower(back_left_power);
        back_right.setPower(back_right_power);
        front_left.setPower(front_left_power);
        front_right.setPower(front_right_power);
    }

    protected void stopAndResetAll() {
        back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    protected void runToPosition(){
        back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected void drive_distance(int distance) {
        stopAndResetAll();

        back_left.setTargetPosition(distance);
        back_right.setTargetPosition(distance);
        front_left.setTargetPosition(distance);
        front_right.setTargetPosition(distance);

        runToPosition();


        runUntilFinished();
    }

    protected void runUntilFinished() {
        drive(.25f,.25f,.25f,.25f);

        while(back_left.isBusy() || back_right.isBusy() || front_left.isBusy() || front_right.isBusy()) {
        }

        drive(0,0,0,0);
    }

    protected void rotate_degree(int degree) {
        stopAndResetAll();

        back_left.setTargetPosition(degree);
        back_right.setTargetPosition(-degree);
        front_left.setTargetPosition(degree);
        front_right.setTargetPosition(-degree);

        runToPosition();

        runUntilFinished();
    }

    protected void startIntake() {
        intake.setPower(-1);
        intake2.setPower(-1);
        //colorSense();
    }

    protected void stopIntake() {
        intake.setPower(0);
        intake2.setPower(0);
    }

    protected void shoot() throws InterruptedException {
        /*shooter.setPower(-1);
        Thread.sleep(1500);
        intake2.setPower(-1);
        Thread.sleep(1500);
        shooter.setPower(0);
        intake2.setPower(0);
        */
        waitForStart();
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int leftTarget = (int)(610 * 200);
        int rightTarget = (int)(610 * 200);
        double TPS = (175/60) * 200;

        waitForStart();
        shooter.setTargetPosition(leftTarget);
        intake2.setTargetPosition(rightTarget);

        shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shooter.setVelocity(10000);
        intake2.setVelocity(10000);

        while (opModeIsActive() && (shooter.isBusy() && intake2.isBusy())){

        }
    }
    /*
    protected void shoot_with_velocity()throws InterruptedException{

    } */

    // protected String colorSense() {
    //     int purple = (color_sensor.red() + color_sensor.blue())/2;
    //     int green = color_sensor.green();
    //     if ((purple>=130) && (green>=150))
    //     {
    //         return "white";
    //     }
    //     else if (purple > green)
    //     {
    //         return "purple";
    //     }
    //     else if (green > purple)
    //     {
    //         return "green";
    //     }

    //     return "white";
    //     }
}
