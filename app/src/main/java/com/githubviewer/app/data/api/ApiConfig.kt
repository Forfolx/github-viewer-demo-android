package com.githubviewer.app.data.api

import javax.inject.Inject

class ApiConfig @Inject constructor() : ApiEndpointHolder {

    override fun getApiEndpoint(): String = SERVER_URL

    companion object {
        const val SERVER_URL = "https://api.github.com"
    }
}