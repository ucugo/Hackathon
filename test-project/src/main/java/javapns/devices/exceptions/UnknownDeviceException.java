package javapns.devices.exceptions;


@SuppressWarnings("serial")
public class UnknownDeviceException extends Exception {

  /* Custom message for this exception */
  private String message;

  /**
   * Constructor
   */
  public UnknownDeviceException() {
    this.message = "Unknown client";
  }

  /**
   * Constructor with custom message
   * @param message
   */
  public UnknownDeviceException(String message) {
    this.message = message;
  }

  /**
   * String representation
   */
  @Override
  public String toString() {
    return this.message;
  }
}