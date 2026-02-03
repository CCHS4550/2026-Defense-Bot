package frc.robot.ControlSchemes;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;
// import frc.robot.Subsystems.Drive.Drive;
import java.util.function.DoubleSupplier;

/** how a controller interacts with the drive train */
public class DriveScheme {
  // slow mode or fast mode
  private static DoubleSupplier driveSpeedModifier = () -> 0.5;

  static Transform2d tagTransform =
      new Transform2d(
          Units.inchesToMeters(20), Units.inchesToMeters(50), Rotation2d.fromDegrees(150));
  static Transform2d tagTransform2 =
      new Transform2d(
          Units.inchesToMeters(0), Units.inchesToMeters(60), Rotation2d.fromDegrees(60));
  static Pose2d testPose =
      new Pose2d(
          Constants.VisionConstants.aprilTagLayout
              .getTagPose(20)
              .get()
              .toPose2d()
              .plus(tagTransform)
              .getTranslation(),
          Rotation2d.fromDegrees(60));
  static Pose2d testPoseOG =
      Constants.VisionConstants.aprilTagLayout.getTagPose(20).get().toPose2d().plus(tagTransform2);

  /*
    public static void configure(Drive drive, CommandXboxController controller) {
      // default command will periodically run in drive train, in this case it periodically updates
      // our joystick values
      Command driveDefaultCommand =
          new ParallelCommandGroup(
              Commands.run(
                  () ->
                      drive.setXJoystickInput(
                          controller.getLeftX() * driveSpeedModifier.getAsDouble())),
              Commands.run(
                  () ->
                      drive.setYJoystickInput(
                          controller.getLeftY() * driveSpeedModifier.getAsDouble())),
              Commands.run(
                  () ->
                      drive.setOmegaJoystickInput(
                          controller.getRightX() * driveSpeedModifier.getAsDouble())));
      driveDefaultCommand.addRequirements(drive);
      drive.setDefaultCommand(driveDefaultCommand);

      // set button bindings
      configureButtons(controller, drive);
    }
  */
}
