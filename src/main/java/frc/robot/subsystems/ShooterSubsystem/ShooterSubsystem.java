package frc.robot.subsystems.ShooterSubsystem;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase{
    private final CANSparkMax m_leftShooter; 
    private final CANSparkMax m_rightShooter;
    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;
    private final SparkPIDController leftShooterPID;
    private final SparkPIDController rightShooterPID; 
    private double targetVelocityRPM = 0;

    public ShooterSubsystem(boolean leftShooterRev, boolean rightShooterRev)
    {
        this.m_leftShooter = new CANSparkMax(Constants.ShooterConstants.kLeftShooterMotorControllerID, CANSparkMax.MotorType.kBrushless);
        this.m_rightShooter = new CANSparkMax(Constants.ShooterConstants.kRightShooterMotorControllerID, CANSparkMax.MotorType.kBrushless);
        this.m_leftEncoder = this.m_leftShooter.getEncoder();
        this.m_rightEncoder = this.m_rightShooter.getEncoder();
        
        this.leftShooterPID = this.m_leftShooter.getPIDController();
        this.rightShooterPID = this.m_rightShooter.getPIDController();

        this.m_leftShooter.restoreFactoryDefaults();
        this.m_rightShooter.restoreFactoryDefaults();
        this.m_leftShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
        this.m_rightShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
        this.m_leftShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 500);
        this.m_rightShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 500);
        this.m_leftShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 500);
        this.m_rightShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 500);

        this.m_leftShooter.setIdleMode(IdleMode.kBrake);
        this.m_rightShooter.setIdleMode(IdleMode.kBrake);
        this.m_leftShooter.setInverted(leftShooterRev);
        this.m_rightShooter.setInverted(rightShooterRev);

        
        }
}



