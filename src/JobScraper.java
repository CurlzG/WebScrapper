import java.io.IOException;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JobScraper {
public static Integer counter = 1;
public static Queue<String> listofJobs = new ArrayDeque<String>();
	public static void main(String[] args) throws IOException {
		FileWriter write = new FileWriter(new File("Jobs.csv"));
		System.out.println("Job Title you would like to scrap for");
		String jobTitle = "java";
		TradeSetUp(jobTitle);
		for(String a : listofJobs) {
			write.append(a + "\n");
		}
	}
	
	/**
	 * this sets up the trademe page for getting to scrap the information
	 * @param name
	 * @throws IOException
	 */
	public static void TradeSetUp(String name) throws IOException {
		
		String url = "https://www.trademe.co.nz/a/jobs/search?search_string=" + name;
		String temp = "&page=";
		System.out.println(url);
		Document doc = Jsoup.connect(url).get();
		Elements pages = doc.select("h3.tm-search-header-result-count__heading");
		System.out.println(pages.text());
		int m = PagesFitler(pages.text());
			for(int i = 1; i != m+1; i++) {
			System.out.println(i);
			Trademe(url+temp+i);
		}
		System.out.println("END");
	}
	/**
	 * This runs through the actually gathering the information about pages
	 * @param name
	 * @throws IOException
	 */
	public static void Trademe(String name) throws IOException{
		Document doc = Jsoup.connect(name).get();
		//Elements Titles = doc.select("tm-search-card-switcher").select("div.tm-jobs-search-card__title");
		Elements tags = doc.select("a.tm-jobs-search-card__link.o-card");
		for(Element t : tags) {
			//System.out.println(t.text().replace(",", "."));
			listofJobs.add(t.text().replace(",", "."));
		}
		//Elements tags = doc.select("tm-jobs-search-card__link o-card.ng-star-inserted");
	}
	/**
	 * This is the filter to calcualate what the page amount is
	 * @param text
	 * @return
	 */
	public static int PagesFitler(String text) {
		String name = text.replaceAll("[^0-9]+","");
		System.out.println(" -->" +name);
		int n = (int)Math.round(Double.parseDouble(name)/20.00);
		return n;
	}
}
