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
    private final XboxController controller1 = new XboxController(ControlConstants.kController1_port);
    private final Joystick controller2 = new Joystick(ControlConstants.kController2_port);

    Robot robot;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer(Robot robot)
    {
        // Configure the button bindings
        this.robot = robot;
        configureButtonBindings();
        m_robotDrive.setDefaultCommand(new RunCommand(()->{
            if (robot.isTeleopEnabled()) {
                telePeriodic();
            }
        },m_robotDrive));
    }
//    public boolean intake_trigger = false;
//    public boolean intake_bumpper = false;
//    public boolean midhang_trigger = false;
//    public boolean midhang_bumpper = false;
    
    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * Joystick} or {@link XboxController}), and then passing it to a {@link
     * JoystickButton}.
     */
    private void configureButtonBindings()
    {
        //controller1 XboxController
        new JoystickButton(controller1,5).
                whenPressed(() -> m_robotHang.enableMidHangMotor(true)).
                whenReleased(m_robotHang::disableMidHangMotor);
        new JoystickButton(controller1,6).
                whenPressed(() -> m_robotCollect.enableIntake(false)).
                whenReleased(m_robotCollect::disableIntake);
//        new JoystickButton(controller1,7).
//                whenPressed(() -> m_robotHang.enableMidHangMotor(true,false)).
//                whenReleased(m_robotHang::disableMidHangMotor);
//        new JoystickButton(controller1,8).
//                whenPressed(() -> m_robotCollect.enableIntake(true,true)).
//                whenReleased(m_robotCollect::disableIntake);
        new JoystickButton(controller1,1).
                whenPressed(() -> m_robotHang.enableSideHangAngle(true)).
                whenReleased(m_robotHang::disableSideHangAngle);
        new JoystickButton(controller1,2).
                whenPressed(() -> m_robotHang.enableSideHangAngle(false)).
                whenReleased(m_robotHang::disableSideHangAngle);
        new JoystickButton(controller1,3).
                whenPressed(m_robotDrive::switchSlowMo);
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
        new JoystickButton(controller1,4).
                whenPressed(() -> m_robotHang.autoHangSwitch(false)).
                whenReleased(() -> m_robotHang.autoHangSwitch(true));

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
                whenPressed(() -> m_robotCollect.enableIntake( false)).
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
        new JoystickButton(controller2,7).
                whenPressed(() -> m_robotCollect.enableTrack(true)).
                whenReleased(m_robotCollect::disableTrack);

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
    private void telePeriodic(){
        m_robotDrive.arcadeDrive(controller1.getLeftY(),controller1.getRightX());

        if(!(controller1.getLeftTriggerAxis() > 0.1) && !controller1.getLeftBumper() && !controller2.getRawButton(4) && !controller2.getRawButton(6)){
            m_robotHang.disableMidHangMotor();
        }else if(controller1.getLeftTriggerAxis() > 0.1){
            m_robotHang.enableMidHangMotor(false);
        }
        if(!(controller1.getRightTriggerAxis() > 0.1) && !controller1.getRightBumper() && !controller2.getRawButton(3) && !controller2.getRawButton(5)){
            m_robotCollect.disableIntake();
        }else if(controller1.getRightTriggerAxis() > 0.1){
            m_robotCollect.enableIntake(true);
        }
//        if(intake_bumpper && !intake_trigger){
//            m_robotCollect.enableIntake(false);
//        }else if(!intake_bumpper && intake_trigger){
//            m_robotCollect.enableIntake(true);
//        }else{
//            m_robotCollect.disableIntake();
//        }
//
//        if (midhang_bumpper && !midhang_trigger){
//            m_robotHang.enableMidHangMotor(true);
//        }else if(!midhang_bumpper && midhang_trigger){
//            m_robotHang.enableMidHangMotor(false);
//        }else{
//            m_robotHang.disableMidHangMotor();
//        }
    }
}
