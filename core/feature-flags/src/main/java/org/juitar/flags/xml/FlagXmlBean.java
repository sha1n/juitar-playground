package org.juitar.flags.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * @author sha1n
 * Date: 12/2/13
 */
@XmlType(name = "flag")
public class FlagXmlBean {

    @XmlAttribute
    boolean inclusive = true;

    @XmlElement(name = "name")
    String name;

    @XmlElementWrapper(name = "scopes")
    @XmlElement(name = "scope", type = ScopeXmlBean.class)
    ScopeXmlBean[] scopes;
}
