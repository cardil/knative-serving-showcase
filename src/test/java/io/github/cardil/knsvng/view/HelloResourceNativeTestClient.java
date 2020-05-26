package io.github.cardil.knsvng.view;

import org.eclipse.microprofile.opentracing.Traced;

@Traced(false)
interface HelloResourceNativeTestClient extends HelloResourceTestClient {
}
