package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake.Intake;

public class RunIntake extends Command {
    private final Intake intakeSubsystem;

    //Initialize Command Subsytems
    public RunIntake(Intake iSubsystem) {
        intakeSubsystem = iSubsystem;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        //Lower intake to position needed to retreive notes, spin up intake motors
        intakeSubsystem.setIntakePosition(intakeSubsystem.intakeLoweredAngle);
        intakeSubsystem.runIntake(intakeSubsystem.intakeSpeed);
    }

    @Override
    public boolean isFinished() {
        //Check if Right Trigger has gone below 60 (50 is base level), if so, ends the command
        if (intakeSubsystem.getControllerAxis() < 60) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        //reset intake position and stop running before the command terminates
        intakeSubsystem.runIntake(0);
        intakeSubsystem.setIntakePosition(0);
    }
}
