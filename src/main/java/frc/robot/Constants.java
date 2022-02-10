// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static class CollectConstants{
        public static final int kMotor_Intake = 6;
        public static final boolean kMotor_Intake_Inverted = false;
        public static final double kIntakeSpeed_enable = 1;

        public static final int kMotor_Track = 9;
        public static final boolean kMotor_Track_Inverted = true;
        public static final double kTrackSpeed = 1;

    }
    public static class DriveConstants{
        public static final int kMotor_L_1 = 1;
        public static final int kMotor_L_2 = 2;
        public static final  boolean kMotor_L_Inverted = false;
        public static final boolean kEncoder_L_reversed = false;

        public static final int kMotor_R_1 = 4;
        public static final int kMotor_R_2 = 3;
        public static final boolean kMotor_R_Inverted = true;
        public static final boolean kEncoder_R_reversed = false;

        public static final double kEncoder_meterperpulse = 4*Math.PI*2.54/1024/1000;
    }
    public static class HangConstants{
        public static final int kPCMModule = 0;
        public static final int kDoubleSol_A = 0;
        public static final int kDoubleSol_B = 1;

        public static final int kMotor_MidHnag = 12;
        public static final boolean kMotor_MidHang_Inverted = false;
        public static final double kp_MidHang = 0.1;
        public static final double ki_MidHang = 0;
        public static final double kd_MidHang = 0;
        public static final double kMidHang_MaxOutput = 1;
        public static final double kMidHang_MinOutput = 1;

        public static final int kMotor_LHang = 10;
        public static final boolean kMotor_LHang_Inverted = true;
        public static final int kEncoder_LHang_A = 6;
        public static final int kEncoder_LHang_B = 7;
        public static final boolean kEncoder_LHang_reversed = true;
        public static final int kLimitswitch_LHang = 9;
        public static final double kp_LHang = 0.5;
        public static final double ki_LHang = 0;
        public static final double kd_LHang = 0;

        public static final int kMotor_RHang = 7;
        public static final boolean kMotor_RHang_Inverted = true;
        public static final int kEncoder_RHang_A = 4;
        public static final int kEncoder_RHang_B = 5;
        public static final boolean kEncoder_RHang_reversed = false;
        public static final int kLimitswitch_Rhang = 8;
        public static final double kp_RHang = 0.5;
        public static final double ki_RHang = 0;
        public static final double kd_RHang = 0;

        public static final double kSideHang_MaxOutput = 1;
        public static final double kSideHang_MinOutput = -1;

        public static final double kEncoder_MidHang_cmperpulse = 6*Math.PI/1024/100;
        public static final double kEncoder_SideHang_cmperpulse = 6*Math.PI/4/188;

    }
    public static class ShootConstants{
        public static final int kMotor_Shoot_upper = 11;
        public static final boolean kMotor_Shoot_upper_Inverted = false;
        public static final double kp_Shoot_Upper_Speed = 0.1;
        public static final double ki_Shoot_Upper_Speed = 0;
        public static final double kd_Shoot_Upper_Speed = 0;
        public static final double kShoot_Upper_Speed = 1;

        public static final int kMotor_Shoot_lower = 5;
        public static final boolean kMotor_Shoot_lower_Inverted = true;
        public static final double kShoot_Lower_Speed = 1;

        public static final int kMotor_Shoot_Angle = 13;
        public static final boolean kMotor_Shoot_Angle_Inverted = false;
        public static final double kp_Shoot_Angle = 0.1;
        public static final double ki_Shoot_Angle = 0;
        public static final double kd_Shoot_Angle = 0;
        public static final double kMotor_Shoot_Angle_Speed = 0.1;

        public static final int kMotor_Shoot_Rotation = 8;
        public static final boolean kMotor_Shoot_Rotation_Inverted = true;
        public static final double kMotor_Shoot_Rotation_Speed = 1;
    }
    public static class ControlConstants{
        public static final int kController1_port = 0;
        public static final int kController2_port = 1;
    }

}
