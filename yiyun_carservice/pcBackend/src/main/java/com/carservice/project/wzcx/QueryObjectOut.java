
package com.carservice.project.wzcx;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xtlb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jkxlh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jkid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="QueryXmlDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "xtlb",
        "jkxlh",
        "jkid",
        "queryXmlDoc"
})
@XmlRootElement(name = "queryObjectOut")
public class QueryObjectOut {

    @XmlElementRef(name = "xtlb", namespace = "http://webServices.service.dahong.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> xtlb;
    @XmlElementRef(name = "jkxlh", namespace = "http://webServices.service.dahong.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jkxlh;
    @XmlElementRef(name = "jkid", namespace = "http://webServices.service.dahong.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jkid;
    @XmlElementRef(name = "QueryXmlDoc", namespace = "http://webServices.service.dahong.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> queryXmlDoc;

    /**
     * 获取xtlb属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getXtlb() {
        return xtlb;
    }

    /**
     * 设置xtlb属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setXtlb(JAXBElement<String> value) {
        this.xtlb = value;
    }

    /**
     * 获取jkxlh属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getJkxlh() {
        return jkxlh;
    }

    /**
     * 设置jkxlh属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setJkxlh(JAXBElement<String> value) {
        this.jkxlh = value;
    }

    /**
     * 获取jkid属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getJkid() {
        return jkid;
    }

    /**
     * 设置jkid属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setJkid(JAXBElement<String> value) {
        this.jkid = value;
    }

    /**
     * 获取queryXmlDoc属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getQueryXmlDoc() {
        return queryXmlDoc;
    }

    /**
     * 设置queryXmlDoc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setQueryXmlDoc(JAXBElement<String> value) {
        this.queryXmlDoc = value;
    }

}
