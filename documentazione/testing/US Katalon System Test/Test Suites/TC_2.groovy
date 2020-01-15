import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import db_connection.MySql

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.annotation.TearDownTestCase

/**
 * Some methods below are samples for using SetUp/TearDown in a test suite.
 */

/**
 * Setup test suite environment.
 */
@SetUp(skipped = false) // Please change skipped to be false to activate this method.
def setUp() {
	url = "jdbc:mysql://localhost:3306/UniSeatDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET"
	CustomKeywords.'db_connection.MySql.connectDB'(url, "", "", "root", "toor")
	query = 'DELETE FROM Utente WHERE email = \'m.rossi12@studenti.unisa.it\''
	CustomKeywords.'db_connection.MySql.execute'(query)
	query = 'INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES (\'Mario\',\'Rossi\',\'m.rossi12@studenti.unisa.it\',SHA2(\'MarioRossi12\', 256),\'STUDENTE\');'
	CustomKeywords.'db_connection.MySql.execute'(query)
	// Put your code here.
}

/**
 * Clean test suites environment.
 */
@TearDown(skipped = false) // Please change skipped to be false to activate this method.
def tearDown() {
	query = 'DELETE FROM Utente WHERE email = \'m.rossi12@studenti.unisa.it\''
	CustomKeywords.'db_connection.MySql.execute'(query)
	CustomKeywords.'db_connection.MySql.closeDatabaseConnection'()
}

/**
 * Run before each test case starts.
 */
@SetupTestCase(skipped = true) // Please change skipped to be false to activate this method.
def setupTestCase() {
	// Put your code here.
}

/**
 * Run after each test case ends.
 */
@TearDownTestCase(skipped = true) // Please change skipped to be false to activate this method.
def tearDownTestCase() {
	// Put your code here.
}

/**
 * References:
 * Groovy tutorial page: http://docs.groovy-lang.org/next/html/documentation/
 */