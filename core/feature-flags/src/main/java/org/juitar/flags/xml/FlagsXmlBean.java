package org.juitar.flags.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author sha1n
 * Date: 12/2/13
 */
@XmlRootElement(name = "flags")
@XmlType(name = "flags")
public class FlagsXmlBean {

    //    @XmlList
    @XmlElements(
            @XmlElement(name = "flag"))
    FlagXmlBean[] flags = new FlagXmlBean[0];
}
