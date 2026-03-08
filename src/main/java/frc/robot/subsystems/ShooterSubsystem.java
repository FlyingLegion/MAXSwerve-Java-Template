package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.math.controller.PIDController; 



import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    public RobotContainer localRobotContainer;

    private double currentShooterSpeed;
    private PIDController pidConfigs;
    public double targetVelocity; 
    public double processVelocity;
    
    private SparkMax m_shooter;
    private SparkMax m_upperProcess;
    private SparkMax m_lowerProcess;


    //shooter subsystem brance
    public ShooterSubsystem(RobotContainer localRobotContainer) {
        pidConfigs = new PIDController(0, 0, 0);
        m_shooter = new SparkMax(ShooterConstants.kShooterMotorID, MotorType.kBrushless);
        m_upperProcess = new SparkMax(ShooterConstants.kUpperProcessID, MotorType.kBrushless);
        m_lowerProcess = new SparkMax(ShooterConstants.kLowerProcessID, MotorType.kBrushless);
        targetVelocity = 0;
    }

    @Override
    public void periodic() {
        m_shooter.set(targetVelocity);
        m_lowerProcess.set(-processVelocity);
        m_upperProcess.set(processVelocity);

    }


    public void revUp() {
        targetVelocity = -1;
    }

    public void runProcess() {
        processVelocity = 0.5;
    }

    public void stopShooter() {
        targetVelocity = 0;
        processVelocity = 0; 
    }

    public Command revUpCommand(){
    return this.run(() -> revUp());
  }

}