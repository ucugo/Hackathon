package javapns.devices.exceptions;


public class DuplicateDeviceException extends Exception {
  /* Custom message for this exception */
  private String message;

  /**
   * Constructor
   */
  public DuplicateDeviceException() {
    this.message = "Client already exists";
  }

  /**
   * Constructor with custom message
   * @param message
   */
  public DuplicateDeviceException(String message) {
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