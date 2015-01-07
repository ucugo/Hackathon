package hackaton.devices.exceptions;

public class NullDeviceTokenException extends Exception {
  /* Custom message for this exception */
  private String message;


  public NullDeviceTokenException() {
    this.message = "Client already exists";
  }

  /**
   * Constructor with custom message
   * @param message
   */
  public NullDeviceTokenException(String message) {
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