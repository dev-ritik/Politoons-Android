package io.github.dev_ritik.politoons.model

import com.google.gson.annotations.SerializedName


class Politoon {

    @SerializedName("name")
    var name: String? = null
    @SerializedName("majorRole")
    var majorRole: String? = null
    @SerializedName("party")
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