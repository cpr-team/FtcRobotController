package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.AutoFunctionsLinear;

import java.util.List;
@Autonomous (name = "AutoTelemetry")
public class AutoTelemetry extends AutoFunctionsLinear {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        waitForStart();
        limelight.pipelineSwitch(0);

        limelight.start();

        waitForStart();

        while (opModeIsActive()) {

            LLResult result = limelight.getLatestResult();
            if (result.isValid()) {
                // Access fiducial results
                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    double x = result.getTx();
                    String fr_id = patterns.get(fr.getFiducialId());
                    telemetry.addData("pattern", patterns.get(fr_id));
                    telemetry.addData("x", x);

                    if (fr_id.equals("red")&& (gamepad1.right_bumper)){
                        if (x>.7){
                            rotateDegrees(2);
                        }
                        if (x<-.7){
                            rotateDegrees(-2);
                        }
                    }
                    if (fr_id.equals("blue")){
                        rotateDegrees(-360);
                    }
                }
            } else {
                telemetry.addData("Limelight", "No data available");
            }

            telemetry.update();
        }
        limelight.stop();
    }
}
