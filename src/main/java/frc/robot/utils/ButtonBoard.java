package frc.robot.utils;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;  

public class ButtonBoard {

    public enum Button {
        /** Left bumper. */
        kBottomRightBlack(5),
        /** Right bumper. */
        kTopRightBlack(6),
        /** Left stick. */
        kWhiteLeft(9),
        /** Right stick. */
        kWhiteRight(10),
        /** A. */
        kGreen(1),
        /** B. */
        kRed(2),
        /** X. */
        kBlue(3),
        /** Y. */
        kYellow(4),
        /** Back. */
        kBlackSelect(7),
        /** Start. */
        kBlackStart(8),
        
        kTopLeftBlack(11),
        kBottomLeftBlack(12);
    
        /** Button value. */
        public final int value;
        public boolean previousValue;
    
        Button(int value) {
          this.value = value;
        }
    }


    private int buttonPreset = 0;
    private XboxController controller;

    public ButtonBoard(int port) {
        this.controller = new XboxController(port);
    }

    public void bindToButton(int slot, Button button, Command onTrue, Command onFalse) {
        JoystickButton joystickButton = new JoystickButton(this.controller, button.value);
        BooleanSupplier slotBoolSupplier = () -> {return slot == buttonPreset;};
        joystickButton.and(slotBoolSupplier);
        if(onTrue != null) {
            joystickButton.onTrue(onTrue);
        }
        if(onFalse != null) {
            joystickButton.onFalse(onFalse);
        }
    }

    public void bindToPOV(int slot, int POV, Command onTrue, Command onFalse) {
        POVButton joystickButton = new POVButton(this.controller, POV);
        BooleanSupplier slotBoolSupplier = () -> {return slot == buttonPreset;};
        joystickButton.and(slotBoolSupplier);
        if(onTrue != null) {
            joystickButton.onTrue(onTrue);
        }
        if(onFalse != null) {
            joystickButton.onFalse(onFalse);
        }
    }

    public void bindToLeftTriggure(int slot, Command onTrue, Command onFalse) {
        BooleanSupplier slotBoolSupplier = () -> {return slot == buttonPreset;};
        BooleanSupplier triggureBooleanSupplier = () -> {return this.controller.getLeftTriggerAxis() >= .2;};
        Trigger trigger = new Trigger(triggureBooleanSupplier);
        trigger.and(slotBoolSupplier);
        if(onTrue != null) {
            trigger.onTrue(onTrue);
        }
        if(onFalse != null) {
            trigger.onFalse(onFalse);
        }
    }

    public void bindToRightTriggure(int slot, Command onTrue, Command onFalse) {
        BooleanSupplier slotBoolSupplier = () -> {return slot == buttonPreset;};
        BooleanSupplier triggureBooleanSupplier = () -> {return this.controller.getRightTriggerAxis() >= .2;};
        Trigger trigger = new Trigger(triggureBooleanSupplier);
        trigger.and(slotBoolSupplier);
        if(onTrue != null) {
            trigger.onTrue(onTrue);
        }
        if(onFalse != null) {
            trigger.onFalse(onFalse);
        }
    }

    public int getPreset() {
        return this.buttonPreset;
    }
    public void setPreset(int preset) {
        this.buttonPreset = preset;
    }

    public XboxController getController() {
        return controller;
    }


}
