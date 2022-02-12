package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;


public class DriveSubsystem extends SubsystemBase {
    //Motors
    private final WPI_TalonSRX m_left = new WPI_TalonSRX(DriveConstants.kMotor_L_1);
    private final WPI_TalonSRX m_right = new WPI_TalonSRX(DriveConstants.kMotor_R_1);
    private final MotorControllerGroup m_leftmotors = new MotorControllerGroup(
            new WPI_VictorSPX(DriveConstants.kMotor_L_2),
            m_left
    );
    private final MotorControllerGroup m_rightmotors = new MotorControllerGroup(
            new WPI_VictorSPX(DriveConstants.kMotor_R_2),
            m_right
    );
    private DifferentialDrive drive = new DifferentialDrive(m_leftmotors,m_rightmotors);

    private DifferentialDriveOdometry m_odometry;

    private AHRS m_navx = new AHRS(SerialPort.Port.kMXP);

    public DriveSubsystem() {
        m_leftmotors.setInverted(DriveConstants.kMotor_L_Inverted);
        m_rightmotors.setInverted(DriveConstants.kMotor_R_Inverted);
        m_left.setSensorPhase(DriveConstants.kEncoder_L_reversed);
        m_right.setSensorPhase(DriveConstants.kEncoder_R_reversed);
        resetEncoders();
        m_odometry = new DifferentialDriveOdometry(m_navx.getRotation2d());
    }
    public WPI_TalonSRX encoder_LDrive(){
        return m_left;
    }
    public WPI_TalonSRX encoder_RDrive(){
        return m_right;
    }

    public void resetEncoders(){
        m_left.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.SensorSum,1,1);
        m_right.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.SensorSum,1,1);
    }

    public void arcadeDrive(double yspeed, double zRotation){
        if(Math.abs(yspeed) > 0.05){
            yspeed = yspeed>0?-Math.pow(yspeed,2):Math.pow(yspeed,2);
        }
        if(Math.abs(zRotation) > 0.05){
            zRotation = zRotation>0?Math.pow(zRotation,2):-Math.pow(zRotation,2);
        }
        drive.arcadeDrive(yspeed, zRotation);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("LeftDrive_Encoder",m_left.getSelectedSensorPosition()*DriveConstants.kEncoder_meterperpulse);
        SmartDashboard.putNumber("RightDrive_Encoder",m_right.getSelectedSensorPosition()*DriveConstants.kEncoder_meterperpulse);
    }
    @Override
    public void simulationPeriodic(){

    }
}
