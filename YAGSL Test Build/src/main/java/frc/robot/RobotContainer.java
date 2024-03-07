// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Subystems.RunClimbDown;
import frc.robot.commands.Subystems.RunClimbUp;
import frc.robot.commands.Subystems.RunFeeder;
import frc.robot.commands.Subystems.RunFeederBack;
import frc.robot.commands.Subystems.RunIntake;
import frc.robot.commands.Subystems.RunOutake;
import frc.robot.commands.Subystems.RunShooter;
import frc.robot.commands.Subystems.ShooterAngle;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.Climb.Climb;
import frc.robot.subsystems.Climb.ClimbHardware;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Feeder.FeederHardware;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeHardware;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterHardware;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.RobotBase;

import java.io.File;
import com.pathplanner.lib.auto.AutoBuilder;
//import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  private final ShooterHardware shooterHardware = new ShooterHardware();
  private final IntakeHardware intakeHardware = new IntakeHardware();
  private final Intake intake = new Intake(intakeHardware);
  private final Shooter shooter = new Shooter(shooterHardware);
  private final Feeder feeder = new Feeder(new FeederHardware(), intake, shooter, shooterHardware, intakeHardware);
  private final Climb climb = new Climb(new ClimbHardware());

  //Create Auto Chooser
  private final SendableChooser<Command> autoChooser;

  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve"));

  // CommandJoystick rotationController = new CommandJoystick(1);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  public CommandXboxController manipXbox = new CommandXboxController(1);
  public XboxController otherManipXbox = new XboxController(1);

  // CommandJoystick driverController   = new CommandJoystick(3);//(OperatorConstants.DRIVER_CONTROLLER_PORT);
  public XboxController driverXbox = new XboxController(0);
  public CommandXboxController driverXboxCommand = new CommandXboxController(0);

  //Controller Triggers
  /*Trigger driverRTHeld = driverXboxCommand.axisGreaterThan(3,0.65);
  Trigger manipRTHeld = manipXbox.axisGreaterThan(3, 0.65);
  Trigger manipRTRelease = manipXbox.axisLessThan(3, 0.65);
  Trigger manipLTHeld = manipXbox.axisGreaterThan(2, 0.65);
  Trigger manipRB = manipXbox.button(6);
  */

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    //Register Named Commands for PathPlanner
    NamedCommands.registerCommand("Run Shooter", shooter.runShooterAuto());
    NamedCommands.registerCommand("Stop Shooter", shooter.stopShooterAuto());
    NamedCommands.registerCommand("Lower Intake", intake.lowerIntakeAuto());
    NamedCommands.registerCommand("Raise Intake", intake.raiseIntakeAuto());
    NamedCommands.registerCommand("Run Intake", intake.runIntakeAuto());
    NamedCommands.registerCommand("Stop Intake", intake.stopIntakeAuto());
    NamedCommands.registerCommand("Shooter Default Angle", shooter.shooterDefaultAuto());
    NamedCommands.registerCommand("Shooter Shooting Angle", shooter.shooterShootAuto());
    NamedCommands.registerCommand("Shooter Amp Angle", shooter.shooterAmpAuto());
    NamedCommands.registerCommand("Run Note Transfer", feeder.runTransferAuto());
    NamedCommands.registerCommand("Stop Note Transfer", feeder.stopTransferCommand());
    NamedCommands.registerCommand("Run Feeder", feeder.runFeederAuto());
    NamedCommands.registerCommand("Stop Feeder", feeder.stopFeederAuto());
    NamedCommands.registerCommand("Zero Gyro", new InstantCommand(drivebase::zeroGyro));

    //Build Auto-Chooser
    //Default auto does nothing, change later if you want a specific default auto
    autoChooser = AutoBuilder.buildAutoChooser("Do Nothing");
    SmartDashboard.putData("Auto Chooser", autoChooser);
    
  
    // Configure the trigger bindings
    configureBindings();

    /*AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
                                                                   () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
                                                                                                OperatorConstants.LEFT_Y_DEADBAND),
                                                                   () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
                                                                                                OperatorConstants.LEFT_X_DEADBAND),
                                                                   () -> MathUtil.applyDeadband(driverXbox.getRightX(),
                                                                                                OperatorConstants.RIGHT_X_DEADBAND),
                                                                   driverXbox::getYButtonPressed,
                                                                   driverXbox::getAButtonPressed,
                                                                   driverXbox::getXButtonPressed,
                                                                   driverXbox::getBButtonPressed);
*/

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the desired angle NOT angular rotation
    Command driveFieldOrientedDirectAngle = drivebase.driveCommand(
        () -> -MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> -MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> -driverXbox.getRightX(),
        () -> -driverXbox.getRightY());

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the angular velocity of the robot

    
    /*Command driveFieldOrientedAnglularVelocity = drivebase.driveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRawAxis(2));
        */

    Command driveFieldOrientedDirectAngleSim = drivebase.simDriveCommand(
        () -> -MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> -MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRawAxis(2));
    

    drivebase.setDefaultCommand(
      !RobotBase.isSimulation() ? driveFieldOrientedDirectAngle : driveFieldOrientedDirectAngleSim);

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    new JoystickButton(driverXbox, 1).onTrue((new InstantCommand(drivebase::zeroGyro)));
    new JoystickButton(driverXbox, 3).onTrue(new InstantCommand(drivebase::addFakeVisionReading));
    new JoystickButton(driverXbox,
                       2).whileTrue(
        Commands.deferredProxy(() -> drivebase.driveToPose(
                                   new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
                              ));

    //Right and Left Trigger Commands
    //driverRTHeld.debounce(0.1, Debouncer.DebounceType.kBoth).onTrue(new RunIntake(intake));
    //manipRTHeld.debounce(0.1, Debouncer.DebounceType.kBoth).onTrue(new RunShooter(shooter));
    new Trigger(() -> (manipXbox.getRawAxis(2)>=0.65)).debounce(0.1, Debouncer.DebounceType.kBoth).onTrue(new RunFeeder(feeder, intake, shooter));
    new Trigger(() -> (manipXbox.getRawAxis(3)>=0.65)).debounce(0.1, Debouncer.DebounceType.kBoth).onTrue(new RunShooter(shooter));
    new Trigger(() -> (driverXbox.getRawAxis(2)>=0.65)).debounce(0.1, DebounceType.kFalling).onTrue(new RunOutake(intake));
    new Trigger(() -> (driverXbox.getRawAxis(3) > 0.65)).debounce(0.5, Debouncer.DebounceType.kFalling).whileTrue(new RunIntake(intake));

    //Button Commands
    //manipRB.debounce(.25, Debouncer.DebounceType.kBoth).onTrue(new ShooterAngle(shooter));
    new JoystickButton(otherManipXbox, 6).onTrue(new ShooterAngle(shooter));
    new JoystickButton(otherManipXbox, 5).onTrue(new RunFeederBack(feeder, intake, shooter));
    new JoystickButton(otherManipXbox, 5).onFalse(feeder.stopTransferCommand());
    new JoystickButton(driverXbox, 6).onTrue(new RunClimbUp(climb));
    new JoystickButton(driverXbox, 5).onTrue(new RunClimbDown(climb));
    new JoystickButton(driverXbox, 6).onFalse(climb.stopClimb());
    new JoystickButton(driverXbox, 5).onFalse(climb.stopClimb());
    new JoystickButton(driverXbox, 7).debounce(0.1, Debouncer.DebounceType.kFalling).onTrue(new InstantCommand(() -> intake.printIntakeAngle()));
    new JoystickButton(driverXbox, 7).debounce(0.1, Debouncer.DebounceType.kFalling).onTrue(new InstantCommand(() -> shooter.printShooterAngle()));

    //    new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */ 
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    //return drivebase.getAutonomousCommand(autoChooser.getSelected().toString(), true);
    return autoChooser.getSelected();
  }

  public Command initShooter1() {
    return new InstantCommand(() -> shooter.setShooterAngle(shooter.shooterAngles[0]));
  }

  public Command initShooter2() {
    return new InstantCommand(() -> shooter.shooterPosition = 0);
  }

  public void setDriveMode()
  {
   // drivebase.setDefaultCommand();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
