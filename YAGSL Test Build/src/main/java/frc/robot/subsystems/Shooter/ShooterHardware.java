package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

public class ShooterHardware {
    
    //Should either be set to "Neo" or "Kraken"
    public boolean motorIsKraken = false;

    public TalonFX shooterMotor1K;
    public TalonFX shooterMotor2K;
    public CANSparkMax shooterMotor1N = new CANSparkMax(11, MotorType.kBrushless);
    public CANSparkMax shooterMotor2N = new CANSparkMax(12, MotorType.kBrushless);

    //Initialize Motor Controllers for the shooter subsystem for either Neo or Kraken
    public CANSparkMax feederMotor = new CANSparkMax(13, MotorType.kBrushless);
    public CANSparkMax shooterPivot = new CANSparkMax(14, MotorType.kBrushless);
    public SparkPIDController shooterPivotController = shooterPivot.getPIDController();
    public RelativeEncoder shooterAlternateEncoder = shooterPivot.getAlternateEncoder(8192);

    public void setMotorType() {
        if (motorIsKraken == true) {
            shooterMotor1K = new TalonFX(11);
            shooterMotor2K = new TalonFX(12);
        } else {
            shooterMotor1N = new CANSparkMax(11, MotorType.kBrushless);
            shooterMotor2N = new CANSparkMax(12, MotorType.kBrushless);
        }
    }   

    
}
