package com.hackernews.job_search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.firebaseio.hacker_news.client.ApiException;
import com.firebaseio.hacker_news.hackernews.api.ItemApi;
import com.firebaseio.hacker_news.hackernews.model.Item;

/**
 * 
 * @author pratik palashikar pratikpalashikar@gmail.com
 * 
 *
 */
public class App {

	private String id;

	/**
	 * List containing all the jobs
	 */
	private List<Item> listOfItems = new ArrayList<Item>();

	/**
	 * List Containing only the relevant jobs
	 */
	private List<Item> listOfRelevantJob = new ArrayList<Item>();

	private static final String KEYWORDS = "software | developer | backend | java | engineer | graduate | master | students | hiring | immidiate | interview";

	/**
	 * Get the particular Job Item
	 */
	public void testGetItem() {

		try {
			ItemApi itemApi = new ItemApi();
			Item response = itemApi.get(id);
			listOfItems.add(response);

		} catch (ApiException e) {
			System.out.printf("ApiException caught: %s\n", e.getMessage());
		}
	}

	/**
	 * This method retrieves all the Ids
	 * 
	 * @return all the integers
	 */
	public List<Integer> getAllIds() {

		try {

			ItemApi itemApi = new ItemApi();
			List<Integer> allJobIds = itemApi.getAllJobIds();

			for (Integer jobId : allJobIds) {
				this.setId(jobId.toString());
				this.testGetItem();
			}

		} catch (Exception e) {
			System.out.printf("ApiException caught: %s\n", e.getMessage());
		}

		return null;
	}

	/**
	 * This methods will filter the specific jobs whether software
	 * developer,Java developer,developer,back-end developer
	 * 
	 * @return
	 */
	public List<Item> filterItems() {

		for (Item item : this.getListOfItems()) {

			String text = item.getTitle();
			// If the text is not null then match it with the regex to get the
			// specific jobs
			if (text != null) {
				Matcher m = Pattern.compile(KEYWORDS).matcher(text.toLowerCase());
				if (m.find()) {
					listOfRelevantJob.add(item);
				}
			}

		}

		return listOfRelevantJob;
	}

	public void writeToFile() {

		try {
			
			BufferedWriter out = new BufferedWriter(new FileWriter(new File("Job.txt")));
			for(Item item:this.getListOfRelevantJob()){
				out.write(item.toString());
			}
			out.close();
		} catch (IOException e) {
			System.out.println("Err with file");
		}
	}

	public static void main(String[] args) {

		System.out.println("Calling all Jobs :) ");

		App a = new App();
		//Get all the job Ids
		a.getAllIds();
		//filter the job ids to relevant job
		a.filterItems();
		//Write it to the file
		a.writeToFile();
		
		System.out.println("Done");

	}

	/**
	 * @return the listOfRelevantJob
	 */
	public List<Item> getListOfRelevantJob() {
		return listOfRelevantJob;
	}

	/**
	 * @param listOfRelevantJob
	 *            the listOfRelevantJob to set
	 */
	public void setListOfRelevantJob(List<Item> listOfRelevantJob) {
		this.listOfRelevantJob = listOfRelevantJob;
	}

	/**
	 * @return the listOfItems
	 */
	public List<Item> getListOfItems() {
		return listOfItems;
	}

	/**
	 * @param listOfItems
	 *            the listOfItems to set
	 */
	public void setListOfItems(List<Item> listOfItems) {
		this.listOfItems = listOfItems;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
