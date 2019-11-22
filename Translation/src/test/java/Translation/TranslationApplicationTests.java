package Translation;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TranslationApplicationTests {

	@Test
	void contextLoads() {
		   System.setProperty("selenide.headless","true");
		   open("https://translate.google.com/?hl=ru#view=home&op=translate&sl=en&tl=ru");
	        String[] strings = {"KIng"};//some strings to translate
	        for (String data: strings) {
	            $x("//textarea[@id='source']").clear();
	            $x("//textarea[@id='source']").sendKeys(data);
	            String translation = $x("//span[@class='tlid-translation translation']").getText();
	            System.out.println("--------------------"+translation);
	        }
	
	}
	
	
	

}
