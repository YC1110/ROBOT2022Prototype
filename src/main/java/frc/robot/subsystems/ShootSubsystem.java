package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShootConstants;

public class ShootSubsystem extends SubsystemBase {
    private final TalonFX m_Shoot_Speed_upper = new TalonFX(ShootConstants.kMotor_Shoot_upper);
    private final WPI_VictorSPX m_Shoot_Speed_lower = new WPI_VictorSPX(ShootConstants.kMotor_Shoot_lower);
    private final CANSparkMax m_Shoot_Angle = new CANSparkMax(ShootConstants.kMotor_Shoot_Angle, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final WPI_VictorSPX m_Shoot_Rotation = new WPI_VictorSPX(ShootConstants.kMotor_Shoot_Rotation);

    private final PIDController PID_Shoot_Speed = new PIDController(
            ShootConstants.kp_Shoot_Upper_Speed,
            ShootConstants.ki_Shoot_Upper_Speed,
            ShootConstants.kd_Shoot_Upper_Speed
    );



    public ShootSubsystem(){
        m_Shoot_Speed_upper.setInverted(ShootConstants.kMotor_Shoot_upper_Inverted);
        m_Shoot_Speed_lower.setInverted(ShootConstants.kMotor_Shoot_lower_Inverted);
        m_Shoot_Angle.setInverted(ShootConstants.kMotor_Shoot_Angle_Inverted);
        m_Shoot_Rotation.setInverted(ShootConstants.kMotor_Shoot_Rotation_Inverted);

        m_Shoot_Rotation.setNeutralMode(NeutralMode.Brake);
        m_Shoot_Speed_upper.setNeutralMode(NeutralMode.Coast);
        m_Shoot_Speed_lower.setNeutralMode(NeutralMode.Coast);
        m_Shoot_Angle.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_Shoot_Speed_upper.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true,38,38,0.1));

    }
    public void enableBoth(boolean reverse){
        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,ShootConstants.kShoot_Upper_Speed*(reverse?-1:1));
        while(m_Shoot_Speed_upper.getSelectedSensorVelocity() > 2){
            m_Shoot_Speed_lower.set(ControlMode.PercentOutput,ShootConstants.kShoot_Lower_Speed*(reverse?-1:1));
        }
    }
    public void stopBoth(){
        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,0);
        m_Shoot_Speed_lower.set(0);
    }

    public void enableUpperShoot(boolean reverse){
//        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,ShootConstants.kShoot_Upper_Speed*(reverse?-1:1));
        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,ShootConstants.kShoot_Upper_Speed*(reverse?-1:1));
    }
    public void disableUpperShoot(){
        m_Shoot_Speed_upper.set(TalonFXControlMode.PercentOutput,0);
    }

    public void enableLowerShoot(boolean reverse){
        m_Shoot_Speed_lower.set(ControlMode.PercentOutput,ShootConstants.kShoot_Lower_Speed*(reverse?-1:1));
    }
    public void disableLowerShoot(){
        m_Shoot_Speed_lower.set(0);
    }
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

    }
    @Override
    public void simulationPeriodic(){

    }
}
