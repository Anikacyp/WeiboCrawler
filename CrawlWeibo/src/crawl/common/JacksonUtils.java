package crawl.common;
/**
 */

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 */
public class JacksonUtils {

    final static ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private static class NullSerializer extends JsonSerializer<Object> {

        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString("");
        }
    }
    /**
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJsonStr(String jsonStr){
		try {
			getObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			getObjectMapper().configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			return getObjectMapper().readValue(jsonStr, Map.class);
		} catch (Exception e) {
			System.out.println("Error string:"+jsonStr);
			return null;
			//throw new RuntimeException("Can not getMapFromJsonStr which jsonStr:"+jsonStr,e);
		}
	}
	/**
	 */
	public static String getJsonFromMap(Map<String, ?> map){
		try {
			return getObjectMapper().writeValueAsString(map);
		} catch (Exception e) {
			throw new RuntimeException("Can not getJsonFromMap which map:"+map,e);
		}
	}
	
	/**
	 * @param content
	 * @param valueType
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T toBean(String content, Class<T> valueType)
			throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(content, valueType);
	}
	
	/**
	 * @param object
	 * @return
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String toJson(Object object) throws IOException {
		StringWriter writer = new StringWriter();
		try {
			objectMapper.writeValue(writer, object);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
		return null;
	}
}
