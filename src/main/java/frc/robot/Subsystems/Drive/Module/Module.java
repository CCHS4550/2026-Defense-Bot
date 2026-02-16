package frc.robot.Subsystems.Drive.Module;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Module extends SubsystemBase{
    ModuleIO io;
    int index;
    public Module (ModuleIO io, int index) {
        this.io = io;
        this.index = index;
    }
}
