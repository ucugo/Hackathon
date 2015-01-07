package hackaton.notification.exceptions;

/**
 * Thrown when an error response packet was received from an APNS server.
 * 
 * @author Sylvain Pedneault
 */
@SuppressWarnings("serial")
public class ErrorResponsePacketReceivedException extends Exception {

  private hackaton.notification.ResponsePacket packet;

  public ErrorResponsePacketReceivedException(hackaton.notification.ResponsePacket packet) {
    super(String.format("An error response packet was received from the APNS server: %s", packet.getMessage()));
    this.packet = packet;
  }

  public hackaton.notification.ResponsePacket getPacket() {
    return packet;
  }

}
