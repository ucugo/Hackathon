package hackaton.devices.implementations.basic;

import java.sql.Timestamp;


public class BasicDevice implements hackaton.devices.Device {

  /*
   * An id representing a particular device.
   * 
   * Note that this is a local reference to the device, which is not related to the actual device UUID or other device-specific identification. Most of the time, this deviceId should be the same as the token.
   */
  private String deviceId;

  /* The device token given by Apple Server, hexadecimal form, 64bits length */
  private String token;

  /* The last time a device registered */
  private Timestamp lastRegister;

  /**
   * Default constructor.
   * @param token The device token
   */
  public BasicDevice(String token) throws hackaton.devices.exceptions.InvalidDeviceTokenFormatException {
    this(token, true);
  }

  public BasicDevice(String token, boolean validate) throws hackaton.devices.exceptions.InvalidDeviceTokenFormatException {
    super();
    this.deviceId = token;
    this.token = token;
    try {
      this.lastRegister = new Timestamp(System.currentTimeMillis());
    } catch (Exception e) {
      // swallow
    }
    if (validate)
      validateTokenFormat(token);
  }

  public BasicDevice() {
  }

  public void validateTokenFormat() throws hackaton.devices.exceptions.InvalidDeviceTokenFormatException {
    validateTokenFormat(token);
  }

  public static void validateTokenFormat(String token) throws hackaton.devices.exceptions.InvalidDeviceTokenFormatException {
    if (token == null) {
      throw new hackaton.devices.exceptions.InvalidDeviceTokenFormatException("Device Token is null, and not the required 64 bytes...");
    }
    if (token.getBytes().length != 64) {
      throw new hackaton.devices.exceptions.InvalidDeviceTokenFormatException("Device Token has a length of [" + token.getBytes().length + "] and not the required 64 bytes!");
    }
  }

  /**
   * Constructor
   * @param id The device id
   * @param token The device token
   */
  public BasicDevice(String id, String token, Timestamp register) throws hackaton.devices.exceptions.InvalidDeviceTokenFormatException {
    super();
    this.deviceId = id;
    this.token = token;
    this.lastRegister = register;

    validateTokenFormat(token);

  }

  /**
   * Getter
   * @return the device id
   */
  @Override
  public String getDeviceId() {
    return deviceId;
  }

  /**
   * Getter
   * @return the device token
   */
  @Override
  public String getToken() {
    return token;
  }

  /**
   * Getter
   * @return the last register
   */
  @Override
  public Timestamp getLastRegister() {
    return lastRegister;
  }

  /**
   * Setter
   * @param id the device id
   */
  @Override
  public void setDeviceId(String id) {
    this.deviceId = id;
  }

  /**
   * Setter the device token
   * @param token
   */
  @Override
  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public void setLastRegister(Timestamp lastRegister) {
    this.lastRegister = lastRegister;
  }

}
