package ru.otus.sua.client.ui;

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.*;


public class CurrencyFeeder {

    /* EXAMPLE xml
    <ValCurs Date="27.10.2018" name="Foreign Currency Market">
        <Valute ID="R01010">
            <NumCode>036</NumCode>
            <CharCode>AUD</CharCode>
            <Nominal>1</Nominal>
            <Name>Австралийский доллар</Name>
            <Value>46,1345</Value>
        </Valute>
    </ValCurs>
     */

    private final MainResources res;
    private final VerticalPanel currencyPanel;

    public CurrencyFeeder(VerticalPanel currencyPanel, MainResources res) {
        this.res = res;
        this.currencyPanel = currencyPanel;
    }

    public void FillCurrencyPanel() {
        retrieve("https://www.cbr-xml-daily.ru/daily_utf8.xml");
    }

    private void deferredFillCurrencyPanel(String xml) {
        Document dom = XMLParser.parse(xml);
        Valute eur = getValute(dom, "R01239");
        Valute usd = getValute(dom, "R01235");
        Valute cny = getValute(dom, "R01375");
        currencyPanel.add(getHorizontalPanel(eur));
        currencyPanel.add(getHorizontalPanel(usd));
        currencyPanel.add(getHorizontalPanel(cny));
    }

    private IsWidget getHorizontalPanel(Valute v) {
        HorizontalPanel hp = new HorizontalPanel();
        Label label1 = new Label("За ");
        Label label2 = new Label(v.getNominal());
        Label label3 = new Label(v.getCharCode());
        Label label4 = new Label(" дают ");
        Label label5 = new Label(v.getValue());

        label1.setStyleName(res.style().marpadding());
        label2.setStyleName(res.style().marpadding());
        label3.setStyleName(res.style().marpadding());
        label4.setStyleName(res.style().marpadding());
        label5.setStyleName(res.style().marpadding());

        hp.add(label1);
        hp.add(label2);
        hp.add(label3);
        hp.add(label4);
        hp.add(label5);

        return hp;
    }

    private Node getNodeByValuteId(Document dom, String valuteId) {
        Node result = null;
        NodeList nodes = dom.getElementsByTagName("Valute");
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).hasAttributes()) {
                NamedNodeMap map = nodes.item(i).getAttributes();
                for (int j = 0; j < map.getLength(); j++) {
                    String id = map.item(j).toString();
                    if (id.equals(valuteId)) {
                        result = nodes.item(i);
                        break;
                    }
                }
            }
        }
        return result;
    }

    private Valute getValute(Document dom, String valuteId) {
        Valute valute = new Valute();
        Node node = getNodeByValuteId(dom, valuteId);
        if (node != null) {
            valute.setValuteId(valuteId);
            valute.setCharCode(getNodeValueByTagName(node, "CharCode"));
            valute.setName(getNodeValueByTagName(node, "Name"));
            valute.setNominal(getNodeValueByTagName(node, "Nominal"));
            String value = getNodeValueByTagName(node, "Value");
            valute.setValue(value.substring(0, value.length() - 2));
            valute.setNumCode(getNodeValueByTagName(node, "NumCode"));
        }
        return valute;
    }

    private String getNodeValueByTagName(Node node, String tag) {
        Node n = ((Element) node).getElementsByTagName(tag).item(0).getFirstChild();
        Text t = (Text) n;
        return t.getData();
    }

    private void retrieve(String url) {

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
        try {
            builder.sendRequest(null, new RequestCallback() {

                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Ошибка: " + exception.getMessage());
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        String result = response.getText();
                        deferredFillCurrencyPanel(result);
                    } else {
                        Window.alert("Ошибка: " + response.getStatusText());
                    }
                }
            });
        } catch (RequestException e) {
            Window.alert("Ошибка: нет соединения с сервером.");
        }
    }


}

class Valute {
    private String valuteId;
    private String charCode;
    private String value;
    private String name;
    private String numCode;
    private String nominal;

    public Valute() {
    }

    public Valute(String valuteId, String charCode, String value, String name, String numCode, String nominal) {
        this.valuteId = valuteId;
        this.charCode = charCode;
        this.value = value;
        this.name = name;
        this.numCode = numCode;
        this.nominal = nominal;
    }

    public String getValuteId() {
        return valuteId;
    }

    public void setValuteId(String valuteId) {
        this.valuteId = valuteId;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }
}
