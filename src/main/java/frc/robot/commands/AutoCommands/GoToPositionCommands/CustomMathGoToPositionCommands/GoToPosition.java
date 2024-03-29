// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.commands.AutoCommands.GoToPositionCommands.CustomMathGoToPositionCommands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.LEDSubsystem.LEDSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDSubsystem.RGBColor;
import frc.robot.subsystems.SwerveDrive.DriveSubsystem;
import frc.robot.utils.CalculateGoToPoseVelocity;


public class GoToPosition extends Command{
    DriveSubsystem m_driveSubsystem;
    Pose2d goalEndPose;
    private ProfiledPIDController headingPIDController;
    private final SlewRateLimiter translationXLimiter;
    private final SlewRateLimiter translationYLimiter;
    private LEDSubsystem m_ledSubsystem;

    
    public GoToPosition(DriveSubsystem m_driveSubsystem, Pose2d goalEndPose, LEDSubsystem m_ledSubsystem) {
        this.m_driveSubsystem = m_driveSubsystem;
        this.goalEndPose = goalEndPose;
        this.headingPIDController = new ProfiledPIDController(AutoConstants.kHeadingPIDControllerP, AutoConstants.kHeadingPIDControllerI,
        AutoConstants.kHeadingPIDControllerD, AutoConstants.kThetaControllerConstraints);
        this.headingPIDController.setTolerance(AutoConstants.kHeadingPIDControllerTolerance);
        this.headingPIDController.setIntegratorRange(-0.3, 0.3);
        this.headingPIDController.reset(this.m_driveSubsystem.getRobotPose().getRotation().getDegrees());
        this.translationXLimiter = new SlewRateLimiter(20);
        this.translationYLimiter = new SlewRateLimiter(20);

        this.m_ledSubsystem = m_ledSubsystem;

        addRequirements(m_driveSubsystem);
    }

    public void initialize() {
        //SmartDashBoard.putBoolean("GO TO POSE TRIGGURED", true);
        this.translationXLimiter.reset(0);
        this.translationYLimiter.reset(0);
        this.headingPIDController.reset(this.m_driveSubsystem.getRobotPose().getRotation().getDegrees());
    }

    public void execute() {
        //SmartDashBoard.putNumber("Auton Goal Thata", this.goalEndPose.getRotation().getDegrees());
        Translation2d robotTransformVelocity = CalculateGoToPoseVelocity.calculateGoToPoseVelocity(m_driveSubsystem.getRobotPose(), this.goalEndPose);
        //SmartDashBoard.putNumber("Goal X Vel", robotTransformVelocity.getX());
        //SmartDashBoard.putNumber("Goal Y Vel", robotTransformVelocity.getY());
        this.m_driveSubsystem.drive(
        ChassisSpeeds.fromFieldRelativeSpeeds(
        this.translationXLimiter.calculate(robotTransformVelocity.getX()),
        this.translationYLimiter.calculate(robotTransformVelocity.getY()),
        headingPIDController.calculate(this.m_driveSubsystem.getRobotPose().getRotation().getDegrees(), this.goalEndPose.getRotation().getDegrees()),
        this.m_driveSubsystem.getRobotPose().getRotation()));
    }

    @Override
    public void end(boolean interrupted) {
        this.m_driveSubsystem.stop();
        this.m_ledSubsystem.setSolidColor(RGBColor.Green.color);

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
       // return Math.abs(this.m_driveSubsystem.getRobotPose().getRotation().getDegrees() - this.goalEndPose.getRotation().getDegrees()) 
        //<= AutoConstants.kHeadingPIDControllerTolerance && Math.abs(this.m_driveSubsystem.getRobotPose().getX() - goalEndPose.getX()) 
        //<= AutoConstants.kAutoPositonTolerance && Math.abs(this.m_driveSubsystem.getRobotPose().getY() - goalEndPose.getY()) 
        //<= AutoConstants.kAutoPositonTolerance;
        return true;
    }
}
