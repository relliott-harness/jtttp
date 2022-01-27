package io.harness.jhttp.server;

import java.nio.file.Paths;

import org.junit.Test;

import java.util.concurrent;

import static org.junit.Assert.*;

public class PathResolverTest {

    @Test
    public void testPathResolver() {
        final PathResolver resolver = new PathResolver(Paths.get("/server/root"));
        assertEquals("/server/root", resolver.resolveFile("/").toString());
        assertEquals("/server/root", resolver.resolveFile("").toString());
        assertEquals("/server/root/path", resolver.resolveFile("/path").toString());
        TimeUnit.SECONDS.sleep(1);
    }
}
