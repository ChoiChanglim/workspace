package my.instagram.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pdfcrowd.Pdfcrowd;
import com.pdfcrowd.Pdfcrowd.HtmlToImageClient;

@Service
public class PdfcrowdService {
	@Autowired
	HtmlToImageClient htmlToImageClient;
	
	@Value("#{propinfo['TEMP_IMG_PATH']}")
    private String TEMP_IMG_PATH;
	
	
	public boolean makePng(String ukey){
		boolean isMake = false;
		try {
	        // configure the conversion
			htmlToImageClient.setOutputFormat("png");

	        // run the conversion and write the result to a file
	        //client.convertFileToFile("/path/to/MyLayout.html", "MyLayout.png");
			String url = "http://yourlucky.cafe24.com?ukey="+ukey;
			String toimg = TEMP_IMG_PATH+ukey+".png";
			htmlToImageClient.convertUrlToFile(url, toimg);
	    }catch(Pdfcrowd.Error why) {
	        // report the error to the standard error stream
	        System.err.println("Pdfcrowd Error: " + why);
	    }catch(IOException why) {
	        // report the error to the standard error stream
	        System.err.println("IO Error: " + why.getMessage());
	    }finally{
	    	isMake = true;
	    }
		return isMake;
	}
	
}
