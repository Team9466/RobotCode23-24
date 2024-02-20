package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;
import frc.robot.RobotContainer;

public class RunFeeder extends Command {
    private final Feeder feederSubsystem;
    private final RobotContainer robotContainer;
    private boolean feederRunning;

    private static double feederSpeed = 0.95;

    //Initialize Command Subsytems
    public RunFeeder(Feeder feeder, RobotContainer container) {
        feederSubsystem = feeder;
        robotContainer = container;
        addRequirements(feederSubsystem);
    }
    @Override
    public void initialize() {
        feederRunning = false;
    }

    @Override
    public void execute() {
        if (feederSubsystem.shooterAtSpeed() == true && feederRunning == false) {
            feederSubsystem.feederMotor.set(feederSpeed);
            feederRunning = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (robotContainer.manipXbox.getRawAxis(2) < 0.60) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void end(boolean interrupted) {
        feederSubsystem.feederMotor.set(0);
    }   
}
