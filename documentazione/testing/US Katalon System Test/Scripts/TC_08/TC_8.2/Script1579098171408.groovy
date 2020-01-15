import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/login?email=c.gravino@unisa.it&password=Gravino1')

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Home/a_La mia prenotazione'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Prenotazioni/button_Elimina prenotazione'))

WebUI.delay(1)

WebUI.navigateToUrl('http://localhost:8080/_comuni/prenotazione.jsp')

actual = WebUI.getText(findTestObject('Object Repository/Page_UniSeat - Prenotazioni/h3_Non ci sono prenotazioni attive'))

expected = 'NON CI SONO PRENOTAZIONI ATTIVE'

assert actual == expected

WebUI.closeBrowser()

