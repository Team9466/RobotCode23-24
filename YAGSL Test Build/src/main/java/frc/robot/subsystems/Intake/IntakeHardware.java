package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkPIDController;

public class IntakeHardware {
    
        //Initialize Motor Controllers for Intake
    public CANSparkMax intakePivot = new CANSparkMax(9, MotorType.kBrushless);
    public TalonSRX intakeIntaking = new TalonSRX(10);
    public SparkAbsoluteEncoder intakeEncoder = intakePivot.getAbsoluteEncoder(Type.kDutyCycle);
    public SparkPIDController intakePivotController = intakePivot.getPIDController();
    
}
