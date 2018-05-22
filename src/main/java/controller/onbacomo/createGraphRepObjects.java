package controller.onbacomo;

import javafx.scene.paint.Color;
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
import java.util.LinkedList;
import java.util.regex.Pattern;

public class createGraphRepObjects {
    private LinkedList<BpmnClass> classList;
    private LinkedList<BpmnRelation> relationList;

    public void createObjects(File fXMLFile) {
        try {
            NodeList nList = getNodeList(fXMLFile);
            classList = new LinkedList<>();
            relationList = new LinkedList<>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    String[] segs = element.getTextContent().split(Pattern.quote(";"));
                    Node nodeParent = element.getParentNode();
                    Element elementParent = (Element) nodeParent;

                    if (nodeParent.getNodeName().equals("owl:Class")) {
                        String[] name = elementParent.getAttribute("rdf:about").split(Pattern.quote("#"));
                        BpmnClass bpmnClass = new BpmnClass();
                        bpmnClass.setName(name[1]);

                        for (String seg : segs) {
                            String[] getValue = seg.split(Pattern.quote(":"));
                            switch (getValue[0]) {
                                case "Shape":
                                    bpmnClass.setId(getValue[1]);
                                    break;
                                case "Color":
                                    if (getValue[1].equals("ALICEBLUE")) {
                                        bpmnClass.setFill(Color.ALICEBLUE);

                                    }else
                                        if (getValue[1].equals("YELLOW")) {
                                            bpmnClass.setFill(Color.YELLOW);
                                        }
                                    break;
                                case "Stroke":
                                    bpmnClass.setStrokeWidth(Double.parseDouble(getValue[1]));
                                    break;
                            }
                        }
                        classList.add(bpmnClass);
                    }

                    if (nodeParent.getNodeName().equals("owl:ObjectProperty")) {
                        String[] name = elementParent.getAttribute("rdf:about").split(Pattern.quote("#"));
                        BpmnRelation bpmnRelation = new BpmnRelation();
                        bpmnRelation.setName(name[1]);
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
                        relationList.add(bpmnRelation);
                    }
                }
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

    public LinkedList<BpmnClass> getClassList() {
        return classList;
    }
    public LinkedList<BpmnRelation> getRelationList() {
        return relationList;
    }

}

