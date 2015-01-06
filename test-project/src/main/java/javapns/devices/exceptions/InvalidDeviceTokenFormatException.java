package javapns.devices.exceptions;


public class InvalidDeviceTokenFormatException extends Exception {
  public InvalidDeviceTokenFormatException(String message) {
    super(message);
  }

  public InvalidDeviceTokenFormatException(String token, String problem) {
    super(String.format("Device token cannot be parsed, most likely because it contains invalid hexadecimal characters: %s in %s", problem, token));
  }
}