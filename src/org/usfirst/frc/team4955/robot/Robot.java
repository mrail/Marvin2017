
package org.usfirst.frc.team4955.robot;

import org.usfirst.frc.team4955.robot.commands.RobotInit;
import org.usfirst.frc.team4955.robot.commands.autonomous.cg.B1;
import org.usfirst.frc.team4955.robot.commands.autonomous.cg.B2;
import org.usfirst.frc.team4955.robot.commands.autonomous.cg.B3;
import org.usfirst.frc.team4955.robot.commands.autonomous.cg.NothingAuto;
import org.usfirst.frc.team4955.robot.commands.autonomous.cg.R1;
import org.usfirst.frc.team4955.robot.commands.autonomous.cg.R2;
import org.usfirst.frc.team4955.robot.commands.autonomous.cg.R3;
import org.usfirst.frc.team4955.robot.commands.autonomous.kickInside.B1Kicker;
import org.usfirst.frc.team4955.robot.commands.autonomous.kickInside.B3Kicker;
import org.usfirst.frc.team4955.robot.commands.autonomous.kickInside.R1Kicker;
import org.usfirst.frc.team4955.robot.commands.autonomous.kickInside.R3Kicker;
import org.usfirst.frc.team4955.robot.commands.drive.JoystickDrive;
import org.usfirst.frc.team4955.robot.commands.drive.WallSensor;
import org.usfirst.frc.team4955.robot.commands.drive.turn.TurnCommandGroup;
import org.usfirst.frc.team4955.robot.subsystems.BallPickUpSubsystem;
import org.usfirst.frc.team4955.robot.subsystems.CameraSubsystem;
import org.usfirst.frc.team4955.robot.subsystems.DriveGameSubsystem;
import org.usfirst.frc.team4955.robot.subsystems.ThrowerSubsystem;
import org.usfirst.frc.team4955.robot.subsystems.WinchSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// Subsystems
	public static DriveGameSubsystem	driveSubsystem;
	public static BallPickUpSubsystem	ballPickUpSystem;
	public static WinchSubsystem		winchSystem;
	public static ThrowerSubsystem		throwerSubsystem;
	public static CameraSubsystem		cameraSubsystem;

	Command						autonomousCommand;
	SendableChooser<Command>	chooser;

	///
	/// INIT
	///
	@Override
	public void robotInit() {
		SmartDashboard.putBoolean("TestMod", false);
		// Pre init
		// Constants.initForRobotR1();// REMOVE ME ON COMPETITION ROBOT R1
		DashboardKeys.init();

		// Robot init
		RobotMap.init();
		subsystemInit();
		OI.init();
		initAutonomousCommands();
	}

	private void subsystemInit() {
		driveSubsystem = new DriveGameSubsystem(RobotMap.driveTrain, RobotMap.gyro);
		ballPickUpSystem = new BallPickUpSubsystem();
		winchSystem = new WinchSubsystem();
		throwerSubsystem = new ThrowerSubsystem();
		cameraSubsystem = new CameraSubsystem();

		SmartDashboard.putBoolean(DashboardKeys.GYRO, RobotMap.gyro != null);
	}

	private void initAutonomousCommands() {
		chooser = new SendableChooser<>();
		chooser.addDefault("Do Nothing", new NothingAuto());
		chooser.addObject("Blue 1", new B1());
		chooser.addObject("Blue 2", new B2());
		chooser.addObject("Blue 3", new B3());
		chooser.addObject("Red  1", new R1());
		chooser.addObject("Red  2", new R2());
		chooser.addObject("Red  3", new R3());
		chooser.addObject("Red  1 Kicker", new R1Kicker());
		chooser.addObject("Red  3 Kicker", new R3Kicker());
		chooser.addObject("Blue 1 Kicker", new B1Kicker());
		chooser.addObject("Blue 3 Kicker", new B3Kicker());
		chooser.addObject("test", new TurnCommandGroup(90, 0));

		SmartDashboard.putData("AutoNew", chooser);

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	///
	/// AUTONOMOUS
	///

	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		RobotMap.cameraFrontServo.set(Constants.CAMERA_TALON_OUT_VALUE);

		if (autonomousCommand != null)
			autonomousCommand.start();

		// autonomousCommand = new R1();
		// autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	///
	/// TELEOP
	///

	TestProcedure testProcedure;

	@Override
	public void teleopInit() {
		if (SmartDashboard.getBoolean("TestMod", false)) {

			testProcedure = new TestProcedure();
		} else {
			OI.initCommands();

			if (autonomousCommand != null)
				autonomousCommand.cancel();

			new RobotInit().start();
			RobotMap.cameraFrontServo.set(Constants.CAMERA_TALON_OUT_VALUE);

			if (driveSubsystem.isPresent()) {
				Command drive = new JoystickDrive();
				Scheduler.getInstance().add(drive);
				drive.start();
			}
			if (RobotMap.backSensor != null && RobotMap.frontSensor != null) {
				Command WallSensor = new WallSensor();
				WallSensor.start();
			}
		}

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().removeAll();
	}

	@Override
	public void teleopPeriodic() {
		if (SmartDashboard.getBoolean("TestMod", false)) {
			testProcedure.periodic();
		}

		Scheduler.getInstance().run();
	}

}
