package ch.eiafr.tsc.data.lib.stats;

public class Stats {

	public static int quantityOfProcessedPage = 0;
	public static int quantityOfPagesAlreadyOK = 0;
	
	public static void addProcessedPage() {
		quantityOfProcessedPage++;
	}
	
	public static void addPageAlreadyOk() {
		quantityOfPagesAlreadyOK++;
	}
	
	public static void reset() {
		quantityOfProcessedPage = 0;
		quantityOfPagesAlreadyOK = 0;
	}
	
	public static void printResult() {
		System.out.println("\nStatistics");
		System.out.println("==========");
		System.out.println("Quantity of processed pages : " + quantityOfProcessedPage);
		System.out.println("Quantity of pages already OK : " + quantityOfPagesAlreadyOK);
	}
}