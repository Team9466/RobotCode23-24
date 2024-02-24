package frc.robot.commands.Subystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climb.Climb;

public class RunClimbUp extends Command {
    public final Climb climb;
    
    public RunClimbUp(Climb climb) {
        this.climb = climb;
        addRequirements(climb);
    }

    @Override
    public void initialize() {
        climb.runClimbers(climb.climbSpeed);
    }
    @Override
    public boolean isFinished() {
        return true;
    }
}
