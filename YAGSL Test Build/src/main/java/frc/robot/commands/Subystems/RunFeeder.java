package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import java.lang.Math;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

public class RunFeeder extends Command {
    private final Feeder feederSubsystem;
    private final RobotContainer robotContainer;
    private final Intake intakeSubsystem;
    private boolean feederRunning;
    private boolean beamBroken;
    private boolean isShooting;

    //Initialize Command Subsytems
    public RunFeeder(Feeder feeder, RobotContainer container, Intake intake) {
        feederSubsystem = feeder;
        robotContainer = container;
        intakeSubsystem = intake;
        addRequirements(feederSubsystem,intakeSubsystem);
    }

    //Feeder Command has 2 different modes for either taking from intake, and shooting.
    //This is determined by the beam break sensor in the initialize section
    @Override
    public void initialize() {
        feederRunning = false;
        if (beamBroken == false) {
            isShooting = false;
        } else {
            isShooting = true;
        }
        
    }

    //For Shooting: Checks if shooter is up to speed and then runs the feeder
    //For Taking: Checks if feeder and shooter are at correct angles (within a tolerance) and then takes it
    @Override
    public void execute() {
        if (isShooting == true) {
            if (feederSubsystem.shooterAtSpeed() == true && feederRunning == false) {
                feederSubsystem.feederMotor.set(feederSubsystem.feederSpeed);
                feederRunning = true;
            }
        } else {
            if ((Math.abs(10 - intakeSubsystem.intakEncoder.getPosition())) <= 5 && (Math.abs(10 - feederSubsystem.feederEncoder.getPosition()) <= 5)) {
                if (feederRunning == true) {
                    feederSubsystem.runTransfer();
                    feederRunning = true;
                }
            }
        }
    }

    //Checks if Left Trigger is released, or for taking, if the beam is broken
    @Override
    public boolean isFinished() {
        if (isShooting == true) {
            if (robotContainer.manipXbox.getRawAxis(2) < 0.60) {
                return true;
            } else {
                return false;
            }
        } else {
            if (robotContainer.manipXbox.getRawAxis(2) < 0.60) {
                return true;
            } else if (beamBroken == true) {
                return true;
            } {
                return false;
            }
        }
    }

    //Stops all motors
    @Override
    public void end(boolean interrupted) {
        if (isShooting == true) {
            feederSubsystem.feederMotor.set(0);
        } else {
            feederSubsystem.feederMotor.set(0);
            intakeSubsystem.intakeIntaking.set(TalonSRXControlMode.PercentOutput, 0);
        }
    }   
}
