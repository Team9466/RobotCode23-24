package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

public class Feeder extends SubsystemBase {
    public Shooter shooter;
    private Intake intake;

    public CANSparkMax feederMotor = new CANSparkMax(15, MotorType.kBrushless);
    public SparkAbsoluteEncoder feederEncoder = feederMotor.getAbsoluteEncoder(Type.kDutyCycle);

    public double requiredShooterSpeed = 0.9;
    public final double feederSpeed = 0.95;

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
    
    public void runTransfer() {
        feederMotor.set(feederSpeed);
        intake.intakeIntaking.set(TalonSRXControlMode.PercentOutput, intake.outtakeSpeed);
    }

    public void stopTransfer() {
        feederMotor.set(0);
        intake.intakeIntaking.set(TalonSRXControlMode.PercentOutput, 0);
    }

    //Commands for constructing Autos
    public Command runFeederAuto() {
        return this.runOnce(() -> feederMotor.set(feederSpeed));
    }
    public Command stopFeederAuto() {
        return this.runOnce(() -> feederMotor.set(0));
    }
    public Command runTransferAuto() {
        return this.runOnce(() -> runTransfer());
    }
    public Command stopTransferAuto() {
        return this.runOnce(() -> runTransfer());
    }
}
