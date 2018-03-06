//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.08 at 02:20:13 PM CET 
//


package eu.europa.esig.dss.jaxb.simplecertificatereport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrustAnchor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrustAnchor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="trustServiceProvider" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="trustServiceProviderRegistrationId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="trustServiceName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrustAnchor", propOrder = {
    "countryCode",
    "trustServiceProvider",
    "trustServiceProviderRegistrationId",
    "trustServiceName"
})
public class XmlTrustAnchor {

    @XmlElement(required = true)
    protected String countryCode;
    @XmlElement(required = true)
    protected String trustServiceProvider;
    @XmlElement(required = true)
    protected String trustServiceProviderRegistrationId;
    @XmlElement(required = true)
    protected String trustServiceName;

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the trustServiceProvider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrustServiceProvider() {
        return trustServiceProvider;
    }

    /**
     * Sets the value of the trustServiceProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrustServiceProvider(String value) {
        this.trustServiceProvider = value;
    }

    /**
     * Gets the value of the trustServiceProviderRegistrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrustServiceProviderRegistrationId() {
        return trustServiceProviderRegistrationId;
    }

    /**
     * Sets the value of the trustServiceProviderRegistrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrustServiceProviderRegistrationId(String value) {
        this.trustServiceProviderRegistrationId = value;
    }

    /**
     * Gets the value of the trustServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrustServiceName() {
        return trustServiceName;
    }

    /**
     * Sets the value of the trustServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrustServiceName(String value) {
        this.trustServiceName = value;
    }

}