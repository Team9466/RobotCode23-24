//Intake Code not tested yet, everything is theoretical

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

public class Intake extends SubsystemBase {
    private CANSparkMax intakePivot = new CANSparkMax(9, MotorType.kBrushless);
    private TalonSRX intakeIntaking = new TalonSRX(10);
    private RelativeEncoder intakeEncoder = intakePivot.getEncoder();
    public SparkPIDController intakePivotController = intakePivot.getPIDController();

    public Command setIntakePosition(double Angle) {
        return this.runOnce(() -> intakePivotController.setReference(Angle, CANSparkMax.ControlType.kPosition));
    }

    public Command runIntake(boolean forward) {
        if (forward == true) {
           return this.runOnce(() -> intakeIntaking.set(TalonSRXControlMode.PercentOutput, 0.95));
        }
        else {
            return this.runOnce(() -> intakeIntaking.set(TalonSRXControlMode.PercentOutput, -0.95));
        }
    }
    
}
