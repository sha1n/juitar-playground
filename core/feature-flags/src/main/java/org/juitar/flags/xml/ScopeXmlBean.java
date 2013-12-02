package org.juitar.flags.xml;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author sha1n
 * Date: 12/2/13
 */
@XmlType(name = "scope")
public class ScopeXmlBean {

    @XmlValue
    String name;
}
