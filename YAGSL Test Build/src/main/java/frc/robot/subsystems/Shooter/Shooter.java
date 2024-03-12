package frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.revrobotics.CANSparkBase;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class Shooter extends SubsystemBase {

    private ShooterHardware shooterHardware;

    private XboxController manipXbox = new XboxController(1);

    //0 for default, 1 for shooting, 2 for amp
    public int shooterPosition = 0;
    //Shooting First, Amp Second in array
    public double[] shooterAngles = {0.230291015625, 0.360291015625, 0.5777587890625};
    public double shooterPivotSpeed = 0.25;
    public double shooterSetAngle = 0;
    
    //Sets the motor speed as RPS, first idle, second shoot, third amp
    public double[] shooterMotorSpeeds = {5, 95, 30};
    public double shooterMotorSpeed = 0.95;
    public double shooterIdleSpeed = 0.05;

    final VelocityDutyCycle shooterSpeedIdle = new VelocityDutyCycle(shooterMotorSpeeds[0]).withSlot(0);
    final VelocityDutyCycle shooterSpeedShoot = new VelocityDutyCycle(shooterMotorSpeeds[1]).withSlot(0);
    final VelocityDutyCycle shooterSpeedAmp = new VelocityDutyCycle(shooterMotorSpeeds[2]).withSlot(0);

    public void runShooterMotors() {
        if (shooterHardware.motorIsKraken == true) {
            if (shooterPosition == 1) {
                shooterHardware.shooterMotor1K.setControl(shooterSpeedShoot);
                shooterHardware.shooterMotor2K.setControl(shooterSpeedShoot);
            } else if (shooterPosition == 2) {
                shooterHardware.shooterMotor1K.setControl(shooterSpeedAmp);
                shooterHardware.shooterMotor2K.setControl(shooterSpeedAmp);
            } else {
                shooterHardware.shooterMotor1K.setControl(shooterSpeedIdle);
                shooterHardware.shooterMotor2K.setControl(shooterSpeedIdle);
            }
        } else {
            if (shooterPosition == 1) {
                shooterHardware.shooterMotor1N.set(-(shooterMotorSpeed));
                shooterHardware.shooterMotor2N.set(shooterMotorSpeed);
            } else if (shooterPosition == 2) {
                shooterHardware.shooterMotor1N.set(-(shooterMotorSpeed/4));
                shooterHardware.shooterMotor2N.set(shooterMotorSpeed/4);
            } else {
                shooterHardware.shooterMotor1N.set(shooterIdleSpeed);
                shooterHardware.shooterMotor2N.set(shooterIdleSpeed);
            }
        }
    }
    public void stopShooterMotors() {
        if (shooterHardware.motorIsKraken == true) {
            shooterHardware.shooterMotor1K.setControl(shooterSpeedIdle);
            shooterHardware.shooterMotor2K.setControl(shooterSpeedIdle);
        } else {
            shooterHardware.shooterMotor1N.set(shooterIdleSpeed);
            shooterHardware.shooterMotor2N.set(shooterIdleSpeed);
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
        return this.runOnce(() -> setShooterAngle(shooterAngles[0])).andThen(() -> shooterPosition = 0);
    }
    public Command shooterShootAuto() {
        return this.runOnce(() -> setShooterAngle(shooterAngles[1])).andThen(() -> shooterPosition = 1);
    }
    public Command shooterAmpAuto() {
        return this.runOnce(() -> setShooterAngle(shooterAngles[2])).andThen(() -> shooterPosition = 2);
    }

    public void printShooterAngle() {
        System.out.println("Shooter Angle: " + shooterHardware.shooterAlternateEncoder.getPosition());
    }
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter Velocity 1", shooterHardware.shooterMotor1K.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Shooter Velocity 2", shooterHardware.shooterMotor2K.getVelocity().getValueAsDouble());
    }

    public Shooter(ShooterHardware shooterHardware) {
        this.shooterHardware = shooterHardware;
        if (shooterHardware.motorIsKraken == true) {
            shooterHardware.shooterMotor1K.setControl(shooterSpeedIdle);
            shooterHardware.shooterMotor2K.setControl(shooterSpeedIdle);
        } else if (shooterHardware.motorIsKraken == false) {
            shooterHardware.shooterMotor1N.set(shooterIdleSpeed);
            shooterHardware.shooterMotor2N.set(shooterIdleSpeed);
        }
    }
}
