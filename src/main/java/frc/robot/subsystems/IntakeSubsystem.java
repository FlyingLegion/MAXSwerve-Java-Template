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

    private SparkMax m_intakeElevator;
    private SparkMax m_intakeWheels;

    public IntakeSubsystem(RobotContainer localRobotContainer) {

        m_intakeElevator = new SparkMax(IntakeConstants.kIntakeMovementMotorID, MotorType.kBrushless);
        m_intakeWheels = new SparkMax(IntakeConstants.kIntakeWheelsMotorID, MotorType.kBrushless);
    }

    @Override
    public void periodic() {
        
    }

    public void stopIntake() {
        m_intakeElevator.set(0);
        m_intakeWheels.set(0);
    }

    public void moveUpIntake() {
        m_intakeElevator.set(-1);
        System.out.println("Up");
    }

    public void moveDownIntake() {
        m_intakeElevator.set(1);
        System.out.println("Down");
    }

    public void runIntakeWheel() {
        m_intakeWheels.set(-1);
        System.out.println("Hey Levie");
    }

    public Command stopIntakeCommand() {
        return this.run(() -> stopIntake());
    }

    public Command moveUpIntakeCommand(){
        return this.run(() -> moveUpIntake());
    }

    public Command moveDownIntakeCommand(){
        return this.run(() -> moveDownIntake());
    } 

    
}
