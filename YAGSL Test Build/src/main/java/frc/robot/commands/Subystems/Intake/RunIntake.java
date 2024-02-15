package frc.robot.commands.Subystems.Intake;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.RobotContainer;

public class RunIntake extends Command {
    private final Intake intakeSubsystem;
    private final RobotContainer robotContainer;

    public RunIntake(Intake iSubsystem, RobotContainer rContainer) {
        intakeSubsystem = iSubsystem;
        robotContainer = rContainer;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        intakeSubsystem.setIntakePosition(50);
        intakeSubsystem.intakeIntaking.set(TalonSRXControlMode.PercentOutput, 0.95);
    }

    @Override
    public boolean isFinished() {
        if (robotContainer.driverXbox.getRawAxis(3) < 30) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.intakeIntaking.set(TalonSRXControlMode.PercentOutput, 0.0);
        intakeSubsystem.setIntakePosition(0);
    }
}
