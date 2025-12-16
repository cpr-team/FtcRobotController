package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Teleop;

/**
 *
 * This file contains a minimal example of an iterative (Non-Linear) "OpMode". An OpMode is a
 * 'program' that runs in either the autonomous or the TeleOp period of an FTC match. The names
 * of OpModes appear on the menu of the FTC Driver Station. When an selection is made from the
 * menu, the corresponding OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@TeleOp
public class TeleopLinear extends LinearOpMode {


    protected DcMotor back_left;

    protected DcMotor back_right;

    protected DcMotor front_left;

    protected DcMotor front_right;

    protected DcMotor intake;

    protected DcMotorEx sorter;

    protected DcMotorEx shooter;
    //private limelight3A limelight;
    protected NormalizedColorSensor color;
    protected boolean intakeMode = true;
    protected Servo kicker;
    protected int degree_count = 0;


    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void runOpMode() {

        back_left = hardwareMap.get(DcMotor.class, "back_left_motor") ;
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        back_right = hardwareMap.get(DcMotor.class, "back_right_motor") ;
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        front_left = hardwareMap.get(DcMotor.class, "front_left_motor") ;
        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        front_right = hardwareMap.get(DcMotor.class, "front_right_motor") ;
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake = hardwareMap.get(DcMotor.class, "intake") ;
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sorter = hardwareMap.get(DcMotorEx.class, "sorter") ;
        sorter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        sorter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter = hardwareMap.get(DcMotorEx.class, "shooter") ;
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        color = hardwareMap.get(NormalizedColorSensor.class, "color_sensor");
        kicker = hardwareMap.get(Servo.class, "kicker");

        //limelight3A = limelight;
        waitForStart();
        //shooter.setVelocity(4);
        while (opModeIsActive()) {
           // shooter.setVelocity(4);

            //color_sensor = hardwareMap.get(ColorSensor.class, "color_sensor");
            telemetry.addData("encoder", back_left.getCurrentPosition());
            telemetry.update();
            float left_x = gamepad1.right_stick_x;
            float left_y = -gamepad1.left_stick_y;
            float right_x = -gamepad1.left_stick_x;
            boolean Intake = gamepad1.right_bumper;
            float max = Math.max(Math.abs(left_y)+Math.abs(left_x)+Math.abs(right_x),1.0f);

            float fr_drive = -(left_y - left_x - right_x)/max;
            float fl_drive = -(left_y + left_x + right_x)/max;
            float br_drive = (left_y + left_x - right_x)/max;
            float bl_drive = -(left_y - left_x + right_x)/max;
            drive(bl_drive, br_drive, fl_drive, fr_drive);

            if (gamepad1.right_bumper){

                startIntake();
            }
            else {
                stopIntake();
            }
//      changing to intake mode and shoot mode
            if (gamepad1.left_bumper) {

                try {
                    shoot();
                }
                catch (InterruptedException ie) {
                }
            }
            else {
//            if (!intakeMode){
//                rotateDegrees(60);
//                intakeMode = true;
//            }
//                if (!sorter.isBusy()){
//                    shooter.setPower(0);
//                }
            }

            if (gamepad1.b){
                reverseIntake();
            }
            //test
            if (gamepad1.y){
                rotateDegrees(120);
            }
//            else {
//                if (!sorter.isBusy()){
//                    shooter.setPower(0);
//                }
//            }
//      set shooter on and off
//            if (gamepad1.x){
//                shooter.setVelocity(1000);
//
//            }
//            else {
//                shooter.setPower(0);
//
//            }
            //test color sense
            if (gamepad1.a)
            {
                colorSense();
            }
            //kicking out ball
            if (gamepad1.left_trigger > .5f){
                kicker.setPosition(0);
            }
            else{
                kicker.setPosition(1);
            }
            boolean right = gamepad1.dpad_right;
            boolean left = gamepad1.dpad_left;

            if (right){
                rotateDegrees(120);
            }
            else if (left){
                rotateDegrees(-120);
            }
        }
    }

    protected void drive(float back_left_power, float back_right_power, float front_left_power, float front_right_power) {
        back_left.setPower(back_left_power);
        back_right.setPower(back_right_power);
        front_left.setPower(-front_left_power);
        front_right.setPower(-front_right_power);
    }


    protected void startIntake() {

        intake.setPower(-1);
        //colorSense();
    }

    protected void sort()
    {
        int sortpos = sorter.getCurrentPosition();
        int target = sortpos +240;
        //sorter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sorter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        sorter.setTargetPosition(target);
        sorter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        sorter.setPower(1);
        telemetry.addData("sorting","yes");
        telemetry.addData("sorty", sortpos);
        telemetry.addData("target",target);
        telemetry.addData("sortpower", sorter.getPower());
        telemetry.update();
        if (sortpos == target){
            sorter.setPower(0);
        }

    }
    protected void rotateDegrees(int degrees){


        //int sortpos = sorter.getCurrentPosition();

        int ticks = 820 * degrees / 360;
        sorter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        if (degree_count >1080){
            ticks -= 1;
            degree_count = 0;
        }
        sorter.setTargetPosition(ticks);
        sorter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        sorter.setVelocity(1000);

       // degree_count += degrees;
        boolean unbroken = true;
        while (sorter.isBusy()&&unbroken){
            if (gamepad1.leftStickButtonWasPressed()){
                unbroken = false;
                break;
            }
        }

    }



    protected void stopIntake() {
        intake.setPower(0);
    }

    protected void reverseIntake() {
        intake.setPower(1);
    }
    protected void kill(){
        //shooter.setVelocity(0);
        intake.setPower(0);
        sorter.setPower(0);
    }

    protected void shoot() throws InterruptedException {
        //kill();
        if (intakeMode){
            rotateDegrees(60);
            intakeMode = false;
        }
        shooter.setPower(1);
        Thread.sleep(3000);
        shooter.setPower(0);
        shooter.setVelocity(6);
        telemetry.addData("velocity", shooter.getVelocity());
        telemetry.update();
        Thread.sleep(3500);
        shooter.setVelocity(6);
        kicker.setPosition(0);
        Thread.sleep(1500);
        kicker.setPosition(1);
        Thread.sleep(2000);
        rotateDegrees(120);
        Thread.sleep(1500);
        shooter.setVelocity(6);
        kicker.setPosition(0);
        Thread.sleep(1000);
        kicker.setPosition(1);
        Thread.sleep(1500);
        rotateDegrees(120);
        Thread.sleep(1500);
        shooter.setVelocity(6);
        kicker.setPosition(0);
        Thread.sleep(1500);
        kicker.setPosition(1);
        intakeMode = true;
        rotateDegrees(60);
        shooter.setVelocity(0);
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */


    public void colorSense() {
        NormalizedRGBA colors = color.getNormalizedColors();

        float normalizedRed = colors.red / colors.alpha;
        float normalizedBlue = colors.blue / colors.alpha;
        float normalizedGreen = colors.green / colors.alpha;

        Float normalizedRedGreenRatio = normalizedRed / normalizedGreen;
        if (normalizedRedGreenRatio > .8f && normalizedRedGreenRatio < 1.2f) {
            telemetry.addData("color", "purple");
            rotateDegrees(120);
        }
        else if (normalizedGreen > 1.2f) {
            telemetry.addData("color", "green");
            rotateDegrees(120);
        }
        else {
            telemetry.addData("color", "black");
        }

        telemetry.update();
    }
    /*
     * Code to run ONCE after the driver hits STOP
     */
}
