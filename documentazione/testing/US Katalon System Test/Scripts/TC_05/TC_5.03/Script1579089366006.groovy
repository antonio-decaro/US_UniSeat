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

WebUI.navigateToUrl('http://localhost:8080/login?email=a.depasquale@unisa.it&password=Depasquale1')

WebUI.navigateToUrl('http://localhost:8080/index.jsp')

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Home/a_Inserisci'))

WebUI.click(findTestObject('Object Repository/Page_UniSeat - Home/a_Inserisci aula'))

WebUI.selectOptionByValue(findTestObject('Object Repository/Page_UniSeat/select_F                                   _9bc053'), 
    'F3', true)

WebUI.click(findTestObject('Object Repository/Page_UniSeat/input_Nome_nome_aula'))

WebUI.setText(findTestObject('Page_UniSeat/input_Nome_nome_aula'), 'nomeaulanonvalido')

WebUI.setText(findTestObject('Object Repository/Page_UniSeat/input_Posti Aula_numero_posti'), '100')

WebUI.click(findTestObject('Object Repository/Page_UniSeat/button_Servizi_setComputer'))

WebUI.click(findTestObject('Object Repository/Page_UniSeat/button_Inserisci'))

actual = WebUI.getText(findTestObject('Object Repository/Page_UniSeat/h6_Nome aula non valido'))

expected = 'Nome aula non valido'

assert actual == expected

WebUI.closeBrowser()

