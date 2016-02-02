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

package org.cloudbus.cloudsimdisk.models.hdd;

/**
 * Storage Model based on Toshiba MG04SCA Enterprise HDD Review (MG04SCA500E).
 * 
 * Link: http://www.storagereview.com/toshiba_mg04sca_enterprise_hdd_review
 * 
 * @author Baptiste Louis
 */
public class StorageModelHddToshibaEnterpriseMG04SCA500E extends StorageModelHdd {

	/* (non-Javadoc)
	 * 
	 * @see org.cloudbus.cloudsim.power.models.PowerModelSpecPower#getPowerData(int) */
	@Override
	protected Object getCharacteristic(int key) {
		switch (key) {
			case 0:
				return "Toshiba"; // Manufacturer 
			case 1:
				return "MG04SCA500E"; // Model Number
			case 2:
				return 5000000; // capacity (MB)
			case 3:
				return 0.00417; // Average Rotation Latency (s)
			case 4:
				return 0.009; // Average Seek Time (s)
			case 5:
				return 215.0; // Maximum Internal Data Transfer Rate (MB/s)
			default:
				return "n/a";

				// SCALABILITY: add new characteristic by adding new CASE.
				//
				// case <KEY_NUMBER>:
				// return <PARAMETER_VALUE>;
				//
		}
	}
}
