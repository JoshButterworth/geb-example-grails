package com.test

import demo.RoomService
import geb.spock.GebReportingSpec
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.IgnoreIf
import spock.lang.Stepwise
import com.test.pages.ListPage
import com.test.pages.CreatePage
import com.test.pages.ShowPage
import com.test.pages.EditPage

@Integration
@Stepwise
@IgnoreIf({ !System.getProperty('geb.env') })
class RoomCRUDSpec extends GebReportingSpec {

	def setup() {
		browser.baseUrl = "http://localhost:${serverPort}/"
	}

	def "there are no rooms"() {
		when:
		go('room/index')
		ListPage page = browser.page ListPage

		then:
		page.numberOfRows() == 0
	}

	def "add a room"() {
		given:
		ListPage page = browser.page ListPage

		when:
		page.newEntity()

		then:
		at CreatePage
	}
	
	def "enter the details"() {
		given:
		CreatePage page = browser.page CreatePage

		when:
		page.populate('name', "Room 101")
		page.save()

		then:
		at ShowPage
	}
	
	def "check the entered details"() {
		given:
		ShowPage page = browser.page ShowPage

		expect:
		page.value('Name') == "Room 101"
	}

	def "edit the details"() {
		given:
		ShowPage page = browser.page ShowPage

		when:
		page.edit()

		then:
		EditPage editPage = at EditPage
		editPage.populate('name', "Room101")

		when:
		editPage.update()

		then:
		at ShowPage
	}
	
	def "check in listing"() {
		when:
		ShowPage page = browser.page ShowPage
		page.nav.select('Room List')

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.entityRows.size() == 1

		when:
		def row = listPage.entityRow(0)

		then:
		row.cellText(0) == "Room101"
	}
	
	def "show row"() {
		given:
		ListPage page = browser.page ListPage

		when:
		page.select('Room101')

		then:
		at ShowPage
	}

	def "delete room"() {
		given:
		ShowPage page = browser.page ShowPage

		when:
		withConfirm { page.delete() }

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.message ==~ /Room .+ deleted/
		listPage.numberOfRows() == 0
	}
}
