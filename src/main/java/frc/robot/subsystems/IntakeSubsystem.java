package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants.*;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.math.controller.PIDController; 



public class IntakeSubsystem extends SubsystemBase{
    public RobotContainer localRobotContainer;

    public double elevatorVelocity;
    public double intakeVelocity;
    
    private SparkMax m_intakeElevator;
    private SparkMax m_intakeWheels;

    public IntakeSubsystem(RobotContainer m_robotContainer) {
        localRobotContainer = m_robotContainer;

        m_intakeElevator = new SparkMax(IntakeConstants.kIntakeMovementMotorID, MotorType.kBrushless);
        m_intakeWheels = new SparkMax(IntakeConstants.kIntakeWheelsMotorID, MotorType.kBrushless);

        elevatorVelocity = 0;
        intakeVelocity = 0;
    }

    @Override
    public void periodic() {
        m_intakeElevator.set(elevatorVelocity);
        m_intakeWheels.set(intakeVelocity);
    }

    /**
     * <b>INTAKE</b>
     * <p>runAll function, call through runAllCommand(v1,v2)
     */
    public void runAll(double v1, double v2) {
        elevatorVelocity = v1;
        intakeVelocity = v2;
    }

    /**
     * <b>INTAKE</b>
     * <p>This command is capable of running all the different parts of the intake at once
     * <p>v1 = Elevator Speed
     * <p>v2 = Wheel Speed
     */
    public Command runAllCommand(double v1, double v2) {
        return this.run(() -> runAll(v1,v2));
    }

    /**
     * <b>INTAKE</b>
     * <p>stopAll function, call through Intake stopAllCommand()
     */
    public void stopAll() {
        elevatorVelocity = 0;
        intakeVelocity = 0;
    }

    /**
     * <b>INTAKE</b>
     * <p>Stops all different parts of the intake at once
     * <p>Same as Intake runAllCommand(0,0)
     */
    public Command stopAllCommand() {
        return this.run(() -> stopAll());
    }

    /** Positive = Down<p>Negative = Up */
    public void runIntakeElevator(double velo) {
        elevatorVelocity = velo;
    }

    /** Positive = Down<p>Negative = Up */
    public Command intakeElevatorCommand(double velo){
        return this.run(() -> runIntakeElevator(velo));
    }

    /** Positive = Out<p>Negative = In */
    public void runIntakeWheel(double velo) {
        intakeVelocity = velo;
    }

    /** Positive = Out<p>Negative = In */
    public Command intakeWheelCommand(double velo){
        return this.run(() -> runIntakeWheel(velo));
    }
    
}
