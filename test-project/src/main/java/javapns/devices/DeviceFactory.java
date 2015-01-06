package javapns.devices;

import javapns.devices.exceptions.DuplicateDeviceException;
import javapns.devices.exceptions.NullDeviceTokenException;
import javapns.devices.exceptions.NullIdException;
import javapns.devices.exceptions.UnknownDeviceException;


public interface DeviceFactory {
  /**
   * Add a device to the map
   * @param id The local device id
   * @param token The device token
   * @return The device created
   * @throws javapns.devices.exceptions.DuplicateDeviceException
   * @throws javapns.devices.exceptions.NullIdException
   * @throws javapns.devices.exceptions.NullDeviceTokenException
   */
  public Device addDevice(String id, String token) throws DuplicateDeviceException, NullIdException, NullDeviceTokenException, Exception;

  /**
   * Get a device according to his id
   * @param id The local device id
   * @return The device
   * @throws javapns.devices.exceptions.UnknownDeviceException
   * @throws javapns.devices.exceptions.NullIdException
   */
  public Device getDevice(String id) throws UnknownDeviceException, NullIdException;

  /**
   * Remove a device
   * @param id The local device id
   * @throws javapns.devices.exceptions.UnknownDeviceException
   * @throws javapns.devices.exceptions.NullIdException
   */
  public void removeDevice(String id) throws UnknownDeviceException, NullIdException;
}