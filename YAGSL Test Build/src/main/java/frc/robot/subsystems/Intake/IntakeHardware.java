package frc.robot.subsystems.Intake;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

public class IntakeHardware {
    
    private double[] pivotPID = {4.25, 0, 0.6};
    private double[] runPID = {0.5, 0, 0.1};

    //Initialize Motor Controllers for Intake
    public CANSparkMax intakePivot = new CANSparkMax(9, MotorType.kBrushless);
    public CANSparkMax intakeIntaking = new CANSparkMax(10, MotorType.kBrushless);
    public RelativeEncoder intakeEncoder = intakePivot.getAlternateEncoder(8192);
    public SparkPIDController intakePivotController = intakePivot.getPIDController();
    public SparkPIDController intakeRunController = intakeIntaking.getPIDController();
    
    public IntakeHardware() {
        
        intakePivotController.setP(pivotPID[0]);
        intakePivotController.setI(pivotPID[1]);
        intakePivotController.setD(pivotPID[2]);
        intakeRunController.setP(runPID[0]);
        intakeRunController.setP(runPID[1]);
        intakeRunController.setP(runPID[2]);
        intakePivotController.setFeedbackDevice(intakeEncoder);
    }
}
