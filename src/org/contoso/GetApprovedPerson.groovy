package org.contoso

class ApprovedSubmitters {
    // Pipeline: Input Step
    // https://jenkins.io/doc/pipeline/steps/pipeline-input-step/
    // User IDs and/or external group names of person or people permitted to respond to the input, separated by ','.
    // If you configure "alice, bob", will match with "alice" but not with "bob". You need to remove all the white spaces.
    def static getJenkinsInputApprovedUsernames() {
        def approvedUsernames = approvedSubmittersList.username
        assert approvedUsernames instanceof ArrayList

        return approvedUsernames.join(',').toLowerCase()
    }

    def static getApprovedEmails() {
        def approvedEmails = approvedSubmittersList.email
        assert approvedEmails instanceof ArrayList

        return approvedEmails.join('\n').toLowerCase()
    }

    def static approvedSubmittersList = [
            [
                    username : "olenna_tyrell",
                    firstName: "Olenna",
                    lastName : "Tyrell",
                    email    : "ollenna@tyrell.local"
            ],
            [
                    username : "loras_tyrell",
                    firstName: "Loras",
                    lastName : "Tyrell",
                    email    : "loras@tyrell.local"
            ],
            [
                    username : "margaery_tyrell",
                    firstName: "Margaery",
                    lastName : "Tyrell",
                    email    : "margaery@tyrell.local"
            ]
    ]
}