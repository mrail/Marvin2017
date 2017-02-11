package org.usfirst.frc.team4955.robot;

import org.usfirst.frc.team4955.robot.utils.driveTrain.DriveTrainControler;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static DriveTrainControler driveTrain;
	
	// Winch
	public static Talon winchTalon;
	
	//Gear
	public static AnalogInput frontLeftSensor;
	public static AnalogInput frontRightSensor;
	
	// Ball pickup 
	public static Talon brushTalon;
	public static Talon elavatorTalon;
	
	//Ball shoot
	public static Talon feedWheelTalon;
	public static CANTalon shootWheelTalon;
	public static AnalogInput feederBallSensor;
	
	public static ADXRS450_Gyro gyro;
	
	public static void init(){
		driveTrain = new DriveTrainControler(0,1,2,3);
		
		//frontLeftSensor = new AnalogInput(0);	
		//frontRightSensor = new AnalogInput(1);
		
		gyro = new ADXRS450_Gyro();
		
		/*winchTalon = new Talon(0);
		brushTalon = new Talon(0);
		elavatorTalon = new Talon(0);
		feedWheelTalon = new Talon(0);
		shootWheelTalon = new CANTalon(0);
		feederBallSensor = new AnalogInput(0);*/
	}
}
