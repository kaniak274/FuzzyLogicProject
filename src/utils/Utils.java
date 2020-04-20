package utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Utils {
    static class StreamUtils {
        public List<Double> streamToList(DoubleStream stream) {
            return stream.boxed().collect(Collectors.toList());
        }
    }
}
