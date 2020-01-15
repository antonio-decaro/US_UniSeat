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

query = 'INSERT INTO prenotazione(id, utente, aula, data, ora_inizio, ora_fine, tipo) VALUES(\'1000\', \'c.gravino@unisa.it\', \'3\', \'2020-12-12\', \'15:00:00\', \'16:00:00\', \'AULA\')'

CustomKeywords.'db_connection.MySql.execute'(query)

query = 'INSERT INTO prenotazione(id, utente, aula, data, ora_inizio, ora_fine, tipo) VALUES(\'1001\', \'a.decaro@studenti.unisa.it\', \'3\', \'2020-10-12\', \'10:00:00\', \'12:30:00\', \'POSTO\')'

CustomKeywords.'db_connection.MySql.execute'(query)

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/login?email=a.depasquale@unisa.it&password=Depasquale1')

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Prenotazioni/a_Visualizza'))

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Home/a_Visualizza prenotazioni'))

WebUI.delay(1)

gravinoMail = WebUI.getText(findTestObject('Object Repository/Page_UniSeat - Prenotazioni/th_cgravinounisait'))

decaroMail = WebUI.getText(findTestObject('Object Repository/Page_UniSeat - Prenotazioni/th_adecarostudentiunisait'))

assert gravinoMail == "c.gravino@unisa.it"

assert decaroMail == "a.decaro@studenti.unisa.it"

WebUI.closeBrowser()

query = "DELETE FROM prenotazione WHERE id = 1000"
CustomKeywords.'db_connection.MySql.execute'(query)

query = "DELETE FROM prenotazione WHERE id = 1001"
CustomKeywords.'db_connection.MySql.execute'(query)

CustomKeywords.'db_connection.MySql.closeDatabaseConnection'();

