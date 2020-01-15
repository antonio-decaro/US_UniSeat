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

url = 'jdbc:mysql://localhost:3306/UniSeatDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET'

CustomKeywords.'db_connection.MySql.connectDB'(url, '', '', 'root', 'toor')

query = 'DELETE FROM utente WHERE email = \'m.rossi12@studenti.unisa.it\''

CustomKeywords.'db_connection.MySql.execute'(query)

query = 'INSERT INTO utente(email, nome, cognome, password, tipo) VALUES(\'m.rossi12@studenti.unisa.it\', \'Mario\', \'Rossi\', SHA2(\'MarioRossi12\', 256), \'STUDENTE\')'

CustomKeywords.'db_connection.MySql.execute'(query)

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/login?email=m.rossi12@studenti.unisa.it&password=MarioRossi12')

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Home/a_Impostazioni'))

WebUI.navigateToUrl('http://localhost:8080/_studente/VisualizzaProfilo.jsp')

WebUI.click(findTestObject('Object Repository/Page_Profilo/button_Modifica'))

WebUI.click(findTestObject('Object Repository/Page_Profilo/input_Impostazioni_nome'))

WebUI.setText(findTestObject('Page_Profilo/inputCognome'), 'R00ssi')

WebUI.click(findTestObject('Object Repository/Page_Profilo/button_Conferma'))

actual = WebUI.getText(findTestObject('Page_Profilo/errorCognome'))

expected = 'Il campo Cognome non rispetta il formato'

assert actual == expected

WebUI.closeBrowser()

