package frc.robot;

import static frc.robot.Constants.VisionConstants.*;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// import frc.robot.Subsystems.Drive.Drive;

public class RobotContainer {

  // subclasses of the robot
  // drive sim
  // private SwerveDriveSimulation driveSimulation = null;

  // controller used
  CommandXboxController primaryController = new CommandXboxController(0);

  // autochooser
  // private final LoggedDashboardChooser<Command> autoChooser;

  public RobotContainer() {}
}
