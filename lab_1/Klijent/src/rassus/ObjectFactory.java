
package rassus;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rassus package. 
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

    private final static QName _SearchNeighbourResponse_QNAME = new QName("http://example/", "searchNeighbourResponse");
    private final static QName _RegisterResponse_QNAME = new QName("http://example/", "registerResponse");
    private final static QName _SearchNeighbour_QNAME = new QName("http://example/", "searchNeighbour");
    private final static QName _StoreMeasurementResponse_QNAME = new QName("http://example/", "storeMeasurementResponse");
    private final static QName _StoreMeasurement_QNAME = new QName("http://example/", "storeMeasurement");
    private final static QName _Register_QNAME = new QName("http://example/", "register");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rassus
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StoreMeasurementResponse }
     * 
     */
    public StoreMeasurementResponse createStoreMeasurementResponse() {
        return new StoreMeasurementResponse();
    }

    /**
     * Create an instance of {@link StoreMeasurement }
     * 
     */
    public StoreMeasurement createStoreMeasurement() {
        return new StoreMeasurement();
    }

    /**
     * Create an instance of {@link SearchNeighbourResponse }
     * 
     */
    public SearchNeighbourResponse createSearchNeighbourResponse() {
        return new SearchNeighbourResponse();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link SearchNeighbour }
     * 
     */
    public SearchNeighbour createSearchNeighbour() {
        return new SearchNeighbour();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchNeighbourResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "searchNeighbourResponse")
    public JAXBElement<SearchNeighbourResponse> createSearchNeighbourResponse(SearchNeighbourResponse value) {
        return new JAXBElement<SearchNeighbourResponse>(_SearchNeighbourResponse_QNAME, SearchNeighbourResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchNeighbour }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "searchNeighbour")
    public JAXBElement<SearchNeighbour> createSearchNeighbour(SearchNeighbour value) {
        return new JAXBElement<SearchNeighbour>(_SearchNeighbour_QNAME, SearchNeighbour.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreMeasurementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "storeMeasurementResponse")
    public JAXBElement<StoreMeasurementResponse> createStoreMeasurementResponse(StoreMeasurementResponse value) {
        return new JAXBElement<StoreMeasurementResponse>(_StoreMeasurementResponse_QNAME, StoreMeasurementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreMeasurement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "storeMeasurement")
    public JAXBElement<StoreMeasurement> createStoreMeasurement(StoreMeasurement value) {
        return new JAXBElement<StoreMeasurement>(_StoreMeasurement_QNAME, StoreMeasurement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

}
