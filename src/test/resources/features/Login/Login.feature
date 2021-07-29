#@Ignore
Feature: Validating Login Api with UserID 

@Login @Sanity 
Scenario Outline: User validate user with userName and Password and 
	Given User enters Valid LoginID "<UserName>" and "<PassWord>" and "<AccountNumber>" and get the get the Login Token
	
	
	Examples: 
		|UserName| PassWord | AccountNumber |
		|AutoUser17371|Demo@12345 | 411002248606 |