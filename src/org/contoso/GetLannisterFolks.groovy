package org.contoso

class RandomPerson {

    String id
    String firstName
    String lastName
    String email

    RandomPerson(id, firstName, lastName, email) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.group = group
    }

    String fullname() {
        return this.firstName + " " + this.lastName
    }
}

static def getApprovedPerson() {
    def getApprovedPersonList = []

    getApprovedPersonList.add("eddard")
    getApprovedPersonList.add("catelyn")
    getApprovedPersonList.add("robb")
    getApprovedPersonList.add("sansa")
    getApprovedPersonList.add("arya")

    return getApprovedPersonList
}


println getApprovedPerson().join(',')

import groovy.json.JsonSlurper

def getLannisters() {
    File LannisterPersonJsonFile = new File("../../../resources/org/contoso/LannisterFolks.json")
    def jsonSlurper = new JsonSlurper()
    def lannisterPersonJsonText = LannisterPersonJsonFile.getText()

    lannisterPersonJsonData = jsonSlurper.parseText(lannisterPersonJsonText)
    assert lannisterPersonJsonData instanceof ArrayList
    lannisterPersonJsonData.each { println it }

    def lannisterPersonList = lannisterPersonJsonData.username
    assert lannisterPersonList instanceof ArrayList
    return lannisterPersonList.join(',')
}

println getLannisters()