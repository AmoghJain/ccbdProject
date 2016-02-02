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

package org.cloudbus.cloudsimdisk.power;

import java.util.ArrayList;
import java.util.List;
import org.cloudbus.cloudsim.ParameterException;
import org.cloudbus.cloudsimdisk.MyHarddriveStorage;
import org.cloudbus.cloudsimdisk.models.hdd.StorageModelHdd;
import org.cloudbus.cloudsimdisk.power.models.hdd.PowerModelHdd;

/**
 * MyPowerHarddriveStorage enables simulation of power-aware Hard drives.
 * 
 * @author Baptiste Louis
 */
public class MyPowerHarddriveStorage extends MyHarddriveStorage {

	/** The power model */
	private PowerModelHdd	powerModelHdd;

	/** Duration in Active mode */
	public double			inActiveDuration;

	/** Idle Duration Intervals history (when the disk is in idle mode) */
	public List<Double>		IdleIntervalsHistory	= new ArrayList<Double>();

	/** Last starting Idle time */
	public double			LastIdleStartTime;

	/** Total energy in idle mode */
	private double			totalEnergyIdle;
	
	/** Total energy in active mode */
	private double			totalEnergyActive;

	/**
	 * Creates a new Hard Drive storage base on a specific HDD Model.
	 * 
	 * @param id
	 *            unique id of the HDD CloudSim component
	 * @param name
	 *            the name
	 * @param storageModelHdd
	 *            the specific model
	 * @param powerModel
	 *            the power model
	 * @throws ParameterException
	 *             when the name and the capacity are not valid
	 */
	public MyPowerHarddriveStorage(int id, String name, StorageModelHdd storageModelHdd, PowerModelHdd powerModel)
			throws ParameterException {
		super(id, name, storageModelHdd);

		// set HDD characteristics
		setPowerModelHdd(powerModel);

		// set initial parameters
		setInActiveDuration(0.0);
		setLastIdleStartTime(0.0);
	}

	// GETTERs and SETTERs

	/**
	 * Sets the power model.
	 * 
	 * @param powerModelHdd
	 *            the new power model
	 */
	protected void setPowerModelHdd(PowerModelHdd powerModelHdd) {
		this.powerModelHdd = powerModelHdd;
	}

	/**
	 * Gets the power model.
	 * 
	 * @return the power model
	 */
	public PowerModelHdd getPowerModelHdd() {
		return powerModelHdd;
	}

	/**
	 * Gets the power in Active mode
	 * 
	 * @return the power
	 */
	public double getPowerActive() {
		return getPowerModelHdd().getPowerActive();
	}

	/**
	 * Gets the power in Idle mode
	 * 
	 * @return the power
	 */
	public double getPowerIdle() {
		return getPowerModelHdd().getPowerIdle();
	}

	/**
	 * Gets the power of the current state of the drive
	 * 
	 * @return the power
	 */
	public double getPower() {
		return getPowerModelHdd().getPower(mode);
	}

	/**
	 * Gets the Active duration.
	 * 
	 * @return the inActiveDuration
	 */
	public double getInActiveDuration() {
		return inActiveDuration;
	}

	/**
	 * Sets the Active duration.
	 * 
	 * @param inActiveDuration
	 *            the inActiveDuration to set
	 */
	public void setInActiveDuration(double inActiveDuration) {
		this.inActiveDuration = inActiveDuration;
	}

	/**
	 * Gets the idle intervals history.
	 * 
	 * @return the idle Intervals history
	 */
	public List<Double> getIdleIntervalsHistory() {
		return IdleIntervalsHistory;
	}

	/**
	 * Gets the last Idle start time.
	 * 
	 * @return the lastIdleStartTime
	 */
	public double getLastIdleStartTime() {
		return LastIdleStartTime;
	}

	/**
	 * Sets the last Idle start time.
	 * 
	 * @param lastIdleStartTime
	 *            the lastIdleModeStartTime to set
	 */
	public void setLastIdleStartTime(double lastIdleStartTime) {
		LastIdleStartTime = lastIdleStartTime;
	}

	/**
	 * Gets the total energy consumed by this HDD in Idle mode.
	 * 
	 * @return the energy
	 */
	public double getTotalEnergyIdle() {
		return totalEnergyIdle;
	}

	/**
	 * Sets the total energy consumed by this HDD in Idle mode.
	 * 
	 * @param totalEnergyIdle the energy
	 */
	public void setTotalEnergyIdle(double totalEnergyIdle) {
		this.totalEnergyIdle = totalEnergyIdle;
	}

	/**
	 * Gets the total energy consumed by this HDD in Active mode.
	 * 
	 * @return the energy
	 */
	public double getTotalEnergyActive() {
		return totalEnergyActive;
	}

	/**
	 * Sets the total energy consumed by this HDD in Active mode.
	 * 
	 * @param totalEnergyActive the energy
	 */
	public void setTotalEnergyActive(double totalEnergyActive) {
		this.totalEnergyActive = totalEnergyActive;
	}
}
