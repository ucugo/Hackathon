package hackaton.devices;

import hackaton.devices.exceptions.NullIdException;


public interface DeviceFactory {
  /**
   * Add a device to the map
   * @param id The local device id
   * @param token The device token
   * @return The device created
   * @throws hackaton.devices.exceptions.DuplicateDeviceException
   * @throws hackaton.devices.exceptions.NullIdException
   * @throws hackaton.devices.exceptions.NullDeviceTokenException
   */
  public Device addDevice(String id, String token) throws hackaton.devices.exceptions.DuplicateDeviceException, NullIdException, hackaton.devices.exceptions.NullDeviceTokenException, Exception;

  /**
   * Get a device according to his id
   * @param id The local device id
   * @return The device
   * @throws hackaton.devices.exceptions.UnknownDeviceException
   * @throws hackaton.devices.exceptions.NullIdException
   */
  public Device getDevice(String id) throws hackaton.devices.exceptions.UnknownDeviceException, NullIdException;

  /**
   * Remove a device
   * @param id The local device id
   * @throws hackaton.devices.exceptions.UnknownDeviceException
   * @throws hackaton.devices.exceptions.NullIdException
   */
  public void removeDevice(String id) throws hackaton.devices.exceptions.UnknownDeviceException, NullIdException;
}