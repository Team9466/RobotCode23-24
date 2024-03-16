package frc.robot.commands.Subystems;

import org.photonvision.PhotonUtils;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Shooter.Shooter;

public class AutoTargeting extends Command {
    
    private final PhotonVision photonVision;
    private final Shooter shooter;
    private final Feeder feeder;

    private boolean gotTargetInfo;
    private boolean angleAdjusted;

    //Values in meters
    private double targetHeight = 1.42;
    private double range;
    private double adjustedAimAngle;

    //Initial Offset is factored in including conversion factor
    private double encoderConversionFactor = 40/21;
    private double initialOffset = 0.3;

    //Aim height is the height above the april tag to aim, in meters
    private double aimHeight = 0.59;
    private double shooterHeight = 0.7747;

    public AutoTargeting(PhotonVision photonVision, Shooter shooter, Feeder feeder) {
        this.photonVision = photonVision;
        this.shooter = shooter;
        this.feeder = feeder;
        addRequirements(shooter, photonVision, feeder);
    }

    @Override
    public void initialize() {
        shooter.autoTargetingEnabled = true;
        shooter.runShooterMotors();
    }

    @Override
    public void execute() {
        if (photonVision.frontCam.getLatestResult().hasTargets() == true && gotTargetInfo == false) {
            range = PhotonUtils.calculateDistanceToTargetMeters(
                photonVision.frontCamHeight,
                targetHeight,
                photonVision.frontCamPitch,
                Units.degreesToRadians(photonVision.frontCam.getLatestResult().getBestTarget().getPitch())
            );
            gotTargetInfo = true;
        }
        if (gotTargetInfo = true && angleAdjusted == false) {
            //aim angle is in rotations
            double realAimAngle = Math.tan((targetHeight+aimHeight-shooterHeight)/range)/(2*Math.PI);
            adjustedAimAngle = initialOffset - (realAimAngle * encoderConversionFactor);
            shooter.setShooterAngle(adjustedAimAngle);
            angleAdjusted = true;
        }
        if (angleAdjusted = true) {
            if (Math.abs(adjustedAimAngle - shooter.getShooterPosition()) <= 0.05) {
                feeder.runFeederMotor(feeder.feederSpeed);
            }
        }
    }

    @Override
    public boolean isFinished() {
        if (shooter.getAButtonPressed() == false) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopShooterMotors();
        feeder.runFeederMotor(0);
        shooter.autoTargetingEnabled = false;
        shooter.setShooterAngle(shooter.shooterAngles[shooter.shooterPosition]);
    }
}