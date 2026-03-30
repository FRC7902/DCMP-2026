package frc.robot.subsystems.indexer;

import org.littletonrobotics.junction.AutoLog;

public interface IndexerSystemIO {
    @AutoLog
    public static class IndexerSystemIOInputs {
        public boolean connected;
        public double positionRads;
        public double velocityRadsPerSec;
        public double appliedVoltage;
        public double supplyCurrentAmps;
        public double torqueCurrentAmps;
        public double tempCelsius;
    }

    public enum IndexerSystemIOMode {
        BRAKE,
        COAST,
        VOLTAGE_CONTROL,
        CLOSED_LOOP
    }

    public static class IndexerSystemIOOutputs {
        public IndexerSystemIOMode mode = IndexerSystemIOMode.BRAKE;
        // Voltage control
        public double appliedVoltage = 0.0;

        public double velocity = 0.0;
        public double kP = 0.0;
        public double kD = 0.0;
        public double feedforward = 0.0;

        public boolean brakeModeEnabled = true;
    }
    public void updateInputs (IndexerSystemIOInputs inputs);

    public void applyOutputs (IndexerSystemIOOutputs outputs);
}
