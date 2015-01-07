package hackaton.notification;

/**
 * A one-to-one link between a payload and device.
 * Provides support for a typical payload-per-device scenario.
 * 
 * @author Sylvain Pedneault
 */
public class PayloadPerDevice {
  private Payload payload;
  private hackaton.devices.Device device;

  public PayloadPerDevice(Payload payload, String token) throws hackaton.devices.exceptions.InvalidDeviceTokenFormatException {
    super();
    this.payload = payload;
    this.device = new hackaton.devices.implementations.basic.BasicDevice(token);
  }

  public PayloadPerDevice(Payload payload, hackaton.devices.Device device) {
    super();
    this.payload = payload;
    this.device = device;
  }

  public Payload getPayload() {
    return payload;
  }

  public hackaton.devices.Device getDevice() {
    return device;
  }
}