package org.usfirst.frc.team4955.robot.commands.autonomous.vision;

import org.usfirst.frc.team4955.robot.Constants;
import org.usfirst.frc.team4955.robot.DashboardKeys;
import org.usfirst.frc.team4955.robot.Robot;
import org.usfirst.frc.team4955.robot.RobotMap;
import org.usfirst.frc.team4955.robot.vision.VisionThread;
import org.usfirst.frc.team4955.robot.vision.VisionThread.VisionState;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ApprocheTarget extends Command {
	VisionState		visionState;
	VisionThread	vt;
	double			distance;
	double			centerOffset;

	public ApprocheTarget(VisionState visionState, double distance, double centerOffset) {
		this.visionState = visionState;
		this.vt = Robot.cameraSubsystem.visionThread;
		this.distance = distance;
		this.centerOffset = centerOffset;
	}

	protected void initialize() {
		Robot.cameraSubsystem.visionThread.setVisionState(visionState);
		SmartDashboard.putString(DashboardKeys.AUTONOMOUS_STATUS, "Approching target");
	}

	@Override
	protected void execute() {
		double cx = GetCenter();
		double rotation = 0;
		if (vt.targetCenterNormalised == null) {

		} else if (cx < 0.5) {
			double t = 2 * (0.5 - cx);
			rotation = -Constants.EaseTurn(t);
		} else {
			double t = 1 - 2 * (1 - cx);
			rotation = Constants.EaseTurn(t);
		}

		RobotMap.driveTrain.arcadeDrive(0.7, rotation);
	}

	private double GetCenter() {
		if (vt.targetCenterNormalised == null) {
			return -1;
		} else {
			return vt.targetCenterNormalised.x + centerOffset;
		}
	}

	@Override
	protected boolean isFinished() {
		return vt.targetCenterNormalised != null & vt.targetDistance < distance;
	}
}