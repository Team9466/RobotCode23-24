//Intake Code not tested yet, everything is theoretical

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;

public class Intake extends SubsystemBase {
    //Initialize Motor Controllers for Intake
    public CANSparkMax intakePivot = new CANSparkMax(9, MotorType.kBrushless);
    public TalonSRX intakeIntaking = new TalonSRX(10);
    public SparkAbsoluteEncoder intakEncoder = intakePivot.getAbsoluteEncoder(Type.kDutyCycle);
    
    public double intakeSpeed = 0.95;
    
    //Setup PID Control for intake pivot
    public SparkPIDController intakePivotController = intakePivot.getPIDController();

    public void setIntakePosition(double Angle) {
        intakePivotController.setReference(Angle, CANSparkMax.ControlType.kPosition);
    }
}
