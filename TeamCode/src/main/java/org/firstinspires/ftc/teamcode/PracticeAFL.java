package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
//import com.qualcomm.robotcore.hardware.ColorSensor;

public abstract class PracticeAFL extends LinearOpMode {
    protected DcMotor back_left;
    protected DcMotor back_right;
    protected DcMotor front_left;
    protected DcMotor front_right;
    //protected DcMotorEx sorter;
    protected Map<Integer, Integer> patterns;
    //protected DcMotor intake;
    protected DcMotorEx intake2;
    //protected DcMotorEx shooter;

    protected DcMotor fan;
    //protected DcMotorEx shooter2;
    //protected ColorSensor color_sensor;
    protected boolean intakeMode = true;

    protected int degree_count = 0;

    //protected Servo kicker;
    protected Limelight3A limelight;
    //protected NormalizedColorSensor color;
    protected String[] ballPos;
    protected boolean sensing = false;

    private double distance;
    @Override
    public void runOpMode() throws InterruptedException {
        //setPattern();

        patterns = new HashMap<>();
        patterns.put(21, 2);
        patterns.put(22, 0);
        patterns.put(23, 1);



        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        limelight = hardwareMap.get(Limelight3A.class, "limeLight");
        limelight.pipelineSwitch(0);
        limelight.start();
        back_left = hardwareMap.get(DcMotor.class, "back_left_motor") ;
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        back_right = hardwareMap.get(DcMotor.class, "back_right_motor") ;
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_left = hardwareMap.get(DcMotor.class, "front_left_motor") ;
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        front_right = hardwareMap.get(DcMotor.class, "front_right_motor") ;
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setDirection(DcMotor.Direction.REVERSE);

        //intake = hardwareMap.get(DcMotor.class, "intake") ;
        //intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        //shooter = hardwareMap.get(DcMotorEx.class, "shooter") ;
        //shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //shooter2 = hardwareMap.get(DcMotorEx.class, "shooter2");
        //shooter2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //sorter = hardwareMap.get(DcMotorEx.class, "sorter") ;
        //sorter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        //sorter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //kicker = hardwareMap.get(Servo.class, "kicker");



        //color = hardwareMap.get(NormalizedColorSensor.class, "color_sensor");
        //ballPos = new String[3];

    }
    int currentPattern;
    public void setPattern()
    {
        LLResult result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> obelisk = result.getFiducialResults();


        if (!obelisk.isEmpty()) {

            currentPattern = patterns.get(obelisk.get(0).getFiducialId());
            telemetry.addData("attemting to set pattern", currentPattern);
            telemetry.update();
        }
    }

