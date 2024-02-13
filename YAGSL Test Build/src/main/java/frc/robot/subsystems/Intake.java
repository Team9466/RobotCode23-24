//Intake Code not tested yet, everything is theoretical

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

public class Intake {
    private CANSparkMax intakePivot = new CANSparkMax(9, MotorType.kBrushless);
    private TalonSRX intakeIntaking = new TalonSRX(10);
    private RelativeEncoder intakeEncoder = intakePivot.getEncoder();
    private SparkPIDController intakePivotController = intakePivot.getPIDController();

    public void setIntakePosition(double Angle) {
        intakePivotController.setReference(Angle, CANSparkMax.ControlType.kPosition);
    }

    public void runIntake(boolean forward) {
        if (forward == true) {
            intakeIntaking.set(TalonSRXControlMode.PercentOutput, 0.95);
        }
        else {
            intakeIntaking.set(TalonSRXControlMode.PercentOutput, -0.95);
        }
    }
}
