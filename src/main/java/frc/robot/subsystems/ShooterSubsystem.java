package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.TimestampedDoubleArray;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.math.controller.PIDController; 



import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    public RobotContainer localRobotContainer;

    public double shooterVelocity; 
    public double transverseVelocity;
    public double spindexerVelocity;
    
    private SparkMax m_shooter;
    private SparkMax m_transverse;
    private SparkMax m_spindexer;


    //shooter subsystem brance
    public ShooterSubsystem(RobotContainer m_robotContainer) {
        localRobotContainer = m_robotContainer;

        m_shooter = new SparkMax(ShooterConstants.kShooterMotorID, MotorType.kBrushless);
        m_transverse = new SparkMax(ShooterConstants.kTransverseID, MotorType.kBrushless);
        m_spindexer = new SparkMax(ShooterConstants.kSpindexerID, MotorType.kBrushless);

        shooterVelocity = 0;
        transverseVelocity = 0;
        spindexerVelocity = 0;
    }

    @Override
    public void periodic() {
        m_shooter.set(shooterVelocity);
        m_transverse.set(transverseVelocity);
        m_spindexer.set(spindexerVelocity);
        //System.out.println(shooterVelocity + ", " + transverseVelocity + ", " + spindexerVelocity);
    }


    //Basic functions to run the values
    /** Positive = In<p>Negative = Out */
    public void runShooter(double velo) {
        shooterVelocity = velo;
    }

    /** Positive = Up<p>Negative = Down */
    public void runTransverse(double velo) {
        transverseVelocity = velo;
    }

    /** Positive = ???<p>Negative = ??? */
    public void runSpindexer(double velo) {
        spindexerVelocity = velo;
    }

    //Basic Commands to trigger functions
    /** Positive = In<p>Negative = Out */
    public Command shooterCommand(double velo){
        System.out.println("Shooter Command calling runshooter function " + velo);
        return this.run(() -> runShooter(velo));
    }
    
    /** Positive = Up<p>Negative = Down */
    public Command transverseCommand(double velo) {
        return this.run(() -> runTransverse(velo));
    }

    /** Positive = ???<p>Negative = ??? */
    public Command spindexerCommand(double velo) {
        return this.run(() -> runSpindexer(velo));
    }

    /**
     * <b>SHOOTER</b>
     * <p>runAll function, call through Shooter runAllCommand(v1,v2,v3)
     */
    public void runAll(double v1, double v2, double v3) {
        shooterVelocity = v1;
        transverseVelocity = v2;
        spindexerVelocity = v3;
    }

    /**
     * <b>SHOOTER</b>
     * <p>This command is capable of running all the different parts of the shooter at once
     * <p>v1 = shooter velocity
     * <p>v2 = transverse velocity
     * <p>v3 = spindexer velocity
     */
    public Command runAllCommand(double v1, double v2, double v3) {
        return this.run(() -> runAll(v1,v2,v3));
    }

    /**
     * <b>SHOOTER</b>
     * <p>stopAll function, call through stopAllCommand()
     */
    public void stopAll() {
        shooterVelocity = 0;
        transverseVelocity = 0; 
        spindexerVelocity = 0;
    }

    /**
     * <b>SHOOTER</b>
     * <p>Stops all different parts of the shooter at once
     * <p>Same as Shooter runAllCommand(0,0,0)
     */
    public Command stopAllCommand() {
        return this.run(() -> stopAll());
    }

    /**
     * Returns the shooter's encoder's velocity value in RPM
     * <p>Currently very inaccurate and off by a factor of something about 30
     */
    public double getShooterRPM() {
        return m_shooter.getAlternateEncoder().getVelocity();
    }


    public class timedShootCommand extends Command {
        double startTime; //time at the start
        double timeElapsed; //delta time
        double timerLength = 1; //total time seconds

        public timedShootCommand(){}

        @Override
        public void initialize() {
            startTime = Timer.getFPGATimestamp();
            System.out.println("Timed Shooter Command Initialized");
        }

        @Override
        public void execute() {
            timeElapsed = Timer.getFPGATimestamp() - startTime;
            System.out.println("Timed Shooter Command Running: " + timeElapsed);
            if(timeElapsed > 0 && timeElapsed < 1) {
                runShooter(-0.45);
            }
            if(timeElapsed > 0.33 && timeElapsed < 1) {
                runTransverse(0.5);
            }
            if(timeElapsed > 0.67 && timeElapsed < 1) {
                runSpindexer(-1);
            }
        }

        @Override
        public boolean isFinished() {
            if(timeElapsed >= timerLength) {
                return true;
            }
            return false;
        }
        
        @Override
        public void end(boolean interrupted) {
            stopAllCommand();
            System.out.println("Timed Shooter Command Ended");
        }
    }
}

