package frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;

public class Shooter extends SubsystemBase {

    private RobotContainer robotContainer;
    private ShooterHardware shooterHardware;

    //0 for default, 1 for shooting, 2 for amp
    public int shooterPosition;
    //Shooting First, Amp Second in array
    public double[] shooterAngles = {10, 20};
    
    //Sets the motor speed as a percentage, must be between -1 & 1
    public double shooterMotorSpeed = 0.95;
    public void runShooterMotors() {
        if (shooterHardware.motorIsKraken == true) {
            shooterHardware.shooterMotor1K.set(shooterMotorSpeed);
            shooterHardware.shooterMotor2K.set(shooterMotorSpeed);
        } else {
            shooterHardware.shooterMotor1N.set(shooterMotorSpeed);
            shooterHardware.shooterMotor2N.set(shooterMotorSpeed);
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
        shooterHardware.shooterPivotController.setReference(angle, CANSparkMax.ControlType.kPosition);
    }

    public double getControllerAxis() {
        return robotContainer.manipXbox.getRawAxis(3);
    }

    //Commands below for use in auto creation
    public Command runShooterAuto() {
        return this.runOnce(() -> runShooterMotors());
    }
    public Command stopShooterAuto() {
        return this.runOnce(() -> stopShooterMotors());
    }
    public Command shooterDefaultAuto() {
        return this.runOnce(() -> setShooterAngle(0));
    }
    public Command shooterShootAuto() {
        return this.runOnce(() -> setShooterAngle(shooterAngles[0]));
    }
    public Command shooterAmpAuto() {
        return this.runOnce(() -> setShooterAngle(shooterAngles[1]));
    }


    public Shooter(ShooterHardware shooterHardware) {
        this.shooterHardware = shooterHardware;
    }
}
