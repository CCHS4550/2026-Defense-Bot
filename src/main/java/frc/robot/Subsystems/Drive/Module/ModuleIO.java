package frc.robot.Subsystems.Drive.Module;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.AutoLog;

public interface ModuleIO {
  @AutoLog
  class ModuleIOInputs {
    public double driveVelocityRotationsPerSecond = 0.0;
    public double driveSupplyCurrentAmps = 0.0;
    public double driveStatorCurrentAmps = 0.0;
    public double driveAppliedVolts = 0.0;
    public double driveTemperature = 0.0;

    public double steerVelocityRotationsPerSecond = 0.0;
    public double steerSupplyCurrentAmps = 0.0;
    public double steerStatorCurrentAmps = 0.0;
    public double steerAppliedVolts = 0.0;
    public double steerTemperature = 0.0;
  }

  public static enum ModuleIOOutputMode {
    COAST,
    BRAKE,
    DRIVE,
    CHARACTERIZE
  }

  public static class ModuleIOOutputs {
    public ModuleIOOutputMode mode = ModuleIOOutputMode.COAST;
    public double driveVelocityRotationsPerSecond = 0.0;
    public double driveFeedforward = 0.0;
    public double driveCharacterizationOutput = 0.0;
    public Rotation2d turnRotation = Rotation2d.kZero;
    public boolean turnNeutral = false;
  }

  default void updateInputs(ModuleIOInputs inputs) {}

  default void applyOutputs(ModuleIOOutputs outputs) {}

  default double getDrivePosition() {
    return 0;
  }

  default double getTurnPosition() {
    return 0;
  }

  default double getDriveSpeed() {
    return 0;
  }

  default double getTurnSpeed() {
    return 0;
  }

  default double getDriveVoltage() {
    return 0;
  }

  default double getTurnVoltage() {
    return 0;
  }

  default double getAbsoluteEncoderRadiansOffset() {
    return 0;
  }

  default double getAbsoluteEncoderRadiansNoOffset() {
    return 0;
  }

  default void resetEncoders() {}

  default void resetTurnEncoder() {}

  default SwerveModuleState getState() {
    return new SwerveModuleState();
  }

  default void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {}

  default void setDriveVelocity(double velocity) {}

  default void setTurnPosition(DoubleSupplier angle) {}

  default void stop() {}

  default void setDriveVoltage(double voltage) {}

  default void setTurnVoltage(double voltage) {}

  default void driveAndTurn(double driveSpeed, double turnSpeed) {}

  default String getName() {
    return "";
  }

  default void setName(String name) {}

  default double getTurnEncoderDistance() {
    return 0;
  }

  default double getTurnEncoderVelocity() {
    return 0;
  }

  default double getDriveEncoderDistance() {
    return 0;
  }

  default double getDriveEncoderVelocity() {
    return 0;
  }
}
