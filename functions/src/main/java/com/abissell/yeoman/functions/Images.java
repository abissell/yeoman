package com.abissell.yeoman.functions;

import com.abissell.yeoman.functions.util.Result;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.jspecify.annotations.Nullable;

public enum Images {
    ;

    private static final DateTimeFormatter EXIF_FORMAT =
            DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");

    private record TagSource(
            Class<? extends ExifDirectoryBase> directoryClass, int dateTag, int offsetTag) {}

    private static final List<TagSource> TAG_PRIORITY =
            List.of(
                    new TagSource(
                            ExifSubIFDDirectory.class,
                            ExifDirectoryBase.TAG_DATETIME_ORIGINAL,
                            ExifDirectoryBase.TAG_TIME_ZONE_ORIGINAL),
                    new TagSource(
                            ExifSubIFDDirectory.class,
                            ExifDirectoryBase.TAG_DATETIME_DIGITIZED,
                            ExifDirectoryBase.TAG_TIME_ZONE_DIGITIZED),
                    new TagSource(
                            ExifIFD0Directory.class,
                            ExifDirectoryBase.TAG_DATETIME,
                            ExifDirectoryBase.TAG_TIME_ZONE));

    public static Result<ImageTs> created(Path file) {
        com.drew.metadata.Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(file.toFile());
        } catch (IOException | ImageProcessingException e) {
            return new Result.Err<>(e.toString());
        }

        for (TagSource tagSource : TAG_PRIORITY) {
            ExifDirectoryBase directory =
                    metadata.getFirstDirectoryOfType(tagSource.directoryClass());
            //noinspection ConstantValue
            if (directory == null) {
                continue;
            }

            String dateString = directory.getString(tagSource.dateTag());
            if (dateString == null) {
                continue;
            }

            String offsetString = directory.getString(tagSource.offsetTag());
            return new Result.Ok<>(parseImageTs(dateString, offsetString));
        }
        return new Result.Err<>("No created timestamp found in " + file);
    }

    private static ImageTs parseImageTs(String dateString, @Nullable String offsetString) {
        var localDateTime = LocalDateTime.parse(dateString.trim(), EXIF_FORMAT);
        if (offsetString != null && !offsetString.isEmpty()) {
            var offset = ZoneOffset.of(offsetString.trim());
            var utcDateTime =
                    localDateTime
                            .atOffset(offset)
                            .withOffsetSameInstant(ZoneOffset.UTC)
                            .toLocalDateTime();
            return new ImageTs.Utc(ZonedDateTime.of(utcDateTime, ZoneOffset.UTC));
        }
        return new ImageTs.Unzoned(localDateTime);
    }
}
