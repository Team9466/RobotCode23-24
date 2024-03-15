package frc.robot.commands.Subystems;

import org.photonvision.PhotonUtils;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class AutoTargeting extends Command {
    
    private final PhotonVision photonVision;
    private final Shooter shooter;
    private final SwerveSubsystem swerveSubsystem;
    private final Feeder feeder;

    private boolean gotTargetInfo;
    private boolean headingAdjusted;
    private boolean angleAdjusted;

    //Values in meters
    private double targetHeight = 1.42;
    private double range;
    private double angleOffset;
    private double angleConversionFactor;
    private double realAimAngle;
    private double adjustedAimAngle;

    //Initial Offset is factored in including conversion factor
    private double encoderConversionFactor = 40/21;
    private double initialOffset = 0.3;

    //Aim height is the height above the april tag to aim, in meters
    private double aimHeight = 0.59;
    private double shooterHeight;

    public AutoTargeting(PhotonVision photonVision, Shooter shooter, SwerveSubsystem swerveSubsystem, Feeder feeder) {
        this.photonVision = photonVision;
        this.shooter = shooter;
        this.swerveSubsystem = swerveSubsystem;
        this.feeder = feeder;
        addRequirements(shooter, photonVision, swerveSubsystem, feeder);
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
            angleOffset = photonVision.frontCam.getLatestResult().getBestTarget().getYaw();
            gotTargetInfo = true;
        }
        if (gotTargetInfo = true && headingAdjusted == false ) {
            double adjustedAngleOffset = swerveSubsystem.getHeading().getRotations() + (angleOffset * angleConversionFactor);
            swerveSubsystem.drive(new Translation2d(), adjustedAngleOffset, true);
            headingAdjusted = true;
        }
        if (gotTargetInfo = true && headingAdjusted == true && angleAdjusted == false) {
            //aim angle is in rotations
            realAimAngle = Math.tan((targetHeight+aimHeight-shooterHeight)/range)/(2*Math.PI);
            adjustedAimAngle = initialOffset - (realAimAngle * encoderConversionFactor);
            shooter.setShooterAngle(adjustedAimAngle);
            angleAdjusted = true;
        }
        if (angleAdjusted = true && headingAdjusted == true) {
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