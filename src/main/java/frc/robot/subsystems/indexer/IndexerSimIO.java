// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.indexer;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

/** Add your docs here. */
public class IndexerSimIO implements IndexerSystemIO{
    private final DCMotorSim motorSim;
    private final DCMotor motor;
    private double appliedVoltage;

    public IndexerSimIO(DCMotor motorModel, double reduction, double moi) {
        this.motor = motorModel;
        this.motorSim = new DCMotorSim(LinearSystemId.createDCMotorSystem(motorModel, reduction, moi),motorModel
        );
    }
    @Override
    public void updateInputs(IndexerSystemIOInputs inputs) {
        motorSim.update(0.02);
        inputs.connected = true;
        inputs.positionRads = motorSim.getAngularPositionRad();
        inputs.velocityRadsPerSec = motorSim.getAngularVelocityRadPerSec();
        inputs.appliedVoltage = appliedVoltage;
        inputs.supplyCurrentAmps = motorSim.getCurrentDrawAmps();
        inputs.torqueCurrentAmps = motor.getCurrent(motorSim.getAngularVelocityRadPerSec(),appliedVoltage);
        inputs.tempCelsius = 25.0;
    }
    @Override
    public void applyOutputs(IndexerSystemIOOutputs outputs) {
        if (DriverStation.isDisabled()) {
            appliedVoltage = 0.0;
        } else {
            appliedVoltage = MathUtil.clamp(outputs.appliedVoltage, -12, 12);
        }
        motorSim.setInputVoltage(appliedVoltage);
    }

}
