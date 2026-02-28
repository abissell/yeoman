package com.abissell.yeoman.functions;

import static org.junit.jupiter.api.Assertions.*;

import com.abissell.yeoman.functions.util.Result;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

final class ImagesTest {

    private static Path testResource(String name) throws URISyntaxException {
        var url = ImagesTest.class.getClassLoader().getResource(name);
        assertNotNull(url, "Test resource not found: " + name);
        return Path.of(url.toURI());
    }

    @Test
    void readsImageTsFromJpegWithExif() throws Exception {
        var path = testResource("exif-with-ts.jpg");
        var result = Images.created(path);
        assertInstanceOf(Result.Ok.class, result);
        var ok = (Result.Ok<ImageTs>) result;
        assertInstanceOf(ImageTs.Unzoned.class, ok.value());
        var unzoned = (ImageTs.Unzoned) ok.value();
        assertEquals(LocalDateTime.of(2024, 1, 15, 14, 30, 0), unzoned.dateTime());
    }

    @Test
    void readsTsWithTimezoneOffset() throws Exception {
        var path = testResource("exif-with-tz.jpg");
        var result = Images.created(path);
        assertInstanceOf(Result.Ok.class, result);
        var ok = (Result.Ok<ImageTs>) result;
        assertInstanceOf(ImageTs.Utc.class, ok.value());
        var utc = (ImageTs.Utc) ok.value();
        assertEquals(
                ZonedDateTime.of(LocalDateTime.of(2024, 1, 15, 9, 0, 0), ZoneOffset.UTC),
                utc.dateTime());
    }

    @Test
    void returnsErrForJpegWithoutExif() throws Exception {
        var path = testResource("no-exif.jpg");
        var result = Images.created(path);
        assertInstanceOf(Result.Err.class, result);
    }

    @Test
    void returnsErrForNonexistentFile() {
        var path = Path.of("/nonexistent/photo.jpg");
        var result = Images.created(path);
        assertInstanceOf(Result.Err.class, result);
    }

    @Test
    void readsImageTsFromTiffWithExif() throws Exception {
        var path = testResource("exif-with-ts.tif");
        var result = Images.created(path);
        assertInstanceOf(Result.Ok.class, result);
        var ok = (Result.Ok<ImageTs>) result;
        assertInstanceOf(ImageTs.Unzoned.class, ok.value());
        var unzoned = (ImageTs.Unzoned) ok.value();
        assertEquals(LocalDateTime.of(2024, 1, 15, 14, 30, 0), unzoned.dateTime());
    }

    @Test
    void readsImageTsFromPngWithExif() throws Exception {
        var path = testResource("exif-with-ts.png");
        var result = Images.created(path);
        assertInstanceOf(Result.Ok.class, result);
        var ok = (Result.Ok<ImageTs>) result;
        assertInstanceOf(ImageTs.Unzoned.class, ok.value());
        var unzoned = (ImageTs.Unzoned) ok.value();
        assertEquals(LocalDateTime.of(2024, 1, 15, 14, 30, 0), unzoned.dateTime());
    }

    @Test
    void utcRejectsNonUtcZone() {
        var nonUtc =
                ZonedDateTime.of(LocalDateTime.of(2024, 1, 15, 14, 30, 0), ZoneId.of("US/Eastern"));
        assertThrows(IllegalArgumentException.class, () -> new ImageTs.Utc(nonUtc));
    }
}
