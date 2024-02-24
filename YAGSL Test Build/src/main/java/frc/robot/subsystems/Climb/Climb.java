package frc.robot.subsystems.Climb;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb extends SubsystemBase {
    private ClimbHardware climbHardware;

    public double climbSpeed = 0.95;

    public void runClimbers(double speed) {
        climbHardware.climbMotor1.set(speed);
        climbHardware.climbMotor2.set(speed);
    }

    public Command stopClimb() {
        return this.runOnce(() -> runClimbers(0));
    }

    public Climb(ClimbHardware climbHardware) {
        this.climbHardware = climbHardware;
    }

}
