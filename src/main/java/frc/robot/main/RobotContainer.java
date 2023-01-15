package frc.robot.main;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.implementation.ArmControllerImpl;
import frc.robot.implementation.DriveControllerImpl;
import frc.robot.implementation.PSTeleController;
import frc.robot.interfaces.ArmController;
import frc.robot.interfaces.DriveController;
import frc.robot.interfaces.TeleController;
import frc.robot.main.Constants.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private TeleController teleController = new PSTeleController(IOConstants.psDriverControllerPort);
  private DriveController driveController = new DriveControllerImpl();
  private ArmController armController = new ArmControllerImpl();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
  }

  public void teleOp() {
    if (teleController.shouldRoboMove()) {
      double speed = teleController.getSpeed();
      double rotation = teleController.getRotation();
      if ((speed>0) || (rotation!=0))
        driveController.move(speed, rotation);
    } else {
      driveController.stop();
    }

    if (teleController.shouldArmMove()) {
      double mag = teleController.getArmMovementMagnitude();
      double lift = teleController.getArmLiftMagnitude();
      if (mag>0)
        armController.extendArm(mag);
      else if (mag<0)
        armController.retractArm(-mag);
      if (lift > 0) {
        armController.liftArm(lift);
      }
      else if (lift < 0) {
        armController.lowerArm(-lift);
      }
      
    }

    if (teleController.shouldArmOpen()) {
      armController.openArm(10);
    }

    if (teleController.shouldArmClose()) {
      armController.openArm(10);
    }

    
    
    
  } 
}