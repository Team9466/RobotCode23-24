package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter.Shooter;

public class StopShooter extends Command {
    private final Shooter shooterSubsystem;

    //initialize command subsystems
    public StopShooter(Shooter shooter) {
        shooterSubsystem = shooter;
        addRequirements(shooterSubsystem);
    } 

    //Run the Shooter Motors
    @Override
    public void initialize() {
        shooterSubsystem.stopShooterMotors();
    }

    //Checks if Right Trigger is released
    @Override 
    public boolean isFinished() {
        return true;
    }
}
