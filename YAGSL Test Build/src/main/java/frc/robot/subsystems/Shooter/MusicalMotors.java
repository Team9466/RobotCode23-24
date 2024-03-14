package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.Orchestra;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MusicalMotors extends SubsystemBase {
   
    public Orchestra shooterOrchestra = new Orchestra();

    public String[] trackList = {"the_funny.chrp"};
    public int selectedTrack = 0;

    public Command playMusic() {
        return this.runOnce(() -> shooterOrchestra.play()).andThen(this.runOnce(() -> System.out.println("Playing Music")));
    }
    public Command stopMusic() {    
        return this.runOnce(() -> shooterOrchestra.stop());
    }
    
    public MusicalMotors(ShooterHardware shooterHardware) {
        shooterOrchestra.addInstrument(shooterHardware.shooterMotor1K, 1);
        shooterOrchestra.addInstrument(shooterHardware.shooterMotor2K, 2);
        shooterOrchestra.loadMusic(trackList[0]);
    }
}

