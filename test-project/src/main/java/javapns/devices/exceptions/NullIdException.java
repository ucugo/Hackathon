package javapns.devices.exceptions;


@SuppressWarnings("serial")
public class NullIdException extends Exception {

  /* Custom message for this exception */
  private String message;

  /**
   * Constructor
   */
  public NullIdException() {
    this.message = "Client already exists";
  }

  /**
   * Constructor with custom message
   * @param message
   */
  public NullIdException(String message) {
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