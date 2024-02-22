package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter.Shooter;

public class RunShooter extends Command {
    private final Shooter shooterSubsystem;
    private final RobotContainer robotContainer;

    //initialize command subsystems
    public RunShooter(Shooter shooter, RobotContainer container) {
        shooterSubsystem = shooter;
        robotContainer = container;
        addRequirements(shooterSubsystem);
    } 

    //Run the Shooter Motors
    @Override
    public void initialize() {
        shooterSubsystem.runShooterMotors();
    }

    //Checks if Right Trigger is released
    @Override 
    public boolean isFinished() {
        if (robotContainer.manipXbox.getRawAxis(3) < 60) {
            return true;
        } else {
            return false;
        }
    }

    //Stops Shooter Motors
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stopShooterMotors();
    }



}
