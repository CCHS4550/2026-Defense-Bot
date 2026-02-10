package frc.robot.Subsystems.Drive.Module;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.AnalogInput;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkClosedLoopController.ArbFFUnits;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogEncoder;
import frc.robot.Constants;

public class ModuleIOSpark implements ModuleIO 
{
  private final ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();

  private final SparkMax sparkDrive;
  private final SparkMax sparkTurn;

  private final RelativeEncoder driveEncoder;
  private final AbsoluteEncoder turnEncoder;
  private final AnalogInput absoluteAnalogInput;
  private final AnalogEncoder absoluteEncoder;

  private final Rotation2d rotationOffset;

  private final SparkClosedLoopController driveController;
  private final SparkClosedLoopController turnController;

  private final PIDController turnPID = new PIDController(
                                              Constants.DriveConstants.turnKp, 0.0, 
                                              Constants.DriveConstants.turnKd);

  private final int module; // 0 front right 1 front left 2 back left 3 back right <---- IMPORTANT

  public ModuleIOSpark(int module) 
  {
    this.module = module;

    switch (module)
    {
      case 0:
        sparkDrive = new SparkMax(Constants.DriveConstants.frontRightDriveCanId, MotorType.kBrushless);
        sparkTurn = new SparkMax(Constants.DriveConstants.frontRightTurnCanId, MotorType.kBrushless);
        absoluteEncoder = new AnalogEncoder(Constants.DriveConstants.frontRightTurnEncoder);
        rotationOffset = Constants.DriveConstants.frontRightOffset;
        break;
      default:
        sparkDrive = new SparkMax(9999999, MotorType.kBrushless);
        sparkTurn = new SparkMax(67676767, MotorType.kBrushless);
        absoluteEncoder = new AnalogEncoder(67676767);
        rotationOffset = new Rotation2d(67676767);
    }

    turnPID.enableContinuousInput(0, Math.PI);

    absoluteAnalogInput = sparkTurn.getAnalog();
    turnEncoder = sparkTurn.getAbsoluteEncoder();
    driveEncoder = sparkDrive.getEncoder();

    driveController = sparkDrive.getClosedLoopController();
    turnController = sparkTurn.getClosedLoopController();

    
  }

  @Override
  public void updateInputs(ModuleIOInputs inputs) 
  {
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

  @Override
  public void setDriveVelocity(double velo) {
    double ffvolts =
        Constants.DriveConstants.driveKs * Math.signum(velo)
            + Constants.DriveConstants.driveKv * velo;
    driveController.setReference(
        velo, ControlType.kVelocity, ClosedLoopSlot.kSlot0, ffvolts, ArbFFUnits.kVoltage);
  }

  @Override
  public void setTurnPosition(Rotation2d rotation) {
    double setPoint =
        MathUtil.inputModulus(
            rotation.plus(rotationOffset).getRadians(),
            Constants.DriveConstants.turnPIDMinInput,
            Constants.DriveConstants.turnPIDMaxInput);
    double volts =
        turnPID.calculate(
            (Rotation2d.fromRotations(absoluteEncoder.get()).plus(Rotation2d.kPi)).getRadians(),
            setPoint);
    System.out.println(volts);
    setTurnVoltage(volts);
  }
}
