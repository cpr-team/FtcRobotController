package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "JustinAuto", group = "Autonomous")
public class JustinAuto extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(131.4667, 35.0696, 180);
    private final Pose path1Start = poseFactory.of(131.4667, 35.0696, 180);
    private final Pose path1 = poseFactory.of(24.0087, 35.1797, 180);
    private final Pose point2Start = poseFactory.of(24.0087, 35.1797, 90);
    private final Pose point2 = poseFactory.of(22.7572, 118.5703, 90);
    private final Pose point3Start = poseFactory.of(22.7572, 118.5703, 0);
    private final Pose point3 = poseFactory.of(110.4014, 118.6971, 0);
    private final Pose point4Start = poseFactory.of(110.4014, 118.6971, -90);
    private final Pose point4 = poseFactory.of(110.9406, 35.0529, -90);
    private final Pose point5 = poseFactory.of(131.5638, 35.0362, 179.9537);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4()),
                follow(follower, path5())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return line(path1Start, path1).linear(path1Start, path1);
    }

    public Path path2() {
        return line(point2Start, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return line(point3Start, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return line(point4Start, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return line(point4, point5).reverseTangent();
    }
}
