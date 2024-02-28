package frc.robot.subsystems.Feeder;

import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANSparkMax;

public class FeederHardware {
    public CANSparkMax feederMotor = new CANSparkMax(13, MotorType.kBrushless);
    public DigitalInput feederBeamBreak = new DigitalInput(0);
}
