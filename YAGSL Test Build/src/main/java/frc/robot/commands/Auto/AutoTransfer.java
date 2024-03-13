package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;

public class AutoTransfer extends Command {
    
    private final Shooter shooterSubsystem;
    private final Feeder feederSubsystem;
    private final Intake intakeSubsystem;

    private boolean transferRunning;
    private double loopsRun;
    private double loopsBeforeBreak = 20;
    
    public AutoTransfer(Feeder feeder, Intake intake, Shooter shooter) {
        shooterSubsystem = shooter;
        feederSubsystem = feeder;
        intakeSubsystem = intake;
        addRequirements(feederSubsystem, intakeSubsystem);
    }

    @Override
    public void initialize() {
        transferRunning = false;
        loopsRun = 0;
    }

    @Override 
    public void execute() {
        if (intakeSubsystem.currentIntakePosition == 0 && shooterSubsystem.shooterPosition == 0 && transferRunning == false) {
            feederSubsystem.runTransfer(true);
        }
        loopsRun += 1;
    }

    @Override
    public boolean isFinished() {
        if (feederSubsystem.getBeamStatus() == false || loopsRun == loopsBeforeBreak || DriverStation.isAutonomous() == false) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void end(boolean interrupted) {
        feederSubsystem.stopTransfer();
    }
}
