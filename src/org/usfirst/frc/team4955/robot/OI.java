package org.usfirst.frc.team4955.robot;

import org.usfirst.frc.team4955.robot.commands.WinchPull;
import org.usfirst.frc.team4955.robot.commands.WinchPush;
import org.usfirst.frc.team4955.robot.commands.ball.StartConveyor;
import org.usfirst.frc.team4955.robot.commands.ball.StopPickup;
import org.usfirst.frc.team4955.robot.utils.driveTrain.InverseDriveTrainCommand;
import org.usfirst.frc.team4955.robot.utils.driveTrain.SetDriveTrainMaxOutputCommand;
import org.usfirst.frc.team4955.robot.utils.input.GyroRatationInput;
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
	
	public static Joystick mainJoystick;
	private static TeleopInput controlerRotateInput;
	public static GyroRatationInput controlerGyroInput;
	public static TeleopInput controlerMoveInput;
	
	public static GamepadButton REVERSE_DRIVE_BUTTOM_NUMBER = GamepadButton.A;
	public static GamepadButton SLOW_DRIVE_OUTPUT_BUTTOM_NUMBER = GamepadButton.B;
	public static GamepadButton DRIVE_OUTPUT_LOWER_BUTTOM_NUMBER = GamepadButton.LB;
	public static GamepadButton DRIVE_OUTPUT_RAISE_BUTTOM_NUMBER = GamepadButton.RB;
	public static GamepadButton BALL_PICKUP = GamepadButton.X;
	public static GamepadButton STOP_BALL_PICKUP = GamepadButton.Y;
	public static GamepadButton WINCH_PULL = GamepadButton.Start;
	public static GamepadButton WINCH_PUSH = GamepadButton.Back;
	public static GamepadButton THROWER_STOP = GamepadButton.LB; 
	
	public static void init(){
		mainJoystick = new Joystick(0);
		
		controlerRotateInput = new JoystickInput(mainJoystick, GamepadAxis.LeftX.value(), 0.14);
		controlerGyroInput = new GyroRatationInput(controlerRotateInput, RobotMap.gyro);
		controlerMoveInput = new JoystickInput(mainJoystick, GamepadAxis.LeftY.value(), 0.14);
		RobotMap.driveTrain.rotateInput = controlerGyroInput;
		RobotMap.driveTrain.moveInput = controlerMoveInput;
		
		
		JoystickButton command = null;
		
		
		//Drive
		if(Robot.driveSubsystem.isPresent()){
			command = new JoystickButton(mainJoystick, REVERSE_DRIVE_BUTTOM_NUMBER.value());
	        command.whenPressed(new InverseDriveTrainCommand(RobotMap.driveTrain));
	        command = new JoystickButton(mainJoystick, SLOW_DRIVE_OUTPUT_BUTTOM_NUMBER.value());
	        command.toggleWhenActive(new SetDriveTrainMaxOutputCommand(RobotMap.driveTrain, 0.4));	
		}
		
        
        // Ball pick-up
		if(Robot.ballPickUpSubsytem.isPresent()){
			command = new JoystickButton(mainJoystick, BALL_PICKUP.value());
			command.toggleWhenActive(new StartConveyor());
			command = new JoystickButton(mainJoystick, STOP_BALL_PICKUP.value());
			command.toggleWhenActive(new StopPickup());	
		}
        
		
		//Winch
		if(Robot.wrinchSubsytem.isPresent()){
			command = new JoystickButton(mainJoystick, WINCH_PULL.value());
			command.toggleWhenActive(new WinchPull());
			
			command = new JoystickButton(mainJoystick, WINCH_PUSH.value());
			command.toggleWhenActive(new WinchPush());	
		}
				
	}
}
