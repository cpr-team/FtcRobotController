package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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
public class Teleop extends OpMode {


    protected DcMotor back_left;

    protected DcMotor back_right;

    protected DcMotor front_left;

    protected DcMotor front_right;

    protected DcMotor intake;

    protected DcMotorEx sorter;

    protected DcMotorEx shooter;
    //private limelight3A limelight;

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init() {

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
        //limelight3A = limelight;
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



    protected void stopIntake() {
        intake.setPower(0);
    }

    protected void reverseIntake() {
        intake.setPower(1);
    }

    protected void shoot() throws InterruptedException {
        shooter.setVelocity(100);
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
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

        if (gamepad1.left_bumper) {
            try {
                shoot();
            }
            catch (InterruptedException ie) {
            }
        }
        else if (gamepad1.b){
            reverseIntake();
        }
        if (gamepad1.y){
            sort();
        }
        else if (gamepad1.x){
            shooter.setPower(1);

        }
        else {
            shooter.setPower(0);

        }
//        if (gamepad1.left_bumper)
//        {
//
//        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