    public int getId(){
        LLResult result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> AprilTag = result.getFiducialResults();


        if (!AprilTag.isEmpty()) {

            return AprilTag.get(0).getFiducialId();

        }

        return 0;
    }
    protected void sort()
    {


        //int sortpos = sorter.getCurrentPosition();
        //int target = sortpos +240;
        //sorter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //sorter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        //sorter.setTargetPosition(target);
        //sorter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        //sorter.setPower(1);
        telemetry.addData("sorting","yes");
        //telemetry.addData("sorty", sortpos);
        //telemetry.addData("target",target);
        //telemetry.addData("sortpower", sorter.getPower());
        telemetry.update();
        //if (sortpos == target){
        //    sorter.setPower(0);
        //}

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

        back_left.setTargetPosition(-distance);
        back_right.setTargetPosition(distance);
        front_left.setTargetPosition(distance);
        front_right.setTargetPosition(-distance);

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
    protected void rotateDegrees(int degrees) {


        //int sortpos = sorter.getCurrentPosition();

        //int ticks = 824 * degrees / 360;
        //sorter.setTargetPosition(degrees/*sorter.getCurrentPosition() + ticks*/);
        //sorter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        //sorter.setPower(1);

        // degree_count += degrees;

        //while (sorter.isBusy()) {

        //}
    }

    protected void rotate2() throws InterruptedException{
        //sorter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        //sorter.setTargetPosition(270);
        //sorter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        //sorter.setPower(1);
        sleep(2000);
        //sorter.setTargetPosition(540);
        //sorter.setPower(1);
        sleep(2000);
        //sorter.setTargetPosition(810);
        //sorter.setPower(1);
        sleep(2000);
        //sorter.setTargetPosition(0);
        //sorter.setPower(1);

    }
    protected void startIntake() {
        //intake.setPower(-1);

        //colorSense();
    }

    protected void stopIntake() {
        //intake.setPower(0);

    }

    protected void reverseIntake() {
        //intake.setPower(1);
    }
    protected void assistedShoot() throws InterruptedException {
        //kill();
        LLResult result = limelight.getLatestResult();
        if(result.getTx() == 0){
            return;
        }

        if(patterns.containsKey(getId())){
            return;
        }
        telemetry.addData("tx", result.getTx() );
        telemetry.update();
        while(Math.abs(result.getTx()) > 5  ) {

//        if(result.getTa() > 0.6) {
//            drive(-0.2f,-0.2f,-0.2f,-0.2f);
//            telemetry.addData("backing", "up");
//            telemetry.update();
//
//
//        }
//        if(result.getTa() < 0.5){
//            drive(0.2f, 0.2f, 0.2f, 0.2f);
//            telemetry.addData("moving", "forward");
//            telemetry.update();
//        }

            if(result.getTx() > 0){
                drive(-0.2f,0.2f,-0.2f,0.2f);


            }
            else{
                drive(0.2f,-0.2f,0.2f,-0.2f);


            }
            result = limelight.getLatestResult();

        }
        drive(0,0,0,0);
        if (intakeMode){
            rotateDegrees(120);
            intakeMode = false;
        }
        //shooter.setPower(-1);
        //shooter2.setPower(1);
        //Thread.sleep(3000);
        //shooter.setVelocity(-1500);
        //shooter2.setVelocity(1500);
        //Thread.sleep(3500);
        //kicker.setPosition(0);
        //Thread.sleep(700);
        //kicker.setPosition(1);
        //Thread.sleep(700);
        //rotateDegrees(120);
        //Thread.sleep(700);
        //kicker.setPosition(0);
        //Thread.sleep(700);
        //kicker.setPosition(1);
        //Thread.sleep(700);
        //rotateDegrees(120);
        //Thread.sleep(700);
        //kicker.setPosition(0);
        //Thread.sleep(700);
        //kicker.setPosition(1);
        //Thread.sleep(700);
        //intakeMode = true;
        //rotateDegrees(120);
        //shooter.setVelocity(0);
        //shooter2.setVelocity(0);
        //sorter.setTargetPosition(0);
        //sorter.setPower(1);

        //ballPos = new String[3];
        //while (sorter.isBusy()) {

        //}
    }
    protected void shoot() throws InterruptedException {
        if (intakeMode){
            rotateDegrees(60);
            intakeMode = false;
        }

        //shooter.setPower(-1);
        //shooter2.setPower(1);
        //Thread.sleep(3000);
        //shooter.setVelocity(-1300);
        //shooter2.setVelocity(1300);
        Thread.sleep(3500);
        //kicker.setPosition(0);
        Thread.sleep(700);
        //kicker.setPosition(1);
        Thread.sleep(700);
        rotateDegrees(120);
        Thread.sleep(700);
        //kicker.setPosition(0);
        Thread.sleep(700);
        //kicker.setPosition(1);
        Thread.sleep(700);
        rotateDegrees(120);
        Thread.sleep(700);
        //kicker.setPosition(0);
        Thread.sleep(700);
        //kicker.setPosition(1);
        Thread.sleep(700);
        intakeMode = true;
        rotateDegrees(60);
        //shooter.setVelocity(0);
        //shooter2.setVelocity(0);
        //sorter.setTargetPosition(0);
        //sorter.setPower(1);

        ballPos = new String[3];
        //while (sorter.isBusy()) {

        //}
    }
    /*protected void LongShoot() throws InterruptedException {
        //kill();
        if (intakeMode){
            rotateDegrees(60);
            intakeMode = false;
        }
        shooter.setPower(-1);
        Thread.sleep(3000);
        shooter.setPower(0);
        shooter.setVelocity(-20);
        telemetry.addData("velocity", shooter.getVelocity());
        telemetry.update();
        Thread.sleep(3500);
        shooter.setVelocity(-20);
        kicker.setPosition(0);
        Thread.sleep(1500);
        kicker.setPosition(1);
        Thread.sleep(2000);
        rotateDegrees(120);
        Thread.sleep(1500);
        shooter.setVelocity(-20);
        kicker.setPosition(0);
        Thread.sleep(1000);
        kicker.setPosition(1);
        Thread.sleep(1500);
        rotateDegrees(120);
        Thread.sleep(1500);
        shooter.setVelocity(-20);
        kicker.setPosition(0);
        Thread.sleep(1500);
        kicker.setPosition(1);
        Thread.sleep(800);
        intakeMode = true;
        rotateDegrees(60);
        shooter.setVelocity(0);
    }

     */
    //public String colorSense() {
    //NormalizedRGBA colors = color.getNormalizedColors();

//        float normalizedRed = colors.red / colors.alpha;
//        float normalizedBlue = colors.blue / colors.alpha;
//        float normalizedGreen = colors.green / colors.alpha;

    //Float normalizedRedGreenRatio = normalizedRed / normalizedGreen;
//        if (normalizedRedGreenRatio > .8f && normalizedRedGreenRatio < 1.2f) {
//            telemetry.addData("color", "purple");
//            rotateDegrees(120);
//            return "purple";
//        } else if (normalizedGreen > 1.2f) {
//            telemetry.addData("color", "green");
//            rotateDegrees(120);
//            return "green";
//        } else {
//            telemetry.addData("color", "black");
//            return "black";
//        }

    //telemetry.update();

    //}
    public void rotate(String direction) {
        if (direction.equals("right")) {
            rotateDegrees(120);
            String[] newBallPos = new String[3];
            for (int i = 0; i < ballPos.length; i++) {
                newBallPos[(i + 1) % 3] = ballPos[i];
            }
            ballPos = newBallPos;
        }
        else {
            rotateDegrees(-120);
            String[] newBallPos = new String[3];
            for (int i = 0; i < ballPos.length; i++) {
                newBallPos[i] = ballPos[(i + 1)%3];
            }
            ballPos = newBallPos;
        }

    }
    /*protected void shoot() throws InterruptedException {
        /*shooter.setPower(-1);
        Thread.sleep(1500);
        intake2.setPower(-1);
        Thread.sleep(1500);
        shooter.setPower(0);
        intake2.setPower(0);

        waitForStart();
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();


        shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shooter.setVelocity(10000);
        intake2.setVelocity(10000);

        while (opModeIsActive() && (shooter.isBusy() && intake2.isBusy())){

        }
    }*/
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
