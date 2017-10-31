package org.contoso

import groovy.json.JsonSlurper

class LannisterFolks {
    def getLannisters() {
        File LannisterPersonJsonFile = new File("../../../resources/org/contoso/LannisterFolks.json")
        def jsonSlurper = new JsonSlurper()
        def lannisterPersonJsonText = LannisterPersonJsonFile.getText()

        def lannisterPersonJsonData = jsonSlurper.parseText(lannisterPersonJsonText)
        assert lannisterPersonJsonData instanceof ArrayList
        lannisterPersonJsonData.each { println it }

        def lannisterPersonList = lannisterPersonJsonData.username
        assert lannisterPersonList instanceof ArrayList
        return lannisterPersonList.join(',')
    }
}
