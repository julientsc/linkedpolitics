import data.twitter.job.command.LimitAPIGetter;

public class TestLimitAPI {

	public static void main(String [] args) {
		
		try {
			
			LimitAPIGetter.getLimits();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
