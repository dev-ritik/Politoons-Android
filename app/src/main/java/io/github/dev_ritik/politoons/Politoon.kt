package io.github.dev_ritik.politoons


class Politoon {

    var name: String? = null
    var majorRole: String? = null
    var party: String? = null

    constructor() {}

    constructor(
        name: String,
        majorRole: String
    ) {
        this.name = name
        this.majorRole = majorRole
    }

    constructor(name: String?, majorRole: String?, party: String?) {
        this.name = name
        this.majorRole = majorRole
        this.party = party
    }



}