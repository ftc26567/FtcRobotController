
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ArmCodeTest extends OpMode {
    private DcMotor Arm1;

    @Override
    public void init() {
        Arm1 = hardwareMap.get(DcMotor.class, "arm");
    }
        @Override
        public void loop() {
            if (gamepad1.a) {
                Arm1.setPower(0.25);
            } else {
                Arm1.setPower(0);
            }

        }

    }
