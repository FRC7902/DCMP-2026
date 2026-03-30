package frc.robot.subsystems.indexer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.indexer.IndexerSystemIO;
import frc.robot.subsystems.indexer.IndexerSystemIO.IndexerSystemIOOutputs;

public class IndexerSystem extends SubsystemBase {
    private final IndexerSystemIO io;
    private final IndexerSystemIOOutputs outputs;

    public IndexerSystem(IndexerSystemIO io, IndexerSystemIOOutputs outputs) {
        this.io = io;
        this.outputs = outputs;
    }
}
