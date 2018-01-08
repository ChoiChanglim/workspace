import java.io.IOException;

import my.instagram.service.PdfcrowdService;

import com.pdfcrowd.Pdfcrowd;
import com.pdfcrowd.Pdfcrowd.HtmlToImageClient;

public class ApiTest {
	    public static void main(String[] args) {
	        try {
	            // create the API client instance
	            HtmlToImageClient client = new HtmlToImageClient("battle9", "aecb25cad6d93f1af11ada233df36146");

	            // configure the conversion
	            client.setOutputFormat("png");

	            // run the conversion and write the result to a file
	            //client.convertFileToFile("/path/to/MyLayout.html", "MyLayout.png");
	            String path = PdfcrowdService.class.getResource("/").getPath()+"MyLayout.png";
	            System.err.println(path);
	            client.convertUrlToFile("http://yourlucky.cafe24.com?ukey=a001f1", path);
	        }
	        catch(Pdfcrowd.Error why) {
	            // report the error to the standard error stream
	            System.err.println("Pdfcrowd Error: " + why);
	        }
	        catch(IOException why) {
	            // report the error to the standard error stream
	            System.err.println("IO Error: " + why.getMessage());
	        }
	    }
	}