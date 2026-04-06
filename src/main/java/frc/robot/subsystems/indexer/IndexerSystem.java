package frc.robot.subsystems.indexer;

import javax.print.attribute.standard.OutputDeviceAssigned;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.subsystems.indexer.IndexerSystemIO.IndexerSystemIOOutputs;
import frc.robot.subsystems.indexer.IndexerSystemIO.IndexerSystemIOMode;

public class IndexerSystem extends SubsystemBase {

    private String name;
    private String inputsName;
    private final IndexerSystemIO io;
    private IndexerSystemIOInputsAutoLogged inputs = new IndexerSystemIOInputsAutoLogged();
    private final IndexerSystemIOOutputs outputs = new IndexerSystemIOOutputs();

    // motor status variables
    private Alert disconnected;

    // feedforward values
    private double kS = 0.0;
    private double kV = 0.0;

    public IndexerSystem(String name, String inputsName, IndexerSystemIO io) {
        this.name = name;
        this.inputsName = inputsName;
        this.io = io;

        disconnected = new Alert(name + "is disconnected", Alert.AlertType.kError);
    }

    public void periodicAfterScheduler() {
        io.applyOutputs(outputs);
    }

    public void runClosedLoopControl(double velocity) {
        outputs.mode = IndexerSystemIOMode.CLOSED_LOOP;
        outputs.velocity = velocity;
        outputs.feedforward = Math.signum(velocity) * kS + velocity * kV;
    }

    public void stop() {
        outputs.mode = IndexerSystemIOMode.BRAKE;
    }

    public void setFeedforward(double kS, double kV) {
        this.kS = kS;
        this.kV = kV;
    }

    public void setFeedback(double kP, double kD) {
        outputs.kP = kP;
        outputs.kD = kD;
    }

    public double getVelocity() {
        return outputs.velocity;
    }

    public double getTorqueCurrent() {
        return inputs.torqueCurrentAmps;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(inputsName, inputs);
        disconnected.set(Robot.showHardwareAlerts());

        if (DriverStation.isDisabled()) {
            outputs.mode = IndexerSystemIOMode.BRAKE;
        }
    }
}
