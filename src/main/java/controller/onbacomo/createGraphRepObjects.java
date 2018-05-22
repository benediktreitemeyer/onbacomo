package controller.onbacomo;

import model.onbacomo.classes.BpmnClass;
import model.onbacomo.relations.BpmnRelation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class createGraphRepObjects {
    private BpmnClass[] classList;
    private BpmnRelation[] relationList;

    public void createObjects(File fXMLFile) {

        try {
            NodeList nList = getNodeList(fXMLFile);
            classList = new BpmnClass[nList.getLength()];
            relationList = new BpmnRelation[nList.getLength()];

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                BpmnClass bpmnClass = new BpmnClass();
                BpmnRelation bpmnRelation = new BpmnRelation();

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    String[] segs = element.getTextContent().split(Pattern.quote(";"));
                    Node nodeParent = element.getParentNode();
                    Element elementParent = (Element) nodeParent;

                    if (nodeParent.getNodeName().equals("owl:Class")) {
                        //TODO: Weiter auf Rectangle oder Circle prüfen ?
                        String[] getName = elementParent.getAttribute("rdf:about").split(Pattern.quote("#"));

                        for (String seg : segs) {
                            bpmnClass.setName(getName[1]);
                            String[] getValue = seg.split(Pattern.quote(":"));
                            switch (getValue[0]) {
                                case "Shape":
                                    bpmnClass.setShape(getValue[1]);
                                    break;
                                case "Color":
                                    bpmnClass.setColor(getValue[1]);
                                    break;
                                case "Stroke":
                                    bpmnClass.setStrokeWidth(Double.parseDouble(getValue[1]));
                                    break;
                            }
                        }
                    }

                    if (nodeParent.getNodeName().equals("owl:ObjectProperty")) {
                        //TODO: Wird untere Zeile noch benötigt ?
                        String[] name = elementParent.getAttribute("rdf:about").split(Pattern.quote("#"));
                        for (String seg : segs) {
                            String[] getValue = seg.split(Pattern.quote(":"));
                            switch (getValue[0]){
                                case "Type":
                                    bpmnRelation.setType(getValue[1]);
                                    break;
                                case "Direction":
                                    bpmnRelation.setDirection(getValue[1]);
                                    break;
                                case "StartClass":
                                    bpmnRelation.setStartClass(getValue[1]);
                                    break;
                                case "EndClass":
                                    bpmnRelation.setEndClass(getValue[1]);
                                    break;
                            }
                        }
                    }
                }
                classList[temp] = bpmnClass;
                relationList[temp] = bpmnRelation;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NodeList getNodeList(File fXMLFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXMLFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName("onbacomo:graphrep");
    }

    public BpmnClass[] getClassList() {
        return classList;
    }
    public BpmnRelation[] getRelationList() {
        return relationList;
    }

}

