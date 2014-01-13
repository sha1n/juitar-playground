package org.juitar.flags.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class XmlFlagsTest {

    public static void main(String... args) {

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(FlagsXmlBean.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            FlagsXmlBean flags = (FlagsXmlBean) unmarshaller.unmarshal(XmlFlagsTest.class.getResourceAsStream("/flags.xml"));


            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(flags, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
