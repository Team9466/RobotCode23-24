package frc.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeHardware;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterHardware;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class Feeder extends SubsystemBase {
    private ShooterHardware shooterHardware;
    private Intake intake;
    private FeederHardware feederHardware;
    private IntakeHardware intakeHardware;
    private Shooter shooter;
    public boolean isShooting;
    public boolean endedByBeam = false;

    private XboxController manipXbox = new XboxController(1);
    public double requiredShooterSpeed = 0.0;
    public final double feederSpeed = -0.55;

    public boolean shooterAtSpeed() {
        if (shooterHardware.motorIsKraken == true) {
            if (shooter.shooterPosition == 2) {
                if (shooterHardware.shooterMotor1K.get() >= (requiredShooterSpeed/4) & shooterHardware.shooterMotor2K.get() >= (requiredShooterSpeed/4)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (shooterHardware.shooterMotor1K.get() >= (requiredShooterSpeed) & shooterHardware.shooterMotor2K.get() >= (requiredShooterSpeed)) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (shooter.shooterPosition == 2) {
                if (shooterHardware.shooterMotor1N.get() >= (requiredShooterSpeed/4) & shooterHardware.shooterMotor2N.get() >= (requiredShooterSpeed/4)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (shooterHardware.shooterMotor1N.get() >= (requiredShooterSpeed) & shooterHardware.shooterMotor2N.get() >= (requiredShooterSpeed)) {
                    return true;
                } else {
                    return false;
                }
            }   
    }}
    
    public double getControllerAxis() {
        return manipXbox.getRawAxis(2);
    }
    
    public void runFeederMotor(double speed) {
        feederHardware.feederMotor.set(speed);
    }

    public boolean getBeamStatus() {
        return feederHardware.feederBeamBreak.get();
    }
    
    public void runTransfer(boolean forward) {
        System.out.println("Running Transfer");
        if (forward == true) {
            feederHardware.feederMotor.set(feederSpeed/2);
            intakeHardware.intakeIntaking.set(-(intake.intakeSpeed/1.5));
        } else {
            feederHardware.feederMotor.set(-feederSpeed/2);
            intakeHardware.intakeIntaking.set((intake.intakeSpeed/1.5));
        }
    }

    public void stopTransfer() {
        feederHardware.feederMotor.set(0);
        intake.runIntake(0);
    }

    public void autoTransfer() {
        boolean beamBroke = false;
        while (beamBroke == false || DriverStation.isAutonomous() == true) {
            feederHardware.feederMotor.set(feederSpeed/2);
            intakeHardware.intakeIntaking.set(-(intake.intakeSpeed/1.5));
            if (getBeamStatus() == false) {
                beamBroke = true;
            }
        }
        feederHardware.feederMotor.set(0);
        intakeHardware.intakeIntaking.set(0);
    }

    //Commands for constructing Autos
    public Command runFeederAuto() {
        return this.runOnce(() -> feederHardware.feederMotor.set(feederSpeed));
    }
    public Command stopFeederAuto() {
        return this.runOnce(() -> feederHardware.feederMotor.set(0));
    }
    public Command runTransferAuto() {
        return this.runOnce(() -> runTransfer(true));
    }
    public Command stopTransferCommand() {
        return this.runOnce(() -> stopTransfer());
    }

    public Feeder(FeederHardware feederHardware, Intake intake, Shooter shooter, ShooterHardware shooterHardware, IntakeHardware intakeHardware) {
        this.feederHardware = feederHardware;
        this.intake = intake;
        this.shooter = shooter;
        this.shooterHardware = shooterHardware;
        this.intakeHardware = intakeHardware;

    }
}
