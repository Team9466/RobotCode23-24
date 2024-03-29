package frc.robot.commands.Subystems;

//import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;

public class RunFeeder extends Command {
    private final Feeder feederSubsystem;
    private final Intake intakeSubsystem;
    private final Shooter shooterSubsystem;
    private boolean feederRunning;
    //private boolean beamBroken = false;

    //Initialize Command Subsytems
    public RunFeeder(Feeder feeder, Intake intake, Shooter shooter) {
        shooterSubsystem = shooter;
        feederSubsystem = feeder;
        intakeSubsystem = intake;
        addRequirements(feederSubsystem,intakeSubsystem);
    }

    //Feeder Command has 2 different modes for either taking from intake, and shooting.
    //This is determined by the beam break sensor in the initialize section
    /*@Override
    public void initialize() {
        System.out.println("Running Feeder");
        feederRunning = false;
        if (feederSubsystem.endedByBeam == false) {
            if (feederSubsystem.getBeamStatus() == true) {
                beamBroken = false;
            } else {
                beamBroken = true;
            }
            if (beamBroken == false) {
                feederSubsystem.isShooting = false;
                System.out.println("Feeder Running: Intaking");
            } else {
                feederSubsystem.isShooting = true;
                System.out.println("Feeder Running: Shooting");
            }
        } else {
            feederSubsystem.isShooting = true;
        }
     */

     @Override
     public void initialize() {
        feederRunning = false;
        if (shooterSubsystem.shooterPosition == 0) {
            feederSubsystem.isShooting = false;
        } else {
            feederSubsystem.isShooting = true;
        }
     }

    //For Shooting: Checks if shooter is up to speed and then runs the feeder
    //For Taking: Checks if feeder and shooter are at correct angles (within a tolerance) and then takes it
    @Override
    public void execute() {
        if (feederSubsystem.isShooting == true) {
            //System.out.println("Running Shooting Checks");
            if (feederSubsystem.shooterAtSpeed() == true && feederRunning == false) {
                feederSubsystem.runFeederMotor(feederSubsystem.feederSpeed);
                feederRunning = true;
            }
        } else {
            //System.out.println("Running Transfer Checks");
            if ((intakeSubsystem.currentIntakePosition == 0) && (shooterSubsystem.shooterPosition == 0)) {
                if (feederRunning == false) {
                    feederSubsystem.runTransfer(true);
                    feederRunning = true;
                }
            }
        }
    }

    //Checks if Left Trigger is released, or for taking, if the beam is broken
    @Override
    public boolean isFinished() {
        if (feederSubsystem.isShooting == true) {
            if (feederSubsystem.getControllerAxis() < 0.60) {
                return true;
            } else {
                return false;
            }
        } else {
            if (feederSubsystem.getControllerAxis() < 0.60) {
                return true;
            } else if (feederSubsystem.getBeamStatus() == false) {
                //feederSubsystem.endedByBeam = true;
                return true;
            } {
                //System.out.println("Ran Checks");
                return false;
            }
        }
    }

    //Stops all motors
    @Override
    public void end(boolean interrupted) {
        if (feederSubsystem.isShooting == true) {
            feederSubsystem.runFeederMotor(0);
            feederSubsystem.endedByBeam = false;
        } else {
            feederSubsystem.runFeederMotor(0);
            intakeSubsystem.runIntake(0);
        }
        System.out.println("Feeder Finished");
    }   
}
