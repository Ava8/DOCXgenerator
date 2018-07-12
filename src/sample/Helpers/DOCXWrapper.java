package sample.Helpers;

import org.docx4j.jaxb.Context;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import sample.Models.DataModel;
import sample.Models.Student;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DOCXWrapper {
    private WordprocessingMLPackage wordMLPackage;
    private ObjectFactory factory = Context.getWmlObjectFactory();
    private boolean firstPage = true;

    public DOCXWrapper() throws Exception{
        wordMLPackage = WordprocessingMLPackage.createPackage();
    }

    //method to add page break if this is not the first page
    private void addNewPage(){
        if (!firstPage){
            P p = factory.createP();
            R r = factory.createR();
            Br br = factory.createBr();
            br.setType(org.docx4j.wml.STBrType.PAGE);
            r.getContent().add(br);
            p.getContent().add(r);
            wordMLPackage.getMainDocumentPart().addObject(p);
        }
    }

    //void to add list of students to document
    public void addStudentList(DataModel dataModel){
        addNewPage();
        for (Student s : dataModel.list) {
            wordMLPackage.getMainDocumentPart().addParagraphOfText("ФИО: " + s.getFio() + " группа: " + s.getGroup() + "\n");
            firstPage = false;
        }
    }

    //void to add attend table to document
    public void addAttendList(DataModel dataModel){

        List<String> s_list = new ArrayList<>();

        s_list.add("ФИО/дата");
        for (Student s:dataModel.list) {
            s_list.add(s.getFio());
        }

        addNewPage();

        int writableWidthTwips = wordMLPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
        int columnNumber = dataModel.visitingRegisterMonthDays.size() + 1;
        Tbl tbl = TblFactory.createTable(s_list.size(), columnNumber, writableWidthTwips/columnNumber);
        List<Object> rows = tbl.getContent();

        for (int i = 0; i < rows.size(); i++){

            Tr tr = (Tr) rows.get(i);
            List<Object> cells = tr.getContent();
            Tc td = (Tc) cells.get(0);
            td.getContent().add(getWordObject(s_list.get(i), factory));
            if (i == 0){
                for(int j = 1; j<cells.size(); j++){
                    td = (Tc) cells.get(j);
                    td.getContent().add(getWordObject(dataModel.visitingRegisterMonthDays.get(j-1), factory));
                }
            }
        }
        wordMLPackage.getMainDocumentPart().addParagraphOfText(dataModel.visitingRegisterSubject + ", " + dataModel.visitingRegisterMonth + "\n");
        wordMLPackage.getMainDocumentPart().addObject(tbl);
        firstPage = false;
    }

    //void to convert text to word object
    private P getWordObject(String text, ObjectFactory factory){
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        t.setValue(text);
        r.getContent().add(t);
        p.getContent().add(r);
        return p;
    }

    public void addTaskList(DataModel dataModel){

    }

    public void addCommentList(DataModel dataModel){

    }

    public void saveDocument(String path) throws Exception{
        File exportFile = new File(System.getProperty("user.home") + "/test.doc");
        wordMLPackage.save(exportFile);
    }
}
