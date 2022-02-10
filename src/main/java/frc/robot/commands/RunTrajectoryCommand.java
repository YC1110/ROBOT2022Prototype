package frc.robot.commands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

public class RunTrajectoryCommand extends CommandBase {
    private final DriveSubsystem driveSubsystem;
    private SequentialCommandGroup commands;
    Trajectory trajectory;

    public RunTrajectoryCommand(DriveSubsystem driveSubsystem, String pathName) {
        this.driveSubsystem = driveSubsystem;

    }

    @Override
    public void initialize() {
        if (trajectory == null){}
        //driveSubsystem.resetOdometry(trajectory.getInitialPose());
        //command.initialize();
    }

    @Override
    public void execute() {
        //command.execute();
    }

    @Override
    public boolean isFinished() {
        return true;
        //return command.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        //command.end(interrupted);
    }
}
