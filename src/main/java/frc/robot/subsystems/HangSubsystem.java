package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HangConstants;



public class HangSubsystem extends SubsystemBase {
    private final WPI_VictorSPX m_LeftHang = new WPI_VictorSPX(HangConstants.kMotor_LHang);
    private final WPI_VictorSPX m_RightHang = new WPI_VictorSPX(HangConstants.kMotor_RHang);
    private final TalonFX m_MidHang = new TalonFX(HangConstants.kMotor_MidHnag);
    private final Compressor compressor = new Compressor(HangConstants.kPCMModule,PneumaticsModuleType.CTREPCM);
    private final DoubleSolenoid m_SideHangAngle = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,HangConstants.kDoubleSol_A,HangConstants.kDoubleSol_B);
    private final DigitalInput limit_LHang = new DigitalInput(HangConstants.kLimitswitch_LHang);
    private final DigitalInput limit_RHang = new DigitalInput(HangConstants.kLimitswitch_Rhang);
    private final Encoder encoder_LHang = new Encoder(HangConstants.kEncoder_LHang_A,HangConstants.kEncoder_LHang_B,HangConstants.kEncoder_LHang_reversed);
    private final Encoder encoder_RHang = new Encoder(HangConstants.kEncoder_RHang_A,HangConstants.kEncoder_RHang_B,HangConstants.kEncoder_RHang_reversed);

    private final TalonFXConfiguration falcon_Hang_Configuration = new TalonFXConfiguration();

    private final PIDController PID_LHang = new PIDController(
            HangConstants.kp_LHang,
            HangConstants.ki_LHang,
            HangConstants.kd_LHang
    );
    private final PIDController PID_RHang = new PIDController(
            HangConstants.kp_RHang,
            HangConstants.ki_RHang,
            HangConstants.kd_RHang
    );
    private final PIDController PID_MHang = new PIDController(
            HangConstants.kp_MidHang,
            HangConstants.ki_MidHang,
            HangConstants.kd_MidHang
    );

    private boolean teleopSideHang = false;
    private double sideHangSetPoint = -200;
    private boolean teleopMidHang;
    private double midHangSetPoint;

    public HangSubsystem(){
        encoder_LHang.setDistancePerPulse(HangConstants.kEncoder_SideHang_cmperpulse);
        encoder_RHang.setDistancePerPulse(HangConstants.kEncoder_SideHang_cmperpulse);
        m_LeftHang.setInverted(HangConstants.kMotor_LHang_Inverted);
        m_RightHang.setInverted(HangConstants.kMotor_RHang_Inverted);
        m_MidHang.setInverted(HangConstants.kMotor_MidHang_Inverted);

        compressor.enableDigital();

        m_LeftHang.setNeutralMode(NeutralMode.Brake);
        m_RightHang.setNeutralMode(NeutralMode.Brake);
        m_MidHang.setNeutralMode(NeutralMode.Brake);

        m_MidHang.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true,39,39,0.1));
        m_MidHang.configSelectedFeedbackSensor(TalonFXFeedbackDevice.SensorSum,1,1);
    }
    public Encoder encoder_LeftHang(){
        return encoder_LHang;
    }
    public Encoder encoder_RightHang(){
        return encoder_RHang;
    }

    public void enableMidHangMotor(boolean up){
        teleopMidHang = true;
        m_MidHang.set(TalonFXControlMode.PercentOutput,up?1:-1);
    }
    public void disableMidHangMotor(){
        teleopMidHang = false;
        m_MidHang.set(TalonFXControlMode.PercentOutput,0);
    }

    public void enableSideHangMotor(boolean up,boolean left,boolean right){
        teleopSideHang = true;
        if(up){
            if(left && right){
                m_LeftHang.set(HangConstants.kSideHang_MaxOutput);
                m_RightHang.set(HangConstants.kSideHang_MaxOutput);
                sideHangSetPoint = 90;
            }else if(left){
                m_LeftHang.set(HangConstants.kSideHang_MaxOutput);
                m_RightHang.set(0);
            }else if(right){
                m_LeftHang.set(0);
                m_RightHang.set(HangConstants.kSideHang_MaxOutput);
            }
        }else{
            if(left && right){
                m_LeftHang.set(HangConstants.kSideHang_MinOutput);
                m_RightHang.set(HangConstants.kSideHang_MinOutput);
                sideHangSetPoint = -200;
            }else if(left){
                m_LeftHang.set(HangConstants.kSideHang_MinOutput);
                m_RightHang.set(0);
            }else if(right){
                m_LeftHang.set(0);
                m_RightHang.set(HangConstants.kSideHang_MinOutput);
            }
        }
    }
    public void disableSideHangMotor(){
        m_LeftHang.set(0);
        m_RightHang.set(0);
        teleopSideHang = false;
    }

    public void enableSideHangAngle(boolean reverse){
        m_SideHangAngle.set(reverse? DoubleSolenoid.Value.kReverse: DoubleSolenoid.Value.kForward);
    }
    public void disableSideHangAngle(){
        m_SideHangAngle.set(DoubleSolenoid.Value.kOff);
    }

    @Override
    public void periodic(){
        if(!teleopSideHang){
            double percentOutput_LeftHang = MathUtil.clamp(
                    PID_LHang.calculate(encoder_LHang.getDistance(), sideHangSetPoint),
                    HangConstants.kSideHang_MinOutput,
                    HangConstants.kSideHang_MaxOutput);
            if(!limit_LHang.get()){
                encoder_LHang.reset();
                percentOutput_LeftHang = 0;
            }
            double percentOutput_RightHang = MathUtil.clamp(
                    PID_RHang.calculate(encoder_RHang.getDistance(), sideHangSetPoint),
                    HangConstants.kSideHang_MinOutput,
                    HangConstants.kSideHang_MaxOutput);
            if(!limit_RHang.get()){
                encoder_RHang.reset();
                percentOutput_RightHang = 0;
            }

            m_LeftHang.set(ControlMode.PercentOutput, percentOutput_LeftHang);
            m_RightHang.set(ControlMode.PercentOutput, percentOutput_RightHang);

            SmartDashboard.putNumber("LeftHang_Output", percentOutput_LeftHang);
            SmartDashboard.putNumber("RightHang_Output", percentOutput_RightHang);
        }
//        if(!teleopMidHang){
//            double percentOutput_MidHang = MathUtil.clamp(
//                    PID_MHang.calculate(m_MidHang.getSelectedSensorPosition(), midHangSetPoint),
//                    HangConstants.kMidHang_MinOutput,
//                    HangConstants.kMidHang_MaxOutput);
//            m_MidHang.set(TalonFXControlMode.PercentOutput,percentOutput_MidHang);
//        }
        SmartDashboard.putNumber("Encoder_LeftHang",encoder_LHang.getDistance());
        SmartDashboard.putNumber("Encoder_RightHang",encoder_RHang.getDistance());
        SmartDashboard.putNumber("Encoder_MidHang",m_MidHang.getSelectedSensorPosition()*HangConstants.kEncoder_MidHang_cmperpulse);

    }

    @Override
    public void simulationPeriodic(){

    }
}
