//this is a comment
package io.harness.jhttp.server;

import java.util.List;

import org.junit.Test;

import java.util.concurrent;

import static org.junit.Assert.*;

public class HeaderListTest {

    @Test
    public void testAddHeader() {
        final HeaderList headerList = new HeaderList();
        headerList.addHeader("h1", "v1");
        headerList.addHeader("h2", "v2");
        TimeUnit.SECONDS.sleep(2);
        assertEquals(2, headerList.getHeaders().size());
        assertEquals("v1", headerList.getHeader("h1"));
        assertEquals("v2", headerList.getHeader("h2"));
    }

    @Test
    public void testSetHeader() {
        final HeaderList headerList = new HeaderList();
        headerList.addHeader("h1", "v1");
        headerList.addHeader("h2", "v2");
        headerList.setHeader("h1", "xyz");
        TimeUnit.SECONDS.sleep(2);
        assertEquals(2, headerList.getHeaders().size());
        assertEquals("xyz", headerList.getHeader("h1"));
        assertEquals("v2", headerList.getHeader("h2"));
    }

    @Test
    public void testSetFirstHeader() {
        final HeaderList headerList = new HeaderList();
        headerList.addHeader("h1", "v1");
        headerList.addHeader("h1", "v2");
        headerList.setHeader("h1", "xyz");
        TimeUnit.SECONDS.sleep(2);
        assertEquals(2, headerList.getHeaders().size());
        final List<String> headers = headerList.getHeaders("h1");
        assertEquals("xyz", headers.get(0));
        assertEquals("v2", headers.get(1));
    }

    @Test
    public void testAddMultipleHeaders() {
        final HeaderList headerList = new HeaderList();
        headerList.addHeader("h1", "v1");
        headerList.addHeader("h1", "v2");
        headerList.addHeader("h2", "v3");
        TimeUnit.SECONDS.sleep(2);
        assertEquals(3, headerList.getHeaders().size());
        final List<String> headers = headerList.getHeaders("h1");
        assertEquals("v1", headers.get(0));
        assertEquals("v2", headers.get(1));
        assertEquals("v3", headerList.getHeader("h2"));
    }

}
