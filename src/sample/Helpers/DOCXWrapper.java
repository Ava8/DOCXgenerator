package sample.Helpers;

import org.docx4j.jaxb.Context;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import sample.Models.DataModel;
import sample.Models.Student;

import java.io.File;
import java.math.BigInteger;
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
            td.getContent().add(getWordObject(s_list.get(i)));
            if (i == 0){
                for(int j = 1; j<cells.size(); j++){
                    td = (Tc) cells.get(j);
                    td.getContent().add(getWordObject(dataModel.visitingRegisterMonthDays.get(j-1)));
                }
            }
        }
        wordMLPackage.getMainDocumentPart().addParagraphOfText(dataModel.visitingRegisterSubject + ", " + dataModel.visitingRegisterMonth + "\n");
        wordMLPackage.getMainDocumentPart().addObject(tbl);
        firstPage = false;
    }

    //void to convert text to word object
    private P getWordObject(String text){
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        t.setValue(text);
        r.getContent().add(t);
        p.getContent().add(r);
        return p;
    }

    public void addTaskList(DataModel dataModel){
        List<String> s_list = new ArrayList<>();
        List<String> t_list = new ArrayList<>();

        s_list.add("null");
        t_list.add("null");

        for (Student s:dataModel.list) {
            s_list.add(s.getFio());
            if (s.getTask() != null){
                t_list.add(s.getTask());
            } else {
                t_list.add("-");
            }
        }

        addNewPage();

        int writableWidthTwips = wordMLPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
        int columnNumber = 4;
        Tbl tbl = TblFactory.createTable(s_list.size(), columnNumber, writableWidthTwips/columnNumber);
        List<Object> rows = tbl.getContent();

        for (int i = 0; i < rows.size(); i++){

            Tr tr = (Tr) rows.get(i);
            List<Object> cells = tr.getContent();

            Tc studentCell = (Tc) cells.get(0);
            Tc taskCell = (Tc) cells.get(1);
            Tc doneCell = (Tc) cells.get(2);
            Tc dateCell = (Tc) cells.get(3);

            if (i == 0){
                studentCell.getContent().add(getWordObject("ФИО"));
                taskCell.getContent().add(getWordObject("Задание"));
                doneCell.getContent().add(getWordObject("Выполнено"));
                dateCell.getContent().add(getWordObject("Дата"));
            } else {
                studentCell.getContent().add(getWordObject(s_list.get(i)));
                taskCell.getContent().add(getWordObject(t_list.get(i)));
            }
        }
        wordMLPackage.getMainDocumentPart().addObject(tbl);
        firstPage = false;
    }

    public void addCommentList(DataModel dataModel){
        List<String> s_list = new ArrayList<>();
        List<String> c_list = new ArrayList<>();

        s_list.add("null");
        c_list.add("null");

        for (Student s:dataModel.list) {
            s_list.add(s.getFio());
            if (s.getMasterComment() != null){
                c_list.add(s.getMasterComment());
            } else {
                c_list.add("-");
            }
        }

        addNewPage();

        int writableWidthTwips = wordMLPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
        int columnNumber = 2;
        Tbl tbl = TblFactory.createTable(s_list.size(), columnNumber, writableWidthTwips/columnNumber);
        List<Object> rows = tbl.getContent();

        for (int i = 0; i < rows.size(); i++){

            Tr tr = (Tr) rows.get(i);
            List<Object> cells = tr.getContent();

            Tc studentCell = (Tc) cells.get(0);
            Tc commentCell = (Tc) cells.get(1);

            if (i == 0){
                studentCell.getContent().add(getWordObject("ФИО"));
                commentCell.getContent().add(getWordObject("Коментарий"));
            } else {
                studentCell.getContent().add(getWordObject(s_list.get(i)));
                commentCell.getContent().add(getWordObject(c_list.get(i)));
            }
        }
        wordMLPackage.getMainDocumentPart().addObject(tbl);
        firstPage = false;
    }

    private void setCellWidth(Tc tableCell, int width) {
        TcPr tableCellProperties = new TcPr();
        TblWidth tableWidth = new TblWidth();
        tableWidth.setType("dxa");
        tableWidth.setW(BigInteger.valueOf(width));
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }

    public void saveDocument(String path) throws Exception{
        File exportFile = new File(path);
        wordMLPackage.save(exportFile);
    }
}
