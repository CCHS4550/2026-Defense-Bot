package frc.robot.Subsystems.Drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Drive.Module.ModuleIO;
import frc.robot.Subsystems.Drive.Module.Module;

public class Drive extends SubsystemBase {

    private Module[] modules = new Module[4];

    private double xJoystickInput = 0.0;
    private double yJoystickInput = 0.0;
    private double omegaJoystickInput = 0.0;

    ProfiledPIDController angleController =
        new ProfiledPIDController(
            Constants.DriveConstants.ANGLE_KP,
            0.0,
            Constants.DriveConstants.ANGLE_KD,
            new TrapezoidProfile.Constraints(
                Constants.DriveConstants.ANGLE_MAX_VELOCITY,
                Constants.DriveConstants.ANGLE_MAX_ACCELERATION));

    private final PIDController autoDriveToPointController = new PIDController(0.3, 0, 0.1);
    private final PIDController teleopDriveToPointController = new PIDController(0.69, 0, 0.1);
    private Pose2d driveToPointPose = new Pose2d();

    private SwerveModulePosition[] lastModulePos =
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };
    
    public Drive(      
        ModuleIO frModuleIO,
        ModuleIO flModuleIO,
        ModuleIO blModuleIO,
        ModuleIO brModuleIO){
            modules[0] = new Module(frModuleIO,0);
            modules[1] = new Module(flModuleIO,1);
            modules[2] = new Module(blModuleIO,2);
            modules[3] = new Module(brModuleIO,3);

            Robotstate.getInstance().updateBotPoseAndSpeeds(new Pose2d(), new ChassisSpeeds());
            Robotstate.getInstance().updateRawGyroVelo(0.0);

            angleController.enableContinuousInput(-Math.PI, Math.PI);
        }
    
    @Override
    public void periodic() {
        for (var module : modules) {
            module.periodic();
        }
        
        joystickDrive(xJoystickInput, yJoystickInput, omegaJoystickInput);
        Robotstate.getInstance().updateBotPoseAndSpeeds(getPose(), getChassisSpeeds());
        Robotstate.getInstance().updateRawGyroVelo(gyroInputs.yawVelocityRadPerSec);

    }
    public void joystickDrive(double xInput, double yInput, double omegaInput) {
        Translation2d linearVelocity =
            getLinearVelocityFromXY(xInput, yInput, Constants.DriveConstants.deadband);

        double omega = MathUtil.applyDeadband(omegaInput, Constants.DriveConstants.deadband);

        omega = Math.copySign(omega * omega, omega);

        ChassisSpeeds speeds =
            new ChassisSpeeds(
                linearVelocity.getX() * getMaxLinearSpeed(),
                linearVelocity.getY() * getMaxLinearSpeed(),
                omega * getMaxAngularSpeed());
        boolean isFlipped =
            DriverStation.getAlliance().isPresent()
                && DriverStation.getAlliance().get() == Alliance.Red;

        runVelocity(
            ChassisSpeeds.fromFieldRelativeSpeeds(
                speeds, isFlipped ? getRotation().plus(new Rotation2d(Math.PI)) : getRotation()));
  }
}   