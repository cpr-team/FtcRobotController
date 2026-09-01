package org.firstinspires.ftc.teamcode;



import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
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

import java.util.List;

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
public class TeleopLinear extends AutoFunctionsLinear {

    int currentPattern;

    public void findGreen() {
        if (ballPos[currentPattern].equals("green")) {
            return;

        }

       else if(ballPos[(currentPattern + 1) % 3].equals( "green")){
            rotate("right");
            return;
        }
       else {
           rotate("left");
           return;
       }
    }








    //private limelight3A limelight;
    protected NormalizedColorSensor color;





    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void runOpMode() throws InterruptedException {

        super.runOpMode();
        //sorter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        //limelight3A = limelight;
        waitForStart();
        //shooter.setVelocity(4);

        while (opModeIsActive()) {
            if (sensing){
                String color = colorSense();
                if (!color.equals("black")){
                    ballPos[0] = color;
                    rotate("right");
                }
            }
           // shooter.setVelocity(4);

            //color_sensor = hardwareMap.get(ColorSensor.class, "color_sensor");
            //telemetry.addData("encoder", sorter.getCurrentPosition());
            telemetry.update();
            //float left_x = -gamepad1.left_stick_y;
            //float left_y = gamepad1.left_stick_x;
            //float right_x = gamepad1.right_stick_x;
            //boolean Intake = gamepad1.right_bumper;
            //float max = Math.max(Math.abs(left_y)+Math.abs(left_x)+Math.abs(right_x),1.0f);

//            float fr_drive = (left_y - left_x - right_x)/max;
//            float fl_drive = (left_y + left_x + right_x)/max;
//            float br_drive = (left_y + left_x - right_x)/max;
//            float bl_drive = (left_y - left_x + right_x)/max;
            //drive(bl_drive, br_drive, fl_drive, fr_drive);
            float left_y = gamepad1.left_stick_y;
            float right_x = gamepad1.right_stick_x;
            drive(left_y,-left_y,-left_y,left_y);
            drive(-right_x,right_x,-right_x,right_x);

            if (gamepad1.right_bumper){

                startIntake();
                sensing = true;
            }
            else {
                stopIntake();
                sensing = false;
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
                //colorSense();
            }
            //kicking out ball
            if (gamepad1.left_trigger > .5f){
                //kicker.setPosition(0);
            }
            else{
                //kicker.setPosition(1);
            }
            boolean right = gamepad1.dpad_right;
            boolean left = gamepad1.dpad_left;

            if (right){
                rotateDegrees(120);
            }
//            else if (left){
//                rotateDegrees(-120);
//            }

            if(gamepad1.dpad_up){
                assistedShoot();
            }

            if(gamepad1.dpad_down){
                rotate2();
            }
        }
    }






    protected void sort()
    {
        //int sortpos = sorter.getCurrentPosition();
        //int target = sortpos +240;
        //sorter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        sorter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        sorter.setTargetPosition(target);
//        sorter.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//        sorter.setPower(1);
        telemetry.addData("sorting","yes");
        //telemetry.addData("sorty", sortpos);
        //telemetry.addData("target",target);
        //telemetry.addData("sortpower", sorter.getPower());
        telemetry.update();
//        if (sortpos == target){
//            sorter.setPower(0);
//        }

    }



        //int sortpos = sorter.getCurrentPosition();







    protected void kill(){
        //shooter.setVelocity(0);
//        intake.setPower(0);
//        sorter.setPower(0);
    }



    /*
     * Code to run ONCE when the driver hits PLAY
     */


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */


    /*public void colorSense() {
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
    */

    /*
     * Code to run ONCE after the driver hits STOP
     */
}
