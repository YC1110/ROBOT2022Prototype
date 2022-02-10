package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CollectConstants;

public class CollectSubsystem extends SubsystemBase {
    private final WPI_VictorSPX m_intake = new WPI_VictorSPX(CollectConstants.kMotor_Intake);
    private final WPI_VictorSPX m_track = new WPI_VictorSPX(CollectConstants.kMotor_Track);
    public CollectSubsystem(){
        m_intake.setInverted(CollectConstants.kMotor_Intake_Inverted);
        m_track.setInverted(CollectConstants.kMotor_Track_Inverted);
    }

    public void enableIntake(boolean reversed){
        m_intake.set(CollectConstants.kIntakeSpeed_enable*(reversed?-1:1));
    }

    public void disableIntake(){
        m_intake.set(0);
    }

    public void enableTrack(boolean reverse){
        m_track.set(CollectConstants.kTrackSpeed*(reverse?-0.4:1));
    }
    public void disableTrack(){
        m_track.set(0);
    }
    public void stopAllCollect(){
        m_track.set(0);
        m_intake.set(0);
    }
    @Override
    public void periodic(){

    }
    @Override
    public void simulationPeriodic(){

    }
}
