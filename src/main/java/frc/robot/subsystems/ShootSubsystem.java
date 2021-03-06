package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShootConstants;


public class ShootSubsystem extends SubsystemBase {
    //motors
    private final TalonFX m_Shoot_Speed_upper = new TalonFX(ShootConstants.kMotor_Shoot_upper);
    private final WPI_VictorSPX m_Shoot_Speed_lower = new WPI_VictorSPX(ShootConstants.kMotor_Shoot_lower);
    private final CANSparkMax m_Shoot_Angle = new CANSparkMax(ShootConstants.kMotor_Shoot_Angle, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final WPI_VictorSPX m_Shoot_Rotation = new WPI_VictorSPX(ShootConstants.kMotor_Shoot_Rotation);
    private int setPoint_RPM;

    private final PIDController PID_Shoot_Rotation = new PIDController(
            ShootConstants.kp_Shoot_Rotation,
            ShootConstants.ki_Shoot_Rotation,
            ShootConstants.kd_Shoot_Rotation
    );
    private final SparkMaxPIDController PID_Shoot_Angle = m_Shoot_Angle.getPIDController();

    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    private boolean auto = true;
    public ShootSubsystem(){
        //invert motors
        m_Shoot_Speed_upper.setInverted(ShootConstants.kMotor_Shoot_upper_Inverted);
        m_Shoot_Speed_lower.setInverted(ShootConstants.kMotor_Shoot_lower_Inverted);
        m_Shoot_Angle.setInverted(ShootConstants.kMotor_Shoot_Angle_Inverted);
        m_Shoot_Rotation.setInverted(ShootConstants.kMotor_Shoot_Rotation_Inverted);
        //set neutral mode
        m_Shoot_Rotation.setNeutralMode(NeutralMode.Brake);
        m_Shoot_Speed_upper.setNeutralMode(NeutralMode.Coast);
        m_Shoot_Speed_lower.setNeutralMode(NeutralMode.Coast);
        m_Shoot_Angle.setIdleMode(CANSparkMax.IdleMode.kBrake);
        //limit current
        m_Shoot_Speed_upper.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true,38,38,0.1));
        m_Shoot_Speed_upper.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,1,1);
        m_Shoot_Speed_upper.config_kP(0,ShootConstants.kp_Shoot_Upper_RPM);
        m_Shoot_Speed_upper.config_kI(0,ShootConstants.ki_Shoot_Upper_RPM);
        m_Shoot_Speed_upper.config_kD(0,ShootConstants.kd_Shoot_Upper_RPM);
        m_Shoot_Speed_upper.setSensorPhase(ShootConstants.kEncoder_Shoot_Upper_reversed);

        PID_Shoot_Angle.setP(ShootConstants.kp_Shoot_Angle);
        PID_Shoot_Angle.setI(ShootConstants.ki_Shoot_Angle);
        PID_Shoot_Angle.setD(ShootConstants.kd_Shoot_Angle);

        setPoint_RPM = 0;
    }
    public void enableBoth(boolean reverse){
//        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,ShootConstants.kShoot_Upper_Speed*(reverse?-1:1));
        auto = false;
        setPoint_RPM = 6380/600*2048*(reverse?-1:1);
        m_Shoot_Speed_upper.set(TalonFXControlMode.Velocity,setPoint_RPM);
        m_Shoot_Speed_lower.set(ControlMode.PercentOutput,ShootConstants.kShoot_Lower_Speed*(reverse?-1:1));
    }
    public void stopBoth(){
        setPoint_RPM = 0;
        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,0);
        m_Shoot_Speed_lower.set(0);
    }

//    public void enableUpperShoot(boolean reverse){
//        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,ShootConstants.kShoot_Upper_Speed*(reverse?-1:1));
//    }
//    public void disableUpperShoot(){
//        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,0);
//    }
//
//    public void enableLowerShoot(boolean reverse){
//        m_Shoot_Speed_lower.set(ControlMode.PercentOutput,ShootConstants.kShoot_Lower_Speed*(reverse?-1:1));
//    }
//    public void disableLowerShoot(){
//        m_Shoot_Speed_lower.set(0);
//    }
    public void enableShootAngle(boolean up){
        m_Shoot_Angle.set(ShootConstants.kMotor_Shoot_Angle_Speed*(up?1:-1));
    }
    public void disableShootAngle(){
        m_Shoot_Angle.set(0);
    }
    public void enableShootRotation(boolean reverse){
        m_Shoot_Rotation.set(ShootConstants.kMotor_Shoot_Rotation_Speed*(reverse?-1:1));
    }
    public void disableShootRotation(){
        m_Shoot_Rotation.set(0);
    }
    @Override
    public void periodic(){
        SmartDashboard.putNumber("ShooterVelocity",m_Shoot_Speed_upper.getSelectedSensorVelocity());
        boolean hasTarget = limelight.getEntry("tv").getDouble(0)>=1;
        if(hasTarget && auto){
            double tx = limelight.getEntry("tx").getDouble(0);
            double ty = limelight.getEntry("ty").getDouble(0);
            double x_output = MathUtil.clamp(
                    PID_Shoot_Rotation.calculate(tx,0),
                    ShootConstants.kMotor_Shoot_Rotation_MinOutput,
                    ShootConstants.kMotor_Shoot_Rotation_MaxOutput
            );
            PID_Shoot_Angle.setReference(ty, CANSparkMax.ControlType.kVelocity);
            PID_Shoot_Angle.setOutputRange(ShootConstants.kMotor_Shoot_Angle_MinOutput,ShootConstants.kMotor_Shoot_Angle_MaxOutput);
            m_Shoot_Rotation.set(ControlMode.PercentOutput,x_output);
        }
    }
    @Override
    public void simulationPeriodic(){

    }
}
