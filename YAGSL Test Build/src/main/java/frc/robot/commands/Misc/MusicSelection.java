package frc.robot.commands.Misc;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter.MusicalMotors;

public class MusicSelection extends Command{
    
    private MusicalMotors musicalMotors;

    public MusicSelection(MusicalMotors musicalMotors) {
        this.musicalMotors = musicalMotors;
    }

    @Override
    public void initialize() {
        if (musicalMotors.selectedTrack >= (musicalMotors.trackList.length-1)) {
            musicalMotors.selectedTrack = 0;
            musicalMotors.shooterOrchestra.loadMusic(musicalMotors.trackList[musicalMotors.selectedTrack]);
        } else {
            musicalMotors.selectedTrack += 1;
            musicalMotors.shooterOrchestra.loadMusic(musicalMotors.trackList[musicalMotors.selectedTrack]);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
