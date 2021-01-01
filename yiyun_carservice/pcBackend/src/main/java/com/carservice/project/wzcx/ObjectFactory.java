
package com.carservice.project.wzcx;


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the wzcx package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WriteObjectOutResponseReturn_QNAME = new QName("http://webServices.service.dahong.com", "return");
    private final static QName _QueryObjectOutJkid_QNAME = new QName("http://webServices.service.dahong.com", "jkid");
    private final static QName _QueryObjectOutXtlb_QNAME = new QName("http://webServices.service.dahong.com", "xtlb");
    private final static QName _QueryObjectOutJkxlh_QNAME = new QName("http://webServices.service.dahong.com", "jkxlh");
    private final static QName _QueryObjectOutQueryXmlDoc_QNAME = new QName("http://webServices.service.dahong.com", "QueryXmlDoc");
    private final static QName _WriteObjectOutWriteXmlDoc_QNAME = new QName("http://webServices.service.dahong.com", "WriteXmlDoc");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: wzcx
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WriteObjectOut }
     *
     */
    public WriteObjectOut createWriteObjectOut() {
        return new WriteObjectOut();
    }

    /**
     * Create an instance of {@link QueryObjectOut }
     *
     */
    public QueryObjectOut createQueryObjectOut() {
        return new QueryObjectOut();
    }

    /**
     * Create an instance of {@link QueryObjectOutResponse }
     *
     */
    public QueryObjectOutResponse createQueryObjectOutResponse() {
        return new QueryObjectOutResponse();
    }

    /**
     * Create an instance of {@link WriteObjectOutResponse }
     *
     */
    public WriteObjectOutResponse createWriteObjectOutResponse() {
        return new WriteObjectOutResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "return", scope = WriteObjectOutResponse.class)
    public JAXBElement<String> createWriteObjectOutResponseReturn(String value) {
        return new JAXBElement<String>(_WriteObjectOutResponseReturn_QNAME, String.class, WriteObjectOutResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "jkid", scope = QueryObjectOut.class)
    public JAXBElement<String> createQueryObjectOutJkid(String value) {
        return new JAXBElement<String>(_QueryObjectOutJkid_QNAME, String.class, QueryObjectOut.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "xtlb", scope = QueryObjectOut.class)
    public JAXBElement<String> createQueryObjectOutXtlb(String value) {
        return new JAXBElement<String>(_QueryObjectOutXtlb_QNAME, String.class, QueryObjectOut.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "jkxlh", scope = QueryObjectOut.class)
    public JAXBElement<String> createQueryObjectOutJkxlh(String value) {
        return new JAXBElement<String>(_QueryObjectOutJkxlh_QNAME, String.class, QueryObjectOut.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "QueryXmlDoc", scope = QueryObjectOut.class)
    public JAXBElement<String> createQueryObjectOutQueryXmlDoc(String value) {
        return new JAXBElement<String>(_QueryObjectOutQueryXmlDoc_QNAME, String.class, QueryObjectOut.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "jkid", scope = WriteObjectOut.class)
    public JAXBElement<String> createWriteObjectOutJkid(String value) {
        return new JAXBElement<String>(_QueryObjectOutJkid_QNAME, String.class, WriteObjectOut.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "xtlb", scope = WriteObjectOut.class)
    public JAXBElement<String> createWriteObjectOutXtlb(String value) {
        return new JAXBElement<String>(_QueryObjectOutXtlb_QNAME, String.class, WriteObjectOut.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "jkxlh", scope = WriteObjectOut.class)
    public JAXBElement<String> createWriteObjectOutJkxlh(String value) {
        return new JAXBElement<String>(_QueryObjectOutJkxlh_QNAME, String.class, WriteObjectOut.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "WriteXmlDoc", scope = WriteObjectOut.class)
    public JAXBElement<String> createWriteObjectOutWriteXmlDoc(String value) {
        return new JAXBElement<String>(_WriteObjectOutWriteXmlDoc_QNAME, String.class, WriteObjectOut.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.service.dahong.com", name = "return", scope = QueryObjectOutResponse.class)
    public JAXBElement<String> createQueryObjectOutResponseReturn(String value) {
        return new JAXBElement<String>(_WriteObjectOutResponseReturn_QNAME, String.class, QueryObjectOutResponse.class, value);
    }

}
