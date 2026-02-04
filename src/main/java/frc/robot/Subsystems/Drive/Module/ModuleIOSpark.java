package frc.robot.Subsystems.Drive.Module;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.AnalogInput;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class ModuleIOSpark implements ModuleIO {
  private final ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();

  private final SparkMax sparkDrive;
  private final SparkMax sparkTurn;

  private final RelativeEncoder driveEncoder;
  private final AbsoluteEncoder turnEncoder;
  private final AnalogInput absoluteAnalogInput;

  private final SparkClosedLoopController driveController;
  private final SparkClosedLoopController turnController;

  private final int module;

  public ModuleIOSpark(int module) {
    this.module = module;

    sparkDrive = new SparkMax(module, MotorType.kBrushless);
    sparkTurn = new SparkMax(module, MotorType.kBrushless);

    absoluteAnalogInput = sparkTurn.getAnalog();
    turnEncoder = sparkTurn.getAbsoluteEncoder();
    driveEncoder = sparkDrive.getEncoder();

    driveController = sparkDrive.getClosedLoopController();
    turnController = sparkTurn.getClosedLoopController();
  }

  @Override
  public void updateInputs(ModuleIOInputs inputs) {
    inputs.driveVelocityRotationsPerSecond = this.inputs.driveVelocityRotationsPerSecond;
    inputs.driveSupplyCurrentAmps = this.inputs.driveSupplyCurrentAmps;
    inputs.driveStatorCurrentAmps = this.inputs.driveStatorCurrentAmps;
    inputs.driveAppliedVolts = this.inputs.driveAppliedVolts;
    inputs.driveTemperature = this.inputs.driveTemperature;

    inputs.steerVelocityRotationsPerSecond = this.inputs.steerVelocityRotationsPerSecond;
    inputs.steerSupplyCurrentAmps = this.inputs.steerSupplyCurrentAmps;
    inputs.steerStatorCurrentAmps = this.inputs.steerStatorCurrentAmps;
    inputs.steerAppliedVolts = this.inputs.steerAppliedVolts;
    inputs.steerTemperature = this.inputs.steerTemperature;
  }
  
  @Override
  public void applyOutputs(ModuleIOOutputs outputs) {}

  @Override
  public void stop() {
    sparkDrive.stopMotor();
    sparkTurn.stopMotor();
  }

  @Override
  public void setDriveVoltage(double voltage) {
    sparkDrive.setVoltage(voltage);
  }

  @Override
  public void setTurnVoltage(double voltage) {
    sparkTurn.setVoltage(voltage);
  }

  @Override
  public double getDriveVoltage() {
    return sparkDrive.getBusVoltage();
  }

  @Override
  public double getTurnVoltage() {
    return sparkTurn.getBusVoltage();
  }
}
