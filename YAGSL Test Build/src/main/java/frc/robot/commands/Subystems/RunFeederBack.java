package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;

public class RunFeederBack extends Command {

    private final Shooter shooterSubsystem;
    private final Feeder feederSubsystem;
    private final Intake intakeSubsystem;

    public RunFeederBack(Feeder feederSubsystem, Intake intakeSubsystem, Shooter shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.feederSubsystem = feederSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(feederSubsystem,intakeSubsystem,shooterSubsystem);
    }

    @Override
    public void initialize() {
        if (shooterSubsystem.shooterPosition == 0 && intakeSubsystem.currentIntakePosition == 0) {
            feederSubsystem.runTransfer(false);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
