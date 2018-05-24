package controller.onbacomo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.onbacomo.classes.BpmnCircle;
import model.onbacomo.classes.BpmnRectangle;
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
    private LinkedList<Shape> classList;
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

                        BpmnCircle bpmnCircle = new BpmnCircle();
                        BpmnRectangle bpmnRectangle = new BpmnRectangle();
                        boolean addCircle = true;

                        for (String seg : segs) {
                            String[] getValue = seg.split(Pattern.quote(":"));
                            switch (getValue[0]) {
                                case "Shape":
                                    if (getValue[1].equals("Circle")) {
                                        bpmnCircle.setId(getValue[1]);
                                        addCircle = true;

                                    } else if (getValue[1].equals("Rectangle")) {
                                        bpmnRectangle.setId(getValue[1]);
                                        addCircle = false;
                                    }
                                    break;
                                case "Color":
                                    if (getValue[1].equals("ALICEBLUE")) {
                                        bpmnCircle.setFill(Color.ALICEBLUE);
                                        bpmnRectangle.setFill(Color.ALICEBLUE);
                                    } else if (getValue[1].equals("YELLOW")) {
                                        bpmnCircle.setFill(Color.YELLOW);
                                        bpmnRectangle.setFill(Color.YELLOW);
                                    }
                                    break;
                                case "Stroke":
                                    bpmnCircle.setStrokeWidth(Double.parseDouble(getValue[1]));
                                    bpmnCircle.setRadius(bpmnCircle.getRadius()-(Double.parseDouble(getValue[1])/2));
                                    bpmnRectangle.setStrokeWidth(Double.parseDouble(getValue[1]));
                                    break;
                            }
                        }

                        if (addCircle) {
                            bpmnCircle.setName(name[1]);
                            classList.add(bpmnCircle);
                        } else {
                            bpmnRectangle.setName(name[1]);
                            classList.add(bpmnRectangle);
                        }
                    }

                    if (nodeParent.getNodeName().equals("owl:ObjectProperty")) {
                        String[] name = elementParent.getAttribute("rdf:about").split(Pattern.quote("#"));
                        BpmnRelation bpmnRelation = new BpmnRelation();
                        bpmnRelation.setName(name[1]);
                        for (String seg : segs) {
                            String[] getValue = seg.split(Pattern.quote(":"));
                            switch (getValue[0]) {
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

    public LinkedList<Shape> getClassList() {
        return classList;
    }

    public LinkedList<BpmnRelation> getRelationList() {
        return relationList;
    }

}

