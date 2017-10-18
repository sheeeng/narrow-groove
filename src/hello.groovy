#!/usr/bin/env groovy

println "Hello from Groovy!"

Integer.metaClass.getDaysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, delegate)
    today.time
}

println(5.daysFromNow)