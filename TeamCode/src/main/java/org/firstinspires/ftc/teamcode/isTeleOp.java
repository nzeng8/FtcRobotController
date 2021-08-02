package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name="OdometryTest", group="Linear Opmode")
public class isTeleOp extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    enum IntakeState { IDLE, STARTED, ENDED };
    private DcMotor backRight = null;
    private DcMotor backLeft = null;
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    public double clip(double x) {
        return Range.clip(x, -1, 1);
    }
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        frontLeft = hardwareMap.dcMotor.get("FrontLeft");
        frontRight = hardwareMap.dcMotor.get("FrontRight");
        backLeft = hardwareMap.dcMotor.get("BackLeft");
        backRight = hardwareMap.dcMotor.get("BackRight");
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        // Wait for the game to start (driver presses PLAY)
        // run until the end of the match (driver presses STOP)
        double speedScale = 0.6;
        double RingPosition = 0.50;
        double PusherForce = 0.64;
        boolean stacking = true;
        double pos = 0;
        waitForStart();
        runtime.reset();
        double intakePower = 0;
        while (opModeIsActive()) {
            //final double liftUpKeepPower = -0.05;
            // Scoring controls
            if (gamepad1.dpad_left && gamepad1.b || gamepad2.dpad_left && gamepad2.b) {
                stacking = false;
            }
            if (gamepad1.dpad_left && gamepad1.x || gamepad2.dpad_left && gamepad2.x) {
                stacking = true;
            }
            // Motion controls
            double backRightPower = 0;
            double backLeftPower = 0;
            double frontRightPower = 0;
            double frontLeftPower = 0;
            frontLeftPower  = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
            frontRightPower = -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
            backLeftPower   = -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;
            backRightPower  = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;
            frontRight.setPower(clip(frontRightPower*speedScale));
            frontLeft.setPower(clip(frontLeftPower*speedScale));
            backRight.setPower(clip(backRightPower*speedScale));
            backLeft.setPower(clip(backLeftPower*speedScale));
            //if(gamepad2.left_stick_y > 0.0){
            //if(intakePower == 0){
            //intakePower = 1.5;
            //Intake.setPower(clip(intakePower));
            //}else{
            //intakePower = 0;
            //Intake.setPower(clip(intakePower));
            //}
            //}
            idle();
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Mode", "%s", stacking ? "Stacking" : "Delivery");
            telemetry.addData("SpeedScale", "%.2f", speedScale);
            //telemetry.addData("WristPostion", "%.2f", wristPosition);
            telemetry.update();
        }
    }
}