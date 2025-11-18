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
                    String fr_id = patterns.get(fr.getFiducialId());
                    telemetry.addData("pattern", patterns.get(fr_id));

                    if (fr_id.equals("red")){
                        rotate_degree(360);
                    }
                    if (fr_id.equals("blue")){
                        rotate_degree(-360);
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
