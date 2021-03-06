package org.usfirst.frc.team4955.robot;

import org.usfirst.frc.team4955.robot.commands.drive.InverseDriveTrainCommand;
import org.usfirst.frc.team4955.robot.commands.drive.SetDriveInputFactor;
import org.usfirst.frc.team4955.robot.commands.drive.Show_Move_Distance;
import org.usfirst.frc.team4955.robot.commands.gear.GearKickerCommand;
import org.usfirst.frc.team4955.robot.commands.pickup.BallPickupCommands;
import org.usfirst.frc.team4955.robot.commands.thrower.ThowerStartCommands;
import org.usfirst.frc.team4955.robot.commands.winch.WinchJoyCommand;
import org.usfirst.frc.team4955.robot.utils.input.DualAxisInput;
import org.usfirst.frc.team4955.robot.utils.input.JoystickInput;
import org.usfirst.frc.team4955.robot.utils.input.TeleopInput;
import org.usfirst.frc.team4955.robot.utils.utils.Gamepad.GamepadAxis;
import org.usfirst.frc.team4955.robot.utils.utils.Gamepad.GamepadButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static Joystick		mainJoystick;
	public static TeleopInput	controlerRotationInput;
	public static TeleopInput	controlerMovementInput;
	public static TeleopInput	winchInput;
	public static TeleopInput	testValue;
	public static TeleopInput	testValue2;

	public static GamepadButton	REVERSE_DRIVE_BUTTOM_NUMBER		= GamepadButton.B;
	public static GamepadButton	SLOW_DRIVE_OUTPUT_BUTTOM_NUMBER	= GamepadButton.A;
	public static GamepadButton	BALL_THROWER					= GamepadButton.RB;
	public static GamepadButton	GEAR_KICKER						= GamepadButton.LB;
	public static GamepadButton	BALL_PICKUP						= GamepadButton.X;

	public static GamepadButton	WINCH_RAISE	= GamepadButton.Start;
	public static GamepadButton	WINCH_LOWER	= GamepadButton.Back;

	public static GamepadButton SHOW_MOVE_DISTANCE = GamepadButton.RStick;

	public static double	LEFT_JOYSTICK_DEAD_ZONE		= 0.14;
	public static double	RIGHT_JOYSTICK_DEAD_ZONE	= 0.14;

	public static void init() {

		// Set up the joystick
		mainJoystick = new Joystick(0);

		controlerRotationInput = new JoystickInput(mainJoystick, GamepadAxis.LeftX.value(), LEFT_JOYSTICK_DEAD_ZONE);
		testValue = new JoystickInput(mainJoystick, GamepadAxis.LeftY.value(), LEFT_JOYSTICK_DEAD_ZONE);
		testValue2 = new JoystickInput(mainJoystick, GamepadAxis.RightY.value(), RIGHT_JOYSTICK_DEAD_ZONE);

		winchInput = new JoystickInput(mainJoystick, GamepadAxis.RightY.value(), RIGHT_JOYSTICK_DEAD_ZONE);
		controlerMovementInput = new DualAxisInput(mainJoystick, GamepadAxis.LeftTrigger.value(), mainJoystick,
				GamepadAxis.RightTrigger.value(), 0, 0);

	}

	public static void initCommands() {
		JoystickButton command = null;
		// Drive
		if (Robot.driveSubsystem.isPresent()) {
			command = new JoystickButton(mainJoystick, REVERSE_DRIVE_BUTTOM_NUMBER.value());
			command.whenPressed(new InverseDriveTrainCommand());
			command = new JoystickButton(mainJoystick, SLOW_DRIVE_OUTPUT_BUTTOM_NUMBER.value());
			command.toggleWhenPressed(new SetDriveInputFactor(Constants.DRIVE_SLOWER_SPEED_FACTOR));
		}

		// Ball pick-up
		if (Robot.ballPickUpSystem.isPresent()) {
			command = new JoystickButton(mainJoystick, BALL_PICKUP.value());
			command.toggleWhenPressed(new BallPickupCommands());
		}

		// Ball Thrower
		if (Robot.throwerSubsystem.isPresent()) {
			command = new JoystickButton(mainJoystick, BALL_THROWER.value());
			command.toggleWhenPressed(new ThowerStartCommands());
		}

		// Winch
		if (Robot.winchSystem.isPresent()) {
			command = new JoystickButton(mainJoystick, WINCH_RAISE.value());
			command.whenActive(new WinchJoyCommand());
		}

		command = new JoystickButton(mainJoystick, GEAR_KICKER.value());
		command.whileActive(new GearKickerCommand(60));

		command = new JoystickButton(mainJoystick, SHOW_MOVE_DISTANCE.value());
		command.toggleWhenActive(new Show_Move_Distance());
	}
}
