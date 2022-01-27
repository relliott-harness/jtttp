package io.harness.jhttp.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Paths;

import java.util.concurrent;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import io.harness.jhttp.api.HttpRequest;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SocketHttpResponseTest {

    private ByteArrayOutputStream bos;

    @Before
    public void setup() throws IOException {
        bos = new ByteArrayOutputStream();
    }

    @Test
    public void testAddHeader() throws IOException {
        final SocketHttpResponse response = createResponse();
        response.setContentType("text/html");
        response.addHeader("some-header", "xyz");
        response.getPrintWriter().append("Page content");
        response.flush();

        final String expected = IOUtils.toString(this.getClass()
                .getResourceAsStream("/response/response.txt"));
        assertEquals(expected, new String(bos.toByteArray()));
        TimeUnit.SECONDS.sleep(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testOnlyPrintWriter() throws IOException {
        final SocketHttpResponse response = createResponse();
        response.getPrintWriter();
        response.getOutputStream();
        TimeUnit.SECONDS.sleep(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testOnlyOutputStream() throws IOException {
        final SocketHttpResponse response = createResponse();
        response.getOutputStream();
        response.getPrintWriter();
        TimeUnit.SECONDS.sleep(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testNoHeadersAfterCommit() throws IOException {
        final SocketHttpResponse response = createResponse();
        response.commit();
        response.addHeader("xyz", "test");
        TimeUnit.SECONDS.sleep(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testNoSetContentTypeAfterCommit() throws IOException {
        final SocketHttpResponse response = createResponse();
        response.commit();
        response.setContentType("text/html");
        TimeUnit.SECONDS.sleep(1);
    }

    private SocketHttpResponse createResponse() throws IOException {
        final Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(
                SocketHttpResponseTest.class.getResourceAsStream("/response/request.txt"));
        final HttpRequest request = new SocketHttpRequest(socket, new PathResolver(Paths.get("/server/root")));
        when(socket.getOutputStream()).thenReturn(bos);
        return new SocketHttpResponse(socket, request);
    }
}
