package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter.Shooter;

public class ShooterAngle extends Command {
    private final Shooter shooterSubsystem;

    //initialize command subsystems
    public ShooterAngle(Shooter shooter) {
        shooterSubsystem = shooter;
        addRequirements(shooterSubsystem);
    } 

    @Override
    public void initialize() {
        if (shooterSubsystem.shooterPosition == 0) {
            shooterSubsystem.shooterPosition = 1;
            shooterSubsystem.setShooterAngle(shooterSubsystem.shooterAngles[0]);
        } else if (shooterSubsystem.shooterPosition == 0) {
            shooterSubsystem.shooterPosition = 2;
            shooterSubsystem.setShooterAngle(shooterSubsystem.shooterAngles[1]);
        } else if (shooterSubsystem.shooterPosition == 0) {
            shooterSubsystem.shooterPosition = 0;
            shooterSubsystem.setShooterAngle(0);
        } else {
            shooterSubsystem.shooterPosition = 0;
            shooterSubsystem.setShooterAngle(0);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
