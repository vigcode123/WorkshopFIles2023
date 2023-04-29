package org.firstinspires.ftc.teamcode.teleop.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name = "DrivetrainTest", group = "Competition")
public class DrivetrainTest extends LinearOpMode {

    private GamepadEx gp1 = new GamepadEx(gamepad1);
    private final MotorEx frontLeftMotor = new MotorEx(opMode.hardwareMap, "motorFL");
    private final MotorEx frontRightMotor = new MotorEx(opMode.hardwareMap, "motorFR");
    private final MotorEx backLeftMotor = new MotorEx(opMode.hardwareMap, "motorBL");
    private final MotorEx backRightMotor = new MotorEx(opMode.hardwareMap, "motorBR");

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeftMotor.setInverted(false);
        frontRightMotor.setInverted(true);
        backLeftMotor.setInverted(false);
        backRightMotor.setInverted(true);

        frontLeftMotor.setRunMode(Motor.RunMode.RawPower);
        frontRightMotor.setRunMode(Motor.RunMode.RawPower);
        backLeftMotor.setRunMode(Motor.RunMode.RawPower);
        backRightMotor.setRunMode(Motor.RunMode.RawPower);

        frontLeftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


        while (opModeIsActive() && !isStopRequested()) {
            double y = -gp1.getLeftY(); // Remember, this is reversed!
            double x = gp1.getLeftX() * 1.1; // Counteract imperfect strafing
            double rx = gp1.getRightX();


            double denominator = Math.max(Math.abs(y) +Math.abs(x)+Math.abs(rx), 1);
            double frontLeftPower = (y+x+rx)/denominator;
            double frontRightPower = (y-x-rx)/denominator;
            double backLeftPower = (y-x+rx)/denominator;
            double backRightPower = (y+x-rx)/denominator;

            frontLeftMotor.set(frontLeftPower); // A number ranging from [-1,1]
            frontRightMotor.set(frontRightPower);
            backLeftMotor.set(backLeftPower);
            backRightMotor.set(backRightPower);

            telemetry.addData("Forward/backward power",y/denominator);
            telemetry.addData("Left/Right(Strafing) power",x/denominator);
            telemetry.addData("Clockwise/Anti-Clockwise(Turning) power",rx/denominator);
        }
    }
}



//@Config
//@TeleOp(name = "DrivetrainTest", group = "Competition")
//public class DrivetrainTest extends LinearOpMode {
//    GamepadEx gp1 = new GamepadEx(gamepad1);
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        while (opModeIsActive() && !isStopRequested()) {
//            if (gp1.wasJustPressed(GamepadKeys.Button.A)) {
//                if (gp1.wasJustPressed(GamepadKeys.Button.B)) {
//                    telemetry.addLine("Good Job! :')");
//                } else {
//                    telemetry.addLine("Oops, you're almost there! >:)");
//                }
//            } else {
//                telemetry.addLine("Oops, not the right one >:))) ");
//            }
//
//            telemetry.update();
//        }
//    }
//}
