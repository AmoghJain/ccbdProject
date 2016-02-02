/*******************************************************************************
 * Title: CloudSimDisk
 * Description: a module for energy aware storage simulation in CloudSim
 * Author: Baptiste Louis
 * Date: June 2015
 *
 * Address: baptiste_louis@live.fr
 * Source: https://github.com/Udacity2048/CloudSimDisk
 * Website: http://baptistelouis.weebly.com/projects.html
 *
 * Licence: GPL - http://www.gnu.org/copyleft/gpl.html
 * Copyright (c) 2015, Lule� University of Technology, Sweden.
 *******************************************************************************/

package org.cloudbus.cloudsimdisk.power.models.hdd;

/**
 * The abstract class of power models for hard disk drives.
 * 
 * @author Baptiste Louis
 */
public abstract class PowerModelHdd {

	// Abstract Method that need to be implemented.

	/**
	 * Gets the power data for a specific mode.
	 * 
	 * @param key
	 *            0 for Idle mode, 1 for Active mode.
	 * @return the power data
	 */
	protected abstract Object getPowerData(int key);

	// Non-abstract Method to retrieve a specific parameter.

	/**
	 * Gets the Power of a specified mode.
	 * 
	 * @param mode
	 *            the mode
	 * 
	 * @return the power
	 */
	public double getPower(int mode) {
		return (double) getPowerData(mode);
	}

	/**
	 * Gets the Power in Idle mode.
	 * 
	 * @return the power
	 */
	public double getPowerIdle() {
		return (double) getPowerData(0);
	}

	/**
	 * Gets the Power in Active mode.
	 * 
	 * @return the power
	 */
	public double getPowerActive() {
		return (double) getPowerData(1);
	}

	/*-----------------------------------------------------------------------
	|  SCALABILITY: create new GETTERs to retrieve power for additional modes.
	|
	|  public <TYPE> getPowerOfYourMode() {
	|      return (CAST_OBJECT) getPowerData(<KEY_NUMBER>);
	|  }
	|
	 *---------------------------------------------------------------------*/
}
