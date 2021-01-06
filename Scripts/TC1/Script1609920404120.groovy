import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Path page = Paths.get(RunConfiguration.getProjectDir()).resolve("src/web/page.html")
assert Files.exists(page)
String url = page.toFile().toURI().toURL().toString()

WebUI.openBrowser(url)
WebUI.setViewPortSize(800,600)
WebUI.waitForPageLoad(10)

TestObject tObj1 = buildTestObjectForElementWithText("Hello World", '//p')
WebUI.comment("looking for an element : ${tObj1.toString()}")
WebUI.verifyElementPresent(tObj1, 10, FailureHandling.CONTINUE_ON_FAILURE)

TestObject tObj2 = buildTestObjectForElementWithText(
	"Ultimatrix Data Hub : DHANYA(527014) have reject 3 row(s) from S1 in DemoMailer2 - 2020 FY Q4 - Projection")
WebUI.comment("looking for an element : ${tObj2.toString()}")
WebUI.verifyElementPresent(tObj2, 10, FailureHandling.CONTINUE_ON_FAILURE)

TestObject tObj3 = buildTestObjectForElementWithText("Hasta la vista, baby")
WebUI.comment("looking for an element : ${tObj3.toString()}")
WebUI.verifyElementPresent(tObj3, 10, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.closeBrowser()

/*
 * 
 */
TestObject buildTestObjectForElementWithText(String text, String base='//span') {
	Objects.requireNonNull(text)
	Objects.requireNonNull(base)
	List<String> words = text.split('\\s') as List
	//
	StringBuilder predicate = new StringBuilder()
	predicate.append("[")
	words.eachWithIndex { word, index ->
		if (index > 0)
			predicate.append(' and ')
		if (word.length() > 0) {
			predicate.append('contains(.,\"')
			predicate.append(word)
			predicate.append('\")')
		}	
	}
	predicate.append("]")
	//
	TestObject tObj = new TestObject(text)
	tObj.addProperty("xpath", ConditionType.EQUALS,
		base + predicate.toString())
	return tObj
}
