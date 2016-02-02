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

package org.cloudbus.cloudsimdisk;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.distributions.ContinuousDistribution;
import org.cloudbus.cloudsim.distributions.ExponentialDistr;
import org.cloudbus.cloudsim.distributions.UniformDistr;
import org.cloudbus.cloudsim.lists.VmList;
import org.cloudbus.cloudsim.power.PowerDatacenterBroker;
import org.cloudbus.cloudsimdisk.distributions.MyBasicDistr;
import org.cloudbus.cloudsimdisk.distributions.MyWikiDistr;
import org.cloudbus.cloudsimdisk.util.WriteToResultFile;

/**
 * My Broker used for storage examples.
 * 
 * @author Baptiste Louis
 * 
 */
public class MyPowerDatacenterBroker extends PowerDatacenterBroker {

	/** History of requests (cloudlets) arrival rate. */
	private List<Double>			History	= new ArrayList<Double>();

	/** Distribution used for arrival rate. */
	private ContinuousDistribution	distri;

	/**
	 * Created a new datacenter Broker object. This Broker has been implemented for CloudSimDisk simulations. It can
	 * schedules the list of Cloudlet according to generic distributions (exponential, uniform). Also, it can read times
	 * from a file, in which each line correspond to one time.
	 * 
	 * @param name
	 *            name to be associated with this entity (as required by Sim_entity class from simjava package)
	 * @param distriType
	 *            the distribution type
	 * @param distriSource
	 *            the file containing time distribution
	 * @throws Exception
	 *             the exception
	 */
	public MyPowerDatacenterBroker(String name, String distriType, String distriSource) throws Exception {
		super(name);

		setDistri(distriType, distriSource);
	}

	/* (non-Javadoc)
	 * 
	 * @see org.cloudbus.cloudsim.DatacenterBroker#submitCloudlets() */
	@SuppressWarnings("javadoc")
	@Override
	protected void submitCloudlets() {

		// Initialize local variable
		int vmIndex = 0;
		double tempArrivalTime = 0;

		// For each Cloudlet of the Cloudlet list...
		for (Cloudlet cloudlet : getCloudletList()) {
			MyCloudlet myCloudlet = (MyCloudlet) cloudlet;

			// prepare spreadsheet results
			WriteToResultFile.AddValueToSheetTab(myCloudlet.getCloudletId(), myCloudlet.getCloudletId(), 0);

			// Vm binding check
			Vm vm;
			if (cloudlet.getVmId() == -1) { // if user didn't bind this cloudlet and it has not been executed yet
				vm = getVmsCreatedList().get(vmIndex);
			} else { // submit to the specific vm
				vm = VmList.getById(getVmsCreatedList(), cloudlet.getVmId());
				if (vm == null) { // vm was not created
					Log.printLine(CloudSim.clock() + ": " + getName() + ": Postponing execution of cloudlet "
							+ cloudlet.getCloudletId() + ": bount VM not available");
					continue;
				}
			}

			// set VM ID to the Cloudlet
			cloudlet.setVmId(vm.getId());

			// request arrival rate sample according to a specific distribution type
			tempArrivalTime = distri.sample();

			// store arrival time
			History.add(tempArrivalTime);
			WriteToResultFile.AddValueToSheetTab(tempArrivalTime, myCloudlet.getCloudletId(), 1);

			// each Cloudlet are scheduled according to the request arrival rate sample
			send(getVmsToDatacentersMap().get(vm.getId()), tempArrivalTime - CloudSim.clock(),
					CloudSimTags.CLOUDLET_SUBMIT, myCloudlet);
			Log.formatLine("%.1f: %s: Cloudlet #%3d is scheduled to be sent to VM #%3d at %7.3f second(s)",
					CloudSim.clock(), getName(), cloudlet.getCloudletId(), vm.getId(), tempArrivalTime);

			// increment variable
			cloudletsSubmitted++;
			vmIndex = (vmIndex + 1) % getVmsCreatedList().size();

			// update Cloudlet Submitted List
			getCloudletSubmittedList().add(cloudlet);
		}

		// remove submitted cloudlets from waiting list
		for (Cloudlet cloudlet : getCloudletSubmittedList()) {
			getCloudletList().remove(cloudlet);
		}
	}

	/**
	 * Gets the arrival time history
	 * 
	 * @return the history
	 */
	public List<Double> getArrivalTimeHistory() {
		return History;
	}

	/**
	 * Sets the request arrival distribution
	 * 
	 * @param type
	 *            the type of distribution
	 * @param source
	 *            the source file containing time distribution
	 */
	public void setDistri(String type, String source) {
		switch (type) {
			case "expo":
				distri = new ExponentialDistr(60); // arbitrary parameters
				break;
			case "unif":
				distri = new UniformDistr(0, 10); // arbitrary parameters
				break;
			case "basic":
				distri = new MyBasicDistr(source);
				break;
			case "wiki":
				distri = new MyWikiDistr(source);
				break;

			// SCALABILITY: add a case and declare your own distribution.

			default:
				distri = new UniformDistr(1, 1.0001); // arbitrary parameters
				break;
		}
	}
}
