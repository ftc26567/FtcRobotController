/*
        package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
public class ArmCodeTestworse extends OpMode {

    private DcMotor topArmFront = null;
    private Servo pickup = null;
    private Servo rotate = null;

    @Override
    public void init() {

        topArmFront = hardwareMap.get(DcMotor.class, "topArmfront");
        pickup = hardwareMap.get(Servo.class, "pickup");
        rotate = hardwareMap.get(Servo.class, "rotate");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            topArmFront.setPower(0.567890);
        } else {
            topArmFront.setPower(0);
        }
        if (gamepad1.b) {
            pickup.setPower(0.25);
        } else {
            pickup.setPower(0);
        }
        if (gamepad1.x) {
            rotate.setPower(0.25);
        } else {
            rotate.setPower(0);
        }
        telemetry.addData("Servo Position", pickup.getPosition());
        telemetry.addData("Servo Position", rotate.getPosition());
        telemetry.update();

    }
}

*/