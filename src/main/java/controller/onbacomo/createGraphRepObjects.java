package controller.onbacomo;

import model.onbacomo.objects.classGraphRep;
import model.onbacomo.objects.relationClassGraphRep;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.regex.Pattern;

public class createGraphRepObjects {
    private classGraphRep[] classList;
    private relationClassGraphRep[] relationClassList;
    private String[] startClasses;
    private String[] endClasses;

    public void createObjects(File fXMLFile) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXMLFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("onbacomo:graphrep");
            classList = new classGraphRep[nList.getLength()];
            relationClassList = new relationClassGraphRep[nList.getLength()];

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                classGraphRep cgr = new classGraphRep();
                relationClassGraphRep rcgr = new relationClassGraphRep();

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String[] segs = eElement.getTextContent().split(Pattern.quote(";"));
                    Node parent = eElement.getParentNode();
                    Element pa = (Element) parent;

                    if (parent.getNodeName().equals("owl:Class")) {
                        String[] getName = pa.getAttribute("rdf:about").split(Pattern.quote("#"));
                        String name = getName[1];

                        for (String seg : segs) {
                            cgr.name = name;
                            String[] getValue = seg.split(Pattern.quote(":"));

                            if (getValue[0].equals("Shape")) {
                                cgr.shape = getValue[1];
                            }

                            if (getValue[0].equals("Color")) {
                                cgr.color = getValue[1];
                            }

                            if (getValue[0].equals("Stroke")) {
                                cgr.strokeWidth = Double.parseDouble(getValue[1]);
                            }
                        }
                    }

                    if (parent.getNodeName().equals("owl:ObjectProperty")) {
                        String[] getName = pa.getAttribute("rdf:about").split(Pattern.quote("#"));
                        String name = getName[1];

                        for (String seg : segs) {
                            rcgr.name = name;
                            String[] getValue = seg.split(Pattern.quote(":"));
                            if (getValue[0].equals("Shape")) {
                                rcgr.shape = getValue[1];
                            }

                            if (getValue[0].equals("Color")) {
                                rcgr.color = getValue[1];

                            }

                            if (getValue[0].equals("Direction")) {
                                rcgr.direction = getValue[1];
                            }

                            if (getValue[0].equals("StartClass")) {
                                startClasses = getValue[1].split(Pattern.quote(","));//Startklassen
                            }

                            if (getValue[0].equals("EndClass")) {
                                endClasses = getValue[1].split(Pattern.quote(","));//Endklassen
                            }
                        }
                    }
                }
                classList[temp] = cgr;
                relationClassList[temp] = rcgr;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public classGraphRep[] getClassList() {
        return classList;
    }

    public relationClassGraphRep[] getRelationClassList() {
        return relationClassList;
    }

    public String[] getStartClasses() {
        return startClasses;
    }

    public String[] getEndClasses() {
        return endClasses;
    }
}
