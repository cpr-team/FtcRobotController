package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "PedroAutoBlue", group = "Autonomous")
public class PedroAutoBlue extends AutoFunctionsLinear {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(132.83709449929478, 40.930183356840615, 90);
    private final Pose path1 = poseFactory.of(109.68617771509167, 118.02609308885756, 90);
    private final Pose path2 = poseFactory.of(85.11953455571225, 118.18692700987307, 90);
    private final Pose path3 = poseFactory.of(79.73095909732017, 22.390029971791254, -90);
    private final Pose path4 = poseFactory.of(85.11953455571225, 118.18692700987307, 90);
    private final Pose path5 = poseFactory.of(109.68617771509167, 118.02609308885756, 90);
    private final Pose path6 = poseFactory.of(132.83709449929478, 40.930183356840615, 90);
    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                detectAprilTag()
        );
    }

    public Command autoRoutinePark() throws InterruptedException{
        telemetry.addData("LLD", LLD);
        telemetry.addLine("in park routine");

        if (LLD) {
            telemetry.addData("something", true);
            telemetry.update();

            return sequential(
                    doShoot2(),
                    follow(follower, reverse()),
                    follow(follower, reverse2())

            );
        }
        else {
            return sequential(
                    follow(follower, path3()),
                    doShoot2(),
                    follow(follower, path3reverse())
            );
        }
    }

    private Command doShoot2() {
        return Command.build().setExecute(this::shoot2).setDone(()->shoot2Done);
    }
    private Command detectAprilTag(){
        return Command.build().setExecute(this::autoLimeLightTestBlue).setDone(()->limelightDone&&LLD);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();

        schedule(autoRoutine());

        while (opModeIsActive()) {
            Scheduler.execute();
            follower.update();

//            telemetry.addData("x", follower.pose().x());
//            telemetry.addData("y", follower.pose().y());
//            telemetry.addData("heading", follower.pose().heading());
//            telemetry.addData("LLD", LLD);
//            if (follower.currentPath() != null) {
//                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
//                telemetry.addData("Path number", follower.pathIndex());
//            }

            if(!follower.isBusy()){
                schedule(autoRoutinePark());
            }
        }

    }

    public Path path1(){return line(start, path1).linear(start, path1);}
    public Path path2(){
        return line(path1, path2).linear(path1,path2);
    }
    public Path path3(){
        return line(path2, path3).linear(path2,path3);
    }
    public Path path1reverse(){
        return line(path1,start).linear(path1,start);
    }
    public Path path2reverse(){
        return line(path2,path1).linear(path2,path1);
    }
    public Path path3reverse(){
        return line(path3,start).linear(path3,start);
    }
    public Path reverse(){
        return line(path4,path5).linear(path4,path5);
    }
    public Path reverse2(){
        return line(path5,path6).linear(path5,path6);
    }

}
