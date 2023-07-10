package dev.sergevas.iot;

import com.diozero.api.PinInfo;
import com.diozero.api.RuntimeIOException;
import com.diozero.sbc.DeviceFactoryHelper;
import org.tinylog.Logger;

import com.diozero.devices.LED;
import com.diozero.util.SleepUtil;

public class App {
	public static void main(String[] args) {
		if (args.length < 1) {
			Logger.error("Usage: {} <gpio>", App.class.getName());
			System.exit(1);
		}
		if (args.length == 1) {
			blinkLed(Integer.parseInt(args[0]));
		} else if (args.length == 2) {
			blinkLedWithChipInfo(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		}
	}

	private static void blinkLedWithChipInfo(int chip, int line) {
		Logger.info("blinkLedWithChipInfo({}, {})", chip, line);
		PinInfo pin_info = DeviceFactoryHelper.getNativeDeviceFactory().getBoardPinInfo()
				.getByChipAndLineOffsetOrThrow(chip, line);
		try (LED led = new LED(pin_info, true, false)) {
			for (int i=0; i<5; i++) {
				Logger.info("LED on");
				led.on();
				SleepUtil.sleepMillis(500);
				Logger.info("LED off");
				led.off();
				SleepUtil.sleepMillis(500);
			}
		} catch (RuntimeIOException e) {
			Logger.error(e, "Error: {}", e);
		} finally {
			// Required if there are non-daemon threads that will prevent the
			// built-in clean-up routines from running
			DeviceFactoryHelper.shutdown();
		}
	}

	private static void blinkLed(int gpio) {
		Logger.info("blinkLed({})", gpio);
		try (LED led = new LED(gpio)) {
			for (int i=0; i<5; i++) {
				Logger.info("LED on");
				led.on();
				SleepUtil.sleepMillis(500);
				Logger.info("LED off");
				led.off();
				SleepUtil.sleepMillis(500);
			}
		}
	}
}
