package examples;

import com.google.gson.JsonObject;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

    public static void main(String[] params) throws JAXBException {
        JexlEngine engine = new JexlEngine();
        Expression expression = engine.createExpression("a+b");
        MapContext context = new MapContext();
        context.set("a", 1);
        context.set("b", 2);
        System.out.println(expression.evaluate(context));
        String bodyJson = requestBody();
        String userDefinedXml = xml();

    }

    private static String xml() throws JAXBException {
        Metadata metadata = new Metadata(Arrays.asList(new Attribute("A", "json['a']"),new Attribute("B","json['b']")));
        StringWriter writer = new StringWriter();
        JAXBContext.newInstance(Metadata.class, Attribute.class).createMarshaller().marshal(metadata, writer);
        return writer.toString();
    }

    @XmlRootElement
    private static class Metadata {
        private List<Attribute> attributes = new ArrayList<>();

        public Metadata() {}
        public Metadata(List<Attribute> attributes) {
            this.attributes = Collections.unmodifiableList(attributes);
        }

        public void setAttributes(List<Attribute> attributes) {
            this.attributes = attributes;
        }

        public List<Attribute> getAttributes() {
            return attributes;
        }
    }

    @XmlRootElement
    public static class Attribute {

        public Attribute() {}
        public Attribute(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private String name;
        private String value;
    }

    private static String requestBody() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("A", "AAAA");
        jsonObject.addProperty("B", "BBBB");
        return jsonObject.getAsString();
    }


}
