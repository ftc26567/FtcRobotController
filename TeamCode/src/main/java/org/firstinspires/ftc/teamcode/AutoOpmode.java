package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Robot: Auto", group="Robot")
public class AutoOpmode extends LinearOpMode {

    /* Declare OpMode members. */
    public DcMotor  armMotor    = null;
    public CRServo intake      = null;
    public Servo wrist       = null; //the wrist servo

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    //static final double     FORWARD_SPEED = 0.6;
    //static final double     TURN_SPEED    = 0.5;

    final double INTAKE_DEPOSIT    =  0.5;
    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    //double armPosition = (int)ARM_COLLAPSED_INTO_ROBOT;


    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation

    //Should test
    final double ARM_SCORE         = 150 * ARM_TICKS_PER_DEGREE;
    final double WRIST_FOLDED_OUT  = 0.45;
    final double WRIST_FOLDED_IN   = 0.75;
    public void drive(double axial, double lateral, double yaw, double seconds) {
        double max;

        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;
        ElapsedTime timer = new ElapsedTime();

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }

        // Send calculated power to wheels
        while (timer.seconds() <= seconds) {
            leftFrontDrive.setPower(leftFrontPower/2);
            rightFrontDrive.setPower(rightFrontPower/2);
            leftBackDrive.setPower(leftBackPower/2);
            rightBackDrive.setPower(rightBackPower/2);
        }
    }
    public void stopDrive() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }

    public void score() {
        intake.setPower(INTAKE_DEPOSIT);
    }

    /* Begin with one game piece inside the claw and the back right corner touching the end of the
    tape of the net zone. The arm and intake should be facing away from the net zone.

    *Hypothetically*, this should work, but I didn't have the robot with me while writing this so
    no guarantees.

    When testing auto, remember to always BE VERY VERY CAREFUL because a) the robot will be moving
    on its own and b) you do not necessarily know if it's going to work the way you want it to (as
    in, it may go a very wrong direction at a very fast speed and lead to unfortunate results). Keep
    someone's hand over the pause button at all times and be ready to press it if needed.

    You may need to mess with the power levels and their signs (I couldn't remember which side was
    the front of the robot) and you will probably need to adjust how long they run for (in seconds).

    Message me on slack if you have issues.
     */

    @Override
    public void runOpMode() {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        leftBackDrive = hardwareMap.get(DcMotor.class, "backLeftMotor");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontRightMotor");
        rightBackDrive = hardwareMap.get(DcMotor.class, "backRightMotor");
        intake = hardwareMap.get(CRServo.class, "intake");
        armMotor   = hardwareMap.get(DcMotor.class, "topArmfront");
        wrist  = hardwareMap.get(Servo.class, "wrist");


        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        /* Send telemetry message to signify robot waiting */
        telemetry.addLine("Auto - Robot Ready.");
        telemetry.update();

        /* Wait for the game driver to press play */
        waitForStart();
        runtime.reset();

        //Step 1: move arm and wrist
        armMotor.setTargetPosition((int) ARM_SCORE);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ((DcMotorEx) armMotor).setVelocity(2100);

        sleep(1000);
        wrist.setPosition(WRIST_FOLDED_OUT);
        sleep(1000);

        //Step 2: drive forward for a little bit to make it into the net zone
        // I have no idea how long this will take or how fast it will be so please test this on a
        // field

        drive(1, 0, 0, 1.0);
        stopDrive();
        //Step 3: score (spin the intake wheel)
        sleep(1000);

        score();
        sleep(1000);

        wrist.setPosition(WRIST_FOLDED_IN);

        sleep(1000);

        armMotor.setTargetPosition((int) ARM_COLLAPSED_INTO_ROBOT);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ((DcMotorEx) armMotor).setVelocity(2100);

        sleep(1000);

        /* Step 4:  Drive backward into the observation zone.
        I have no idea how long this will take or how fast it will be so please test this on a field.
        Depending on where the other robot ends up being placed, this may run into them, so you
        may need to last minute comment out this section and redeploy.
        This is not a very elegant solution but we can work on it in the future */

        drive(-1, 0, 0, 2.5);

        // Step 4: stop
        stopDrive();

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}