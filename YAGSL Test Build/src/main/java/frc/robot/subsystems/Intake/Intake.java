//Intake Code not tested yet, everything is theoretical

package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;

public class Intake extends SubsystemBase {

    private RobotContainer robotContainer;
    private IntakeHardware intakeHardware;

    public double intakeSpeed = 0.95;
    public double outtakeSpeed = -intakeSpeed;
    public double intakeLoweredAngle = 0;
    
    //Setup PID Control for intake pivot

    public double getControllerAxis() {
        return robotContainer.driverXbox.getRawAxis(3);
    }

    public void runIntake(double speed) {
        intakeHardware.intakeIntaking.set(TalonSRXControlMode.PercentOutput, speed);
    }

    public void setIntakePosition(double angle) {
        intakeHardware.intakePivotController.setReference(angle/360, CANSparkMax.ControlType.kPosition);
    }

    public double getIntakePosition() {
        return intakeHardware.intakeEncoder.getPosition();
    }

    //Commands for use when constructing Autos
    public Command lowerIntakeAuto() {
        return this.runOnce(() -> setIntakePosition(intakeLoweredAngle));
    }
    public Command raiseIntakeAuto() {
        return this.runOnce(() -> setIntakePosition(0));
    }
    public Command runIntakeAuto() {
        return this.runOnce(() -> intakeHardware.intakeIntaking.set(TalonSRXControlMode.PercentOutput, intakeSpeed));
    }
    public Command stopIntakeAuto() {
        return this.runOnce(() -> intakeHardware.intakeIntaking.set(TalonSRXControlMode.PercentOutput, 0));
    }

    public Intake(IntakeHardware intakeHardware) {
        this.intakeHardware = intakeHardware;
    }
}
