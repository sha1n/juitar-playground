package org.juitar.flags.xml;

import org.juitar.flags.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class Loader {

    public static void loadFlags(InputStream inputStream) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(FlagsXmlBean.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            FlagsXmlBean flags = (FlagsXmlBean) unmarshaller.unmarshal(inputStream);

            registerFlags(flags);

            printDebug(jaxbContext, flags);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void registerFlags(FlagsXmlBean flags) {
        for (FlagXmlBean flagXmlBean : flags.flags) {
            Flag flag;
            if (flagXmlBean.scopes != null) {
                ScopedFlag scopedFlag = new ScopedFlag(flagXmlBean.name, flagXmlBean.inclusive);
                flag = scopedFlag;
                for (ScopeXmlBean scopeXmlBean : flagXmlBean.scopes) {
                    Scope scope = new Scope(scopeXmlBean.name);
                    Scopes.register(scope);
                    scopedFlag.register(scope);
                }
            } else {
                flag = new SimpleFlag(flagXmlBean.name, true);
            }
            Flags.register(flag);
        }

    }

    private static void printDebug(JAXBContext jaxbContext, FlagsXmlBean flags) throws JAXBException {
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(flags, System.out);
    }
}
