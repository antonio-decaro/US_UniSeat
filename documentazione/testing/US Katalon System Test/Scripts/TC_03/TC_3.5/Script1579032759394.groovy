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
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/')

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Home/a_Accedi'))

WebUI.setText(findTestObject('Object Repository/Page_Login/input_Accedi_email'), 'a.decaro@studenti.unisa.it')

WebUI.click(findTestObject('Object Repository/Page_Login/label_Password'))

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Login/input_E-Mail_password'), '2KQzu/1eCRtlfOXIb806pg==')

WebUI.click(findTestObject('Object Repository/Page_Login/button_Accedi'))

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Home/a_Seleziona'))

WebUI.navigateToUrl('http://localhost:8080/_comuni/aule.jsp?edificio=F3#services')

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Edificio F3/button_Prenota PostoP3'))

WebUI.setText(findTestObject('Object Repository/Page_UniSeat - Edificio F3/input_Durata                               _956af2'), 
    '30')

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Edificio F3/button_Prenota'))

actual = WebUI.getText(findTestObject('Object Repository/Page_UniSeat - Edificio F3/p_Prenotazione effettuata con successo'))

expected = 'Prenotazione effettuata con successo'

assert actual.equals(expected)

WebUI.closeBrowser()

CustomKeywords.'db_connection.MySql.connectDB'('jdbc:mysql://localhost:3306/UniSeatDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET', 
    '', '', 'root', 'toor')

CustomKeywords.'db_connection.MySql.execute'('DELETE FROM prenotazione;')

CustomKeywords.'db_connection.MySql.closeDatabaseConnection'()

WebUI.closeBrowser()

