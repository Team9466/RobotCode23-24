package frc.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.ShooterHardware;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class Feeder extends SubsystemBase {
    public ShooterHardware shooter;
    private Intake intake;
    private FeederHardware feederHardware;
    public boolean isShooting;

    private XboxController manipXbox = new XboxController(1);
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
    
    public double getControllerAxis() {
        return manipXbox.getRawAxis(2);
    }
    
    public void runFeederMotor(double speed) {
        feederHardware.feederMotor.set(speed);
    }
    
    public void runTransfer() {
        feederHardware.feederMotor.set(feederSpeed);
        intake.runIntake(intake.outtakeSpeed);
    }

    public void stopTransfer() {
        feederHardware.feederMotor.set(0);
        intake.runIntake(0);
    }

    //Commands for constructing Autos
    public Command runFeederAuto() {
        return this.runOnce(() -> feederHardware.feederMotor.set(feederSpeed));
    }
    public Command stopFeederAuto() {
        return this.runOnce(() -> feederHardware.feederMotor.set(0));
    }
    public Command runTransferAuto() {
        return this.runOnce(() -> runTransfer());
    }
    public Command stopTransferAuto() {
        return this.runOnce(() -> runTransfer());
    }

    public Feeder(FeederHardware feederHardware, Intake intake) {
        this.feederHardware = feederHardware;
        this.intake = intake;
    }
}
