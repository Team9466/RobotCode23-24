package frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkBase;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class Shooter extends SubsystemBase {

    private ShooterHardware shooterHardware;

    private XboxController manipXbox = new XboxController(1);

    //0 for default, 1 for shooting, 2 for amp
    public int shooterPosition = 0;
    //Shooting First, Amp Second in array
    public double[] shooterAngles = {0.238291015625, 0.33251953125, 0.5777587890625};
    public double shooterPivotSpeed = 0.25;
    public double shooterSetAngle = 0;
    
    //Sets the motor speed as a percentage, must be between -1 & 1
    public double shooterMotorSpeed = 0.95;
    public void runShooterMotors() {
        if (shooterHardware.motorIsKraken == true) {
            if (shooterPosition == 1) {
                shooterHardware.shooterMotor1K.set(shooterMotorSpeed);
                shooterHardware.shooterMotor2K.set(shooterMotorSpeed);
            } else if (shooterPosition == 2) {
                shooterHardware.shooterMotor1K.set(shooterMotorSpeed/4);
                shooterHardware.shooterMotor2K.set(shooterMotorSpeed/4);
            } else {
                shooterHardware.shooterMotor1K.set(shooterMotorSpeed/4);
                shooterHardware.shooterMotor2K.set(shooterMotorSpeed/4);
            }
        } else {
            if (shooterPosition == 1) {
                shooterHardware.shooterMotor1N.set(shooterMotorSpeed);
                shooterHardware.shooterMotor2N.set(shooterMotorSpeed);
            } else if (shooterPosition == 2) {
                shooterHardware.shooterMotor1N.set(shooterMotorSpeed/4);
                shooterHardware.shooterMotor2N.set(shooterMotorSpeed/4);
            } else {
                shooterHardware.shooterMotor1N.set(shooterMotorSpeed/4);
                shooterHardware.shooterMotor2N.set(shooterMotorSpeed/4);
            }
        }
    }
    public void stopShooterMotors() {
        if (shooterHardware.motorIsKraken == true) {
            shooterHardware.shooterMotor1K.set(0);
            shooterHardware.shooterMotor2K.set(0);
        } else {
            shooterHardware.shooterMotor1N.set(0);
            shooterHardware.shooterMotor2N.set(0);
        }
    }

    public void setShooterAngle(double angle) {
        System.out.println("Set Shooter Angle");
        System.out.println(shooterHardware.shooterAlternateEncoder.getPosition());
        shooterHardware.shooterPivotController.setFeedbackDevice(shooterHardware.shooterAlternateEncoder);
        shooterHardware.shooterPivotController.setPositionPIDWrappingEnabled(true);
        shooterHardware.shooterPivotController.setReference((angle), CANSparkBase.ControlType.kPosition);
    }

    public void runShooterPivot(double speed) {
        shooterHardware.shooterPivotController.setReference(speed, CANSparkBase.ControlType.kDutyCycle);
    }

    public double getControllerAxis() {
        return manipXbox.getRawAxis(3);
    }

    //Commands below for use in auto creation
    public Command runShooterAuto() {
        return this.runOnce(() -> runShooterMotors());
    }
    public Command stopShooterAuto() {
        return this.runOnce(() -> stopShooterMotors());
    }
    public Command shooterDefaultAuto() {
        return this.runOnce(() -> setShooterAngle(shooterAngles[0]));
    }
    public Command shooterShootAuto() {
        return this.runOnce(() -> setShooterAngle(shooterAngles[1]));
    }
    public Command shooterAmpAuto() {
        return this.runOnce(() -> setShooterAngle(shooterAngles[2]));
    }

    public void printShooterAngle() {
        System.out.println("Shooter Angle: " + shooterHardware.shooterAlternateEncoder.getPosition());
    }
    
    public Shooter(ShooterHardware shooterHardware) {
        this.shooterHardware = shooterHardware;
    }
}
