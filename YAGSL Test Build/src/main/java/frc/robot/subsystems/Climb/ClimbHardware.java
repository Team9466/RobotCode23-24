package frc.robot.subsystems.Climb;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ClimbHardware {
    public CANSparkMax climbMotor1 = new CANSparkMax(16, MotorType.kBrushless);
    public CANSparkMax climbMotor2 = new CANSparkMax(17, MotorType.kBrushless);
}
