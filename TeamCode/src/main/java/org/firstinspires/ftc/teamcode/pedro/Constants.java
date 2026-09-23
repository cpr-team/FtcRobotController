package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower (
         new PinpointLocalizer(h, localizerConfig),
         new Mecanum(h, drivetrainConfig),
         new Foresight(foresightConfig)
        );
    }

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("front_left_motor");
        c.frontRightName.set("front_right_motor");
        c.backLeftName.set("back_left_motor");
        c.backRightName.set("back_right_motor");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(1.2090166347233329);
        c.yPodOffset.set(7.106732345941499);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.97238961639009);
                Controller secondaryTranslationalForward = Controller.proportional(0.35927198052705756);
                Controller primaryTranslationalLateral = Controller.proportional(0.2063715084060107);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07624875800775714);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016219288637743717));
                c.brake.set(Controller.proportionalFeedforward(0.01378639534208216));

                c.headingFeedback.set(Controller.proportional(2.6332676011353895));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.045298868599695466, 0.0066558488022966855));

                c.linearBrakeCoefficients.set(Matrix.diag(0.06903278471901303, 0.10019473628865445));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0015802995893515694, 8.953666741786526E-4));

                c.maxAchievableForwardVelocity.set(66.19946681547852);
                c.maxAchievableStrafeVelocity.set(54.64337657394378);
                c.naturalForwardDeceleration.set(33.459915817232904);
                c.naturalStrafeDeceleration.set(46.06715438583701);
            }
    );
}

