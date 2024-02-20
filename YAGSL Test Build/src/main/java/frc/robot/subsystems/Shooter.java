package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Shooter extends SubsystemBase {
    //Should either be set to "Neo" or "Kraken"
    public boolean motorIsKraken = false;

    public TalonFX shooterMotor1K;
    public TalonFX shooterMotor2K;
    public CANSparkMax shooterMotor1N;
    public CANSparkMax shooterMotor2N;

    
    //Initialize Motor Controllers for the shooter subsystem for either Neo or Kraken
    public CANSparkMax feederMotor = new CANSparkMax(13, MotorType.kBrushless);
    
    public void setMotorType() {
        if (motorIsKraken == true) {
            shooterMotor1K = new TalonFX(11);
            shooterMotor2K = new TalonFX(12);
        } else {
            shooterMotor1N = new CANSparkMax(11, MotorType.kBrushless);
            shooterMotor2N = new CANSparkMax(12, MotorType.kBrushless);
        }
    }   
    
    //Sets the motor speed as a percentage, must be between -1 & 1
    public double shooterMotorSpeed = 0.95;
    public void runShooterMotors() {
        if (motorIsKraken == true) {
            shooterMotor1K.set(shooterMotorSpeed);
            shooterMotor2K.set(shooterMotorSpeed);
        } else {
            shooterMotor1N.set(shooterMotorSpeed);
            shooterMotor2N.set(shooterMotorSpeed);
        }
    }
    public void stopShooterMotors() {
        if (motorIsKraken == true) {
            shooterMotor1K.set(0);
            shooterMotor2K.set(0);
        } else {
            shooterMotor1N.set(0);
            shooterMotor2N.set(0);
        }
    }
}
