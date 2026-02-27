import org.jspecify.annotations.NullMarked;

@NullMarked
module com.abissell.yeoman.functions {
    requires static org.jspecify;
    requires metadata.extractor;

    exports com.abissell.yeoman.functions;
    exports com.abissell.yeoman.functions.util;
}
