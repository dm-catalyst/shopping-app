package pl.catalyst.utils.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import pl.catalyst.utils.converter.exception.JsonConverterException;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

public class JsonConverter<T> {

    @Value("filename")
    private String filename;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter() {
    }

    public void toJson(final T element) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            gson.toJson(element, fileWriter);
        } catch (Exception e) {
            throw new JsonConverterException(e.getMessage());
        }
    }

    public Optional<T> fromJson() {
        try (FileReader fileReader = new FileReader(filename)) {
            return Optional.of(gson.fromJson(fileReader, type));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonConverterException(e.getMessage());
        }
    }

    public Optional<T> fromJson(String filename) {
        try (FileReader fileReader = new FileReader(getFilePath(filename))) {
            return Optional.of(gson.fromJson(fileReader, type));
        } catch (Exception e) {
            throw new JsonConverterException(e.getMessage());
        }
    }

    private String getFilePath(String file) {
        ClassLoader classLoader = getClass().getClassLoader();
        return Objects.requireNonNull(classLoader.getResource("customer/" + file)).getPath();
    }
}
