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
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/_studente/registrazione.jsp')

WebUI.setText(findTestObject('Page_UniSeat/input_Registrati_nome'), 'Mario')

WebUI.click(findTestObject('Page_UniSeat/label_Cognome'))

WebUI.setText(findTestObject('Page_UniSeat/input_Nome_cognome'), 'Rossi')

WebUI.setText(findTestObject('Page_UniSeat/input_Cognome_email'), 'm.rossi13@studenti.unisa.it')

WebUI.setText(findTestObject('Page_UniSeat/input_Email_password'), 'MarioRossi12')

WebUI.setText(findTestObject('Page_UniSeat/input_Password_confPassword'), 'MarioRossi12')

WebUI.click(findTestObject('Page_UniSeat/button_Registrati'))

actual = WebUI.getText(findTestObject('Page_UniSeat/div_message'))

expected = 'E-Mail di conferma inviata al tuo indirizzo di posta'

assert expected.equals(actual)

WebUI.closeBrowser()

CustomKeywords.'db_connection.MySql.connectDB'('jdbc:mysql://localhost:3306/UniSeatDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET', 
    'UniSeatDB', '3306', 'root', 'toor')

CustomKeywords.'db_connection.MySql.execute'('DELETE FROM Utente WHERE email = \'m.rossi13@studenti.unisa.it\'')

CustomKeywords.'db_connection.MySql.closeDatabaseConnection'()

