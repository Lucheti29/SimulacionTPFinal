package com.github.luksdlt92.simulacion.parser;

import com.github.luksdlt92.simulacion.constant.Seniority;
import com.github.luksdlt92.simulacion.constant.Technology;
import com.github.luksdlt92.simulacion.model.instance.SimulationInstance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class StageParser {

    private final DocumentBuilder mBuilder;

    public static StageParser newInstance() throws ParserConfigurationException {
        return new StageParser();
    }

    private StageParser() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        mBuilder = factory.newDocumentBuilder();
    }

    public SimulationInstance.Builder parseFile(File file) {
        try {
            Document document = mBuilder.parse(file);
            document.getDocumentElement().normalize();
            Element node = document.getDocumentElement();

            SimulationInstance.Builder simulationBuilder = SimulationInstance.Builder.newInstance();

            String qaPeopleText = node.getElementsByTagName("qapeople").item(0).getTextContent();
            simulationBuilder.setQAPeopleAmount(Integer.valueOf(qaPeopleText));
            
            String stageIdText = node.getElementsByTagName("id").item(0).getTextContent();
            simulationBuilder.setStageId(Integer.valueOf(stageIdText));

            NodeList technologies = node.getElementsByTagName("technology");

            for (int i = 0; i < technologies.getLength(); i++) {
                Element element = (Element) technologies.item(i);

                String technologyName = element.getAttributes().item(0).getNodeValue();
                int technologyId = Technology.getValue(technologyName);

                simulationBuilder.setProjectsAmount(technologyId, Integer.valueOf(element.getElementsByTagName("projects").item(0).getTextContent()));

                NodeList seniorities = element.getElementsByTagName("seniority");

                for (int i2 = 0; i2 < seniorities.getLength(); i2++) {
                    Element element2 = (Element) seniorities.item(i2);

                    String juniorAmount = element2.getElementsByTagName("junior").item(0).getTextContent();
                    String semiseniorAmount = element2.getElementsByTagName("semisenior").item(0).getTextContent();
                    String seniorAmount = element2.getElementsByTagName("senior").item(0).getTextContent();

                    simulationBuilder.setSeniorityAmount(technologyId, Seniority.JUNIOR, Integer.valueOf(juniorAmount));
                    simulationBuilder.setSeniorityAmount(technologyId, Seniority.SEMISENIOR, Integer.valueOf(semiseniorAmount));
                    simulationBuilder.setSeniorityAmount(technologyId, Seniority.SENIOR, Integer.valueOf(seniorAmount));
                }
            }

            return simulationBuilder;
        } catch (Exception e) {
            System.out.println(StageParser.class.getSimpleName() + e.getMessage());
        }

        return null;
    }
}
