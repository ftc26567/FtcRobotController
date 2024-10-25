package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp()
public class MotorTest1 extends OpMode {
    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    @Override
    public void init() {
        backLeftMotor= hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor =  hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor =  hardwareMap.get(DcMotor.class, "backRightMotor");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            backRightMotor.setPower(0.25);
            backLeftMotor.setPower(-0.25);
            frontRightMotor.setPower(0.25);
            frontLeftMotor.setPower(-0.25);
        } else {
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
        }
        if (gamepad1.b) {
            backRightMotor.setPower(-0.25);
            backLeftMotor.setPower(0.25);
            frontRightMotor.setPower(-0.25);
            frontLeftMotor.setPower(0.25);
        } else {
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
        }
        if (gamepad1.x) {
            backRightMotor.setPower(0.25);
            backLeftMotor.setPower(0.25);
            frontRightMotor.setPower(0.25);
            frontLeftMotor.setPower(0.25);
        } else {
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
        }
        if (gamepad1.y) {
            backRightMotor.setPower(-0.25);
            backLeftMotor.setPower(-0.25);
            frontRightMotor.setPower(-0.25);
            frontLeftMotor.setPower(-0.25);
        } else {
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
        }
   }

}
