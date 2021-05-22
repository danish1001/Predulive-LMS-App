package com.dr.predulive.dashboardActivities.Recycler

class IndividualData {
    private var name: String
    private var email: String
    private var about: String
    private var imageUrl: String

    constructor(name: String, email: String, about: String, imageUrl: String) {
        this.name = name
        this.email = email
        this.about = about
        this.imageUrl = imageUrl
    }


    public fun getName(): String {
        return this.name
    }
    public fun getEmail(): String {
        return this.email
    }
    public fun getAbout(): String {
        return this.about
    }
    public fun getImageUrl(): String {
        return this.imageUrl
    }

}