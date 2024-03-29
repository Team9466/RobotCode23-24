package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

public class ShooterHardware {
    
    //Should either be set to "Neo" or "Kraken"
    public boolean motorIsKraken = true;
    private double[] pivotPID = {2.10, 0, 0.21};

    public TalonFX shooterMotor1K;
    public TalonFX shooterMotor2K;
    public TalonFXConfigurator shooterConfigurator1;
    public TalonFXConfigurator shooterConfigurator2;
    public CANSparkMax shooterMotor1N;
    public CANSparkMax shooterMotor2N;

    //Initialize Motor Controllers for the shooter subsystem for either Neo or Kraken
    public CANSparkMax shooterPivot = new CANSparkMax(14, MotorType.kBrushless);
    public SparkPIDController shooterPivotController = shooterPivot.getPIDController();
    public RelativeEncoder shooterAlternateEncoder = shooterPivot.getAlternateEncoder(8192);
    public TalonFXConfiguration shooterConfig = new TalonFXConfiguration();
    public TalonFXConfiguration motor1PID = new TalonFXConfiguration();
    public TalonFXConfiguration motor2PID = new TalonFXConfiguration();

    private double[] shooterPIDS = {0.025, 0, 0.0005, 0.005};
    private double[] shooterV = {0.0105, 0.0110};
    private double[] shooterP = {0.021, 0.021};

    public void setMotorType() {
        if (motorIsKraken == true) {
            shooterMotor1K = new TalonFX(11);
            shooterMotor2K = new TalonFX(12);
            shooterConfigurator1 = shooterMotor1K.getConfigurator();
            shooterConfigurator2 = shooterMotor2K.getConfigurator();
        } else {
            shooterMotor1N = new CANSparkMax(11, MotorType.kBrushless);
            shooterMotor2N = new CANSparkMax(12, MotorType.kBrushless);
        }
    }   

    public ShooterHardware() {
        setMotorType();
        shooterConfig.CurrentLimits.StatorCurrentLimit = 100;
        shooterConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        shooterConfig.CurrentLimits.SupplyCurrentLimit = 80;
        shooterConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        shooterConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        shooterConfig.Slot0.kP = shooterPIDS[0];
        shooterConfig.Slot0.kI = shooterPIDS[1];
        shooterConfig.Slot0.kD = shooterPIDS[2];
        shooterConfig.Slot0.kS = shooterPIDS[3];
        motor1PID.Slot0.kV = shooterV[0];
        motor2PID.Slot0.kV = shooterV[1];
        motor1PID.Slot0.kP = shooterP[0];
        motor2PID.Slot0.kP = shooterP[1];

        if (motorIsKraken = true) {
            shooterConfigurator1.apply(shooterConfig);
            shooterConfigurator2.apply(shooterConfig);
            shooterConfigurator1.apply(motor1PID);
            shooterConfigurator2.apply(motor2PID);
            shooterConfigurator1.refresh(shooterConfig);
            shooterConfigurator2.refresh(shooterConfig);
            shooterConfigurator1.refresh(motor1PID);
            shooterConfigurator2.refresh(motor2PID);
            shooterMotor2K.setInverted(false);
            shooterMotor1K.setInverted(false);
            System.out.println("Shooter Config Applied");
        }
        
        shooterPivotController.setP(pivotPID[0]);
        shooterPivotController.setI(pivotPID[1]);
        shooterPivotController.setD(pivotPID[2]);
        shooterPivotController.setFeedbackDevice(shooterAlternateEncoder);
        shooterPivotController.setPositionPIDWrappingEnabled(true);

    }
    
}
