package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Feeder extends SubsystemBase {
    public Shooter shooter;

    public CANSparkMax feederMotor = new CANSparkMax(14, MotorType.kBrushless);

    public double requiredShooterSpeed = 0.9;
    public boolean shooterAtSpeed() {
        if (shooter.motorIsKraken == true) {
            if (shooter.shooterMotor1K.get() >= requiredShooterSpeed & shooter.shooterMotor2K.get() >= requiredShooterSpeed) {
                return true;
            } else {
                return false;
            }
        } else {
            if (shooter.shooterMotor1N.get() >= requiredShooterSpeed & shooter.shooterMotor2N.get() >= requiredShooterSpeed) {
                return true;
            } else {
                return false;   
    }}}
}
