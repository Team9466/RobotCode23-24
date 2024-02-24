//Intake Code not tested yet, everything is theoretical

package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;

public class Intake extends SubsystemBase {

    private IntakeHardware intakeHardware;
    public XboxController driverXbox = new XboxController(0);

    public double intakeSpeed = -0.95;
    public double outtakeSpeed = -intakeSpeed;
    public double[] intakeAngles = {-0.02,-0.475};
    
    //Setup PID Control for intake pivot

    public double getControllerAxis() {
        return driverXbox.getRawAxis(3);
    }

    public void runIntake(double speed) {
        intakeHardware.intakeIntaking.set(speed);
    }

    public void setIntakePosition(double angle) {
        intakeHardware.intakePivotController.setReference(angle, CANSparkMax.ControlType.kPosition);
    }

    public double getIntakePosition() {
        return intakeHardware.intakeEncoder.getPosition();
    }

    //Commands for use when constructing Autos
    public Command lowerIntakeAuto() {
        return this.runOnce(() -> setIntakePosition(intakeAngles[1]));
    }
    public Command raiseIntakeAuto() {
        return this.runOnce(() -> setIntakePosition(intakeAngles[0]));
    }
    public Command runIntakeAuto() {
        return this.runOnce(() -> intakeHardware.intakeIntaking.set(intakeSpeed));
    }
    public Command stopIntakeAuto() {
        return this.runOnce(() -> intakeHardware.intakeIntaking.set(0));
    }

    public void printIntakeAngle() {
        System.out.println("Intake Angle: " + intakeHardware.intakeEncoder.getPosition());
    }

    public Intake(IntakeHardware intakeHardware) {
        this.intakeHardware = intakeHardware;
    }
}
