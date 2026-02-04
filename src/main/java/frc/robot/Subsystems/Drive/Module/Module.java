package frc.robot.Subsystems.Drive.Module;

public class Module{
    private final ModuleIO io;

    private final ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();

    private int index; //which module it is 0-3

    public Module(ModuleIO io, int index){
        this.io = io;
        this.index = index;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
    }
    public void runSwerveState(SwerveModuleState state){
        
    }
}