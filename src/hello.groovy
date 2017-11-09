#!/usr/bin/env groovy

import groovy.json.JsonSlurper

println "Hello from Groovy!"

Integer.metaClass.getDaysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, delegate)
    today.time
}

println(5.daysFromNow)

def xml = """
<response version-api="2.0">
    <value>
        <books>
            <book id="2">
                <title>Don Xijote</title>
                <author id="1">Manuel De Cervantes</author>
            </book>
        </books>
    </value>
</response>
"""

def parser = new XmlParser()
def response = parser.parseText(xml)
response.appendNode(
        { classifier(value: "jetty") }
)

println response.text()

def CAR_RECORDS = '''
   <records>
     <car name='HSV Maloo' make='Holden' year='2006'/>
     <car name='P50' make='Peel' year='1962'/>
     <car name='Royale' make='Bugatti' year='1931'/>
   </records>
 '''

def carRecords = new XmlParser().parseText(CAR_RECORDS)

def cars = carRecords.children()
def royale = cars.find { it.@name == 'Royale' }
cars.remove(royale)
cars.add(0, royale)
def newCar = new Node(carRecords, 'car', [name:'My New Car', make:'Peel', year:'1962'])

assert ["Royale", "HSV Maloo", "P50", "My New Car"] == carRecords.car*.@name

new XmlNodePrinter().print(carRecords)

def newClassifier = new Node(carRecords, 'classifier', [value: 'jetty'])

new XmlNodePrinter().print(carRecords)

// Convert it to a Map containing a List of Maps
def jsonObject = [ root: carRecords.node.collect {
    [ node: it.text() ]
} ]

import static groovy.json.JsonOutput.prettyPrint

// And dump it as Json
def json = new groovy.json.JsonBuilder( jsonObject )

println prettyPrint(json.toString())

static def getCoords() {
    def mavenCoordsFromRecentCommit = """
{
  "groupId": "org.contoso.targaryen",
  "artifactId": "daenerys",
  "version": "2017.11.07T16.16.16.444-333-1eff3b88aa953491ed8fcd477b98f3d97e7382ef",
  "artifactHits": [
    {
      "repositoryId": "releases",
      "artifactLinks": [
        {
          "extension": "pom"
        },
        {
          "extension": "jar"
        },
        {
          "classifier": "javadoc",
          "extension": "jar"
        },
        {
          "classifier": "jetty",
          "extension": "jar"
        },
        {
          "classifier": "klassifier",
          "extension": "backaging"
        }
      ]
    }
  ]
}
"""

    println "[Dbg,Targaryen,C]:\n${mavenCoordsFromRecentCommit}"

    println "[Dbg,Targaryen,C]:\n${mavenCoordsFromRecentCommit}"

    JsonSlurper jsonSlurper = new JsonSlurper()
    def parsedMavenCoords = jsonSlurper.parseText(mavenCoordsFromRecentCommit)

    println "[Dbg,Targaryen,D]:\n${parsedMavenCoords}"

    def listArtifactsLinks = parsedMavenCoords.artifactHits.artifactLinks.flatten()
    println "[Dbg,Targaryen,R]:\n${listArtifactsLinks}"

    // Option: 1: Get whatever key,value appears first in any submap regardless other (needed?) key exists.
    //
    // Remove null items using `- null` notation. https://stackoverflow.com/a/5270663
    def listClassifier = listArtifactsLinks.classifier.unique() - null
    println "[Dbg,Targaryen,T]:\n${listClassifier}"
    println "[Dbg,Targaryen,U]:\n${listClassifier.first()}"

    // Remove null items using `- null` notation. https://stackoverflow.com/a/5270663
    def listExtension = listArtifactsLinks.extension.unique() - null
    println "[Dbg,Targaryen,K]:\n${listExtension}"

    listExtension.removeIf { it == "pom" }  // Blacklist pom extension from packaging/extension.
    println "[Dbg,Targaryen,L]:\n${listExtension}"
    println "[Dbg,Targaryen,U]:\n${listClassifier.first()}"

    // Option 2: Get key,value only if given set of keys exists in the same submap.
    //
    println "[Dbg,Targaryen,M]-----------\n"

    def getSubMap = { data ->
        data.findResults { it.subMap(["classifier", "extension"])}
    }
    println getSubMap(listArtifactsLinks)

    // Check map that fits given set of keys.
    println listArtifactsLinks.collect{item ->
        item.containsKey('classifier') && item.containsKey('extension')
    }

    // Get map that fits given set of keys and filtered values.
    def filteredKeysArtifactsLinks = listArtifactsLinks.findAll {
        it.containsKey('classifier') && it.containsKey('extension') && it["classifier"] != "javadoc"
    }.collect { it }

    println filteredKeysArtifactsLinks
    println filteredKeysArtifactsLinks['classifier'].first()
    println filteredKeysArtifactsLinks['extension'].first()

    println "[Dbg,Targaryen,M]-----------\n"

    def mvnData = [
            groupId    : parsedMavenCoords.groupId,
            artifactId : parsedMavenCoords.artifactId,
            version    : parsedMavenCoords.version,
            classifier : filteredKeysArtifactsLinks['classifier'].first(),
            packaging  : filteredKeysArtifactsLinks['extension'].first()
    ]

    println "[Dbg,Targaryen,W]:\n${mvnData}"

    return mvnData
}

println "[Dbg,Targaryen,Z]:\n${getCoords()}"