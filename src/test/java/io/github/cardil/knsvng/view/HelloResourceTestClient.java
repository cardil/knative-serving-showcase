package io.github.cardil.knsvng.view;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = Constants.DEFAULT_TEST_URL)
interface HelloResourceTestClient extends HelloResource {
}
