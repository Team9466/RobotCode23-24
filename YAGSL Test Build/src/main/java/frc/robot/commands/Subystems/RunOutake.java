package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake.Intake;
import java.lang.Math;

public class RunOutake extends Command {
    private final Intake intakeSubsystem;

    //Initialize Command Subsytems
    public RunOutake(Intake iSubsystem) {
        intakeSubsystem = iSubsystem;

        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Outake Running");
        //Lower intake to position needed to retreive notes, spin up intake motors
        intakeSubsystem.setIntakePosition(intakeSubsystem.intakeAngles[1]);
        intakeSubsystem.currentIntakePosition = 1;
    }

    @Override
    public void execute() {
        if (Math.abs(intakeSubsystem.intakeAngles[1]-intakeSubsystem.getIntakeEncoderAngle()) <= 0.05) {
            intakeSubsystem.runIntake(-intakeSubsystem.intakeSpeed);
        }
    }

    @Override
    public boolean isFinished() {
        //Check if Right Trigger has gone below 60 (50 is base level), if so, ends the command
        if (intakeSubsystem.getControllerAxis2() < 0.60) {

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        //reset intake position and stop running before the command terminates
        intakeSubsystem.runIntake(0);
        intakeSubsystem.setIntakePosition(intakeSubsystem.intakeAngles[0]);
        intakeSubsystem.currentIntakePosition = 0;
        System.out.println("Outake Finished");
    }
}
