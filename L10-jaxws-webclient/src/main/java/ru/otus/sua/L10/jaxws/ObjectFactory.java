
package ru.otus.sua.L10.jaxws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.otus.sua.L10.jaxws package. 
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

    private final static QName _TaxData_QNAME = new QName("http://L10.sua.otus.ru/", "taxData");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.otus.sua.L10.jaxws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TaxData }
     * 
     */
    public TaxData createTaxData() {
        return new TaxData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaxData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://L10.sua.otus.ru/", name = "taxData")
    public JAXBElement<TaxData> createTaxData(TaxData value) {
        return new JAXBElement<TaxData>(_TaxData_QNAME, TaxData.class, null, value);
    }

}
