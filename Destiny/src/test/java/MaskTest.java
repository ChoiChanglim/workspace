import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import my.random.api.util.StringUtil;


public class MaskTest {

	public static void main(String[] args) {
		String src = "aaa42f";
		
		String masked = StringUtil.MaskingName(src);
		System.err.println(masked);
	}
	
	

}
