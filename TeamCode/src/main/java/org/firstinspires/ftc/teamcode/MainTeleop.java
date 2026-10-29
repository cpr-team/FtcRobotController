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
public class MainTeleop extends AutoFunctionsLinear {

    boolean ON = false;
    @Override
    public void runOpMode() throws InterruptedException {

        super.runOpMode();

        waitForStart();

        while (opModeIsActive()) {



            telemetry.update();
            float left_x = -gamepad1.left_stick_x;
            float left_y = gamepad1.left_stick_y;
            float right_x = gamepad1.right_stick_x;

            float max = Math.max(Math.abs(left_y)+Math.abs(left_x)+Math.abs(right_x),1.0f);

            float fr_drive = (left_x - left_y + right_x)/max;
            float fl_drive = (left_x + left_y + right_x)/max;
            float br_drive = (left_x + left_y - right_x)/max;
            float bl_drive = (left_x - left_y - right_x)/max;
            drive(bl_drive, br_drive, fl_drive, fr_drive);

            if (gamepad1.right_bumper){
                startIntake();
            }
            else if (gamepad1.b) {
                reverseIntake();
            }
            else if (gamepad1.left_bumper) {
                intake2.setPower(1);
                intake.setPower(-1);
            }
            else if (gamepad1.right_trigger > .5f) {
                intake2.setPower(-1);
                intake.setPower(1);
                shooter.setVelocity(1700);
            }
            else {
                stopIntake();
                shooter.setVelocity(0);
                intake2.setPower(0);
            }

//            if(gamepad1.left_bumper){
//                intake2.setPower(1);
//            }
//            else{
//                intake2.setPower(0);
//            }
//      changing to intake mode and shoot mode
//            if (gamepad1.left_bumper) {
//
//                try {
//                    shoot();
//                }
//                catch (InterruptedException ie) {
//                }
//            }

//
//            if(gamepad1.right_trigger > .5f){
//                shooter.setVelocity(2000);
//                intake2.setPower(1);
//                intake.setPower(1);
//            }else{
//                shooter.setVelocity(0);
//                intake2.setPower(0);
//                intake.setPower(0);
//            }




            //kicking out ball
            if (gamepad1.left_trigger > .5f){
                intakeHold.setPosition(0);
            }
            else{
                intakeHold.setPosition(1);
            }

//            if(gamepad1.x && !ON){
//                ShooterSize.setPosition(0);
//                ON = true;
//            }
//            else if(gamepad1.x && ON){
//                ShooterSize.setPosition(1);
//                ON = false;
//            }


//            if(gamepad1.dpad_up){
//                assistedShoot();
//            }


        }}
    protected void kill(){
        //shooter.setVelocity(0);
        //intake.setPower(0);
        //sorter.setPower(0);
    }
}
