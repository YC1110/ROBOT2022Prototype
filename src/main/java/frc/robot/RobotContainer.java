// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.RunTrajectoryCommand;
import frc.robot.subsystems.*;
import frc.robot.Constants.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...



    private final CollectSubsystem m_robotCollect = new CollectSubsystem();
    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final HangSubsystem m_robotHang = new HangSubsystem();
    private final ShootSubsystem m_robotShoot = new ShootSubsystem();
    private final Joystick controller1 = new Joystick(ControlConstants.kController1_port);
    private final Joystick controller2 = new Joystick(ControlConstants.kController2_port);

    Robot robot;
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer(Robot robot)
    {
        // Configure the button bindings
        this.robot = robot;
        configureButtonBindings();
        m_robotDrive.setDefaultCommand(new RunCommand(()->{
            if (robot.isTeleopEnabled())
                teleperiodic();
        },m_robotDrive));
    }
    
    
    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * Joystick} or {@link XboxController}), and then passing it to a {@link
     * JoystickButton}.
     */
    private void configureButtonBindings()
    {
        //controller1 F310_DMode
        new JoystickButton(controller1,5).
                whenPressed(() -> m_robotHang.enableMidHangMotor(true)).
                whenReleased(m_robotHang::disableMidHangMotor);
        new JoystickButton(controller1,6).
                whenPressed(() -> m_robotCollect.enableIntake(false)).
                whenReleased(m_robotCollect::disableIntake);
        new JoystickButton(controller1,7).
                whenPressed(() -> m_robotHang.enableMidHangMotor(false)).
                whenReleased(m_robotHang::disableMidHangMotor);
        new JoystickButton(controller1,8).
                whenPressed(() -> m_robotCollect.enableIntake(true)).
                whenReleased(m_robotCollect::disableIntake);
        new JoystickButton(controller1,2).
                whenPressed(() -> m_robotHang.enableSideHangAngle(true)).
                whenReleased(m_robotHang::disableSideHangAngle);
        new JoystickButton(controller1,3).
                whenPressed(() -> m_robotHang.enableSideHangAngle(false)).
                whenReleased(m_robotHang::disableSideHangAngle);
        new POVButton(controller1,0).
                whenPressed(() -> m_robotHang.enableSideHangMotor(true,true,true)).
                whenReleased(m_robotHang::disableSideHangMotor);
        new POVButton(controller1,45).
                whenPressed(() -> m_robotHang.enableSideHangMotor(true,false,true)).
                whenReleased(m_robotHang::disableSideHangMotor);
        new POVButton(controller1,135).
                whenPressed(() -> m_robotHang.enableSideHangMotor(false,false,true)).
                whenReleased(m_robotHang::disableSideHangMotor);
        new POVButton(controller1,180).
                whenPressed(() -> m_robotHang.enableSideHangMotor(false,true,true)).
                whenReleased(m_robotHang::disableSideHangMotor);
        new POVButton(controller1,225).
                whenPressed(() -> m_robotHang.enableSideHangMotor(false,true,false)).
                whenReleased(m_robotHang::disableSideHangMotor);
        new POVButton(controller1,315).
                whenPressed(() -> m_robotHang.enableSideHangMotor(true,true,false)).
                whenReleased(m_robotHang::disableSideHangMotor);

        //controller2 X3D
        new JoystickButton(controller2,1).
                whenPressed(() -> m_robotShoot.enableBoth(false)).
                whenReleased(m_robotShoot::stopBoth);
        new JoystickButton(controller2,2).
                whenPressed(() -> m_robotCollect.enableTrack(false)).
                whenReleased(m_robotCollect::disableTrack);
        new JoystickButton(controller2,5).
                whenPressed(() -> m_robotCollect.enableIntake(true)).
                whenReleased(m_robotCollect::disableIntake);
        new JoystickButton(controller2,3).
                whenPressed(() -> m_robotCollect.enableIntake(false)).
                whenReleased(m_robotCollect::disableIntake);
        new JoystickButton(controller2,4).
                whenPressed(() -> m_robotHang.enableMidHangMotor(false)).
                whenReleased(m_robotHang::disableMidHangMotor);
        new JoystickButton(controller2,6).
                whenPressed(() -> m_robotHang.enableMidHangMotor(true)).
                whenReleased(m_robotHang::disableMidHangMotor);
        new POVButton(controller2,0).
                whenPressed(() ->m_robotShoot.enableShootAngle(true)).
                whenReleased(m_robotShoot::disableShootAngle);
        new POVButton(controller2,180).
                whenPressed(() -> m_robotShoot.enableShootAngle(false)).
                whenReleased(m_robotShoot::disableShootAngle);
        new POVButton(controller2,90).
                whenPressed(() -> m_robotShoot.enableShootRotation(true)).
                whenReleased(m_robotShoot::disableShootRotation);
        new POVButton(controller2,270).
                whenPressed(() -> m_robotShoot.enableShootRotation(false)).
                whenReleased(m_robotShoot::disableShootRotation);


        // Add button to command mappings here.
        // See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
//     * @return the command to run in autonomous
     */
//    public Command getAutonomousCommand()
//    {
//        // An ExampleCommand will run in autonomous
//        return autoCommand;
//    }
    private void teleperiodic(){
        m_robotDrive.arcadeDrive(controller1.getY(),controller1.getZ());
    }
}
