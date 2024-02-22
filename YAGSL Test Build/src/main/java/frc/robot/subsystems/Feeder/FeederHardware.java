package frc.robot.subsystems.Feeder;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;


public class FeederHardware {
    public CANSparkMax feederMotor = new CANSparkMax(15, MotorType.kBrushless);
}
