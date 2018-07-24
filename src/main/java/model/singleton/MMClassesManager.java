package model.singleton;

import model.modelobjects.Shape.OnbacomoShape;
import org.semanticweb.owlapi.model.OWLIndividual;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class MMClassesManager {
    private static MMClassesManager instance;
    private static LinkedHashSet<OWLIndividual> classIndividuals;
    private static LinkedList<OnbacomoShape> startClassesList;
    private static LinkedList<OnbacomoShape> endClassesList;
    private static LinkedList<String> startClassTypeList;
    private static LinkedList<String> endClassTypeList;

    public synchronized static MMClassesManager getInstance() {
        if (instance == null) {
            instance = new MMClassesManager();
        }
        return instance;
    }

    public LinkedHashSet<OWLIndividual> getClassIndividuals() {
        return classIndividuals;
    }
    public void setClassIndividuals(LinkedHashSet<OWLIndividual> classIndividuals) {
        MMClassesManager.classIndividuals = classIndividuals;
    }

    public LinkedList<OnbacomoShape> getStartClassesList(){
        if (startClassesList == null){
            startClassesList = new LinkedList<>();
        }
        return startClassesList;
    }
    public LinkedList<OnbacomoShape> getEndClassesList(){
        if (endClassesList == null){
            endClassesList = new LinkedList<>();
        }
        return endClassesList;
    }
    public static LinkedList<String> getStartClassTypeList() {
        if (startClassTypeList == null){
            startClassTypeList = new LinkedList<>();
        }
        return startClassTypeList;
    }
    public static LinkedList<String> getEndClassTypeList() {
        if (endClassTypeList == null){
            endClassTypeList = new LinkedList<>();
        }
        return endClassTypeList;
    }

    public void addToStartClassesList(OnbacomoShape onbacomoClass) {
        if (startClassesList == null){
            startClassesList = new LinkedList<>();
            MMClassesManager.startClassesList.add(onbacomoClass);
        }else {
            MMClassesManager.startClassesList.add(onbacomoClass);
        }
    }
    public void addToEndClassesList(OnbacomoShape onbacomoClass) {
        if (endClassesList == null){
            endClassesList = new LinkedList<>();
            MMClassesManager.endClassesList.add(onbacomoClass);
        }else {
            MMClassesManager.endClassesList.add(onbacomoClass);
        }
    }
    public void addToEndClassTypeList(String type) {
        if (endClassTypeList == null){
            endClassTypeList = new LinkedList<>();
            MMClassesManager.endClassTypeList.add(type);
        }else {
            MMClassesManager.endClassTypeList.add(type);
        }
    }
    public void addToStartClassTypeList(String type) {
        if (startClassTypeList == null){
            startClassTypeList = new LinkedList<>();
            MMClassesManager.startClassTypeList.add(type);
        }else {
            MMClassesManager.startClassTypeList.add(type);
        }
    }

}
