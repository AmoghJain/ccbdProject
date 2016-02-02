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
package org.cloudbus.cloudsimdisk.distributions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.distributions.ContinuousDistribution;

/**
 * A wikipedia Number generator. Each sample is read from a Wikipedia Workload file. The Wikipedia Workload file is
 * formatted according to WikiBench.s. More information at http://www.wikibench.eu/?page_id=60 .
 * 
 * @author Baptiste Louis
 * 
 */
public class MyWikiDistr implements ContinuousDistribution {
	
	/** The path to the Wikipedia Workload file. */
	private final String	path;
	
	/** The request arrival times */
	private List<Double>	times;
	
	/** Reader index */
	private int				index;
	
	/** The BASE TIME for this workload */
	private double			baseTime;
	
	/**
	 * Creates a new wikipedia number generator.
	 * 
	 * @param workloadFileName
	 *            the name of the wikipedia workload file. It has to be in "files/wikipedia" folder.
	 */
	public MyWikiDistr(
			String workloadFileName) {
		path = "files/" + workloadFileName;
		index = -1;
		times = new ArrayList<Double>();
		init();
	}
	
	/**
	 * Initializes the wikipedia generator.
	 */
	public void init() {
		
		try {
			
			// initializes a reader
			BufferedReader input = new BufferedReader(
					new FileReader(
							path));
			
			// initializes variables
			String line = "";
			String fullTimeStamp = "";
			String[] lineSplited;
			
			// read line by line
			while ((line = input.readLine()) != null) {
				
				// retrieve timeStamp
				lineSplited = line.split("\\s+"); // regular expression quantifiers for whitespace
				fullTimeStamp = lineSplited[1];
				
				// add time to the array
				times.add(Double.parseDouble(fullTimeStamp));
			}
			
			// define base Time (wikipedia workload Time-Stamp are not starting from 0)
			double tempDouble = times.get(0);
			baseTime = (int) tempDouble;
			
			// close the reader
			input.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get the next sample.
	 * 
	 * @return a time
	 */
	public double sample() {
		index++;
		return (times.get(index) - baseTime);
	}
}
