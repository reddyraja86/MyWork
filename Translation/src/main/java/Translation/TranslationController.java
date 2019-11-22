package Translation;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/translate")
public class TranslationController {

	@GetMapping("/test/{val}")
	public String translate(@PathVariable() String val) {
		String s="";
		
		   System.setProperty("selenide.headless","true");
		   open("https://translate.google.com/?hl=ru#view=home&op=translate&sl=en&tl=ru");
	        String[] strings = {val ,"queen"};
	        for (String data: strings) {
	            $x("//textarea[@id='source']").clear();
	            $x("//textarea[@id='source']").sendKeys(data);
	             s = $x("//span[@class='tlid-translation translation']").getText();
	        }
		
		
		return s;
	}
	
	@CrossOrigin(origins = "http://10.96.11.92:8080")
	@PostMapping("/uploadFile/{sl}/{tl}")
    public String uploadFile(@RequestParam("file") MultipartFile file,@PathVariable() String sl,@PathVariable() String tl) {
        
		BufferedReader br;
		String f="";
		List<String> result = new ArrayList<>();
		List<String> keyList = new ArrayList<>();
		Map<String,String> fileData = new LinkedHashMap<String,String>();
		try {

		     String line;
		     InputStream is = file.getInputStream();
		     br = new BufferedReader(new InputStreamReader(is));
		     
		     while ((line = br.readLine()) != null) {
		    	 String [] da=line.split(":");
		    	 	if(da!=null &&  da.length>1) {
		    	 		fileData.put(da[0], da[1]);
		    	 		//System.out.println("<option value=\""+da[1]+"\">"+da[0]+"-"+ da[1]+"</option>");
		    	 		result.add(da[1]);
		    	 		keyList.add(da[0]);
		    	 	}
		          
		     }
		     
		    f = callGoogleTransalte(result,sl,tl,fileData,keyList);

		  } catch (IOException e) {
		    System.err.println(e.getMessage());       
		  }

		return f;
		 
    }
	
	
	
	private String  callGoogleTransalte(List<String> l,String sl,String tl,Map<String,String> fileData,List<String> k){
		
		System.out.println("source language"+sl);
		System.out.println("Target language"+tl);
		Map<String,String>  result = new LinkedHashMap<String,String>();
		
		String f="";
		
		if(l != null && l.size()>0) {
			
			System.setProperty("selenide.headless","true");
			open("https://translate.google.com/?hl=ru#view=home&op=translate&sl="+sl.trim()+"&tl="+tl.trim());
		    String[] strings = l.toArray(new String[0]);
		    int index =0;
		     for (String data: strings) {
		            $x("//textarea[@id='source']").clear();
		            $x("//textarea[@id='source']").sendKeys(data);
		            String  s = $x("//span[@class='tlid-translation translation']").getText();
		            System.out.println(k.get(index)+":"+ s);
		             f= f+k.get(index)+":"+ s +"\n";
		            result.put(k.get(index),s);
		            
		            index++;
		            
		      }
			   
		}
		
		return f;
		//return result;
		
	}

	
	
}
