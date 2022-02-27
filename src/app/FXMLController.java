/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import org.json.*;  
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.TreeWalker;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.xml.sax.InputSource;


/**
 * FXML Controller class
 *
 * @author sarra
 */
public class FXMLController implements Initializable {
    private String a="";
    private String gs="";
    private String currentDir="";
     
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //hiding the convert button
        convert.setVisible(false);
        tojson.setVisible(false);
        toxml.setVisible(false);
    }    
    
    public static HashMap<Object, Integer> sortByValue(HashMap<Object, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Object, Integer> > list =
               new LinkedList<Map.Entry<Object, Integer> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Object, Integer> >() {
            public int compare(Map.Entry<Object, Integer> o1,
                               Map.Entry<Object, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        // put data from sorted list to hashmap
        HashMap<Object, Integer> temp = new LinkedHashMap<Object, Integer>();
        for (Map.Entry<Object, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    
 
    @FXML
    private ListView plotConvert;
    
    @FXML
    private Button convert;
    
    @FXML
    private Button upload;

    @FXML
    private ListView listView;

    @FXML
    private MenuItem exit;
    
    @FXML
    private ListView fileName;
   
    @FXML
    private Button tojson;
    
    @FXML
    private Button toxml;

    public static Document xmlString_to_doc(String xml){
      //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try{
             //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            return doc;
        }catch(Exception ex){
            System.out.print(ex);
        }    
        return null;
    }
    
    @FXML
    void toXml(ActionEvent event) throws JSONException, IOException, TransformerException {
         ObjectMapper mapper = new ObjectMapper();

        String json=listView.getItems().get(0).toString();
        plotConvert.getItems().clear();
        String xml=json_to_xml(json);
        Document doc=xmlString_to_doc(xml);
        printXML(doc,plotConvert);

       
        
    }
    
    @FXML
    void tojson(ActionEvent event) throws JSONException, IOException {
        String xml=listView.getItems().get(0).toString();
        plotConvert.getItems().clear();
        plotConvert.getItems().add(xml_to_json(xml));
    }
    @FXML
    void exitProgram(ActionEvent event) {

    }
    @FXML
    void convertMe(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, JAXBException, JSONException {
        try{
         
        plotConvert.getItems().clear();
        String selectedFile = fileName.getItems().get(0).toString();
        if(selectedFile.endsWith("xml")|| selectedFile.endsWith("xl")){
            File file=new File(selectedFile);
            this.currentDir=file.getCanonicalPath();
            Document doc=CreateDomXML(file);
             String ch = createClass(doc);
                             createFiles(doc);
             plotConvert.getItems().add(ch);
            //xmlToObj_Test(file,doc,plotConvert);
            
        }else if(selectedFile.endsWith("json")){
            String json=listView.getItems().get(0).toString();
            String xml=json_to_xml(json);
            try{
              
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));

                Document doc = db.parse(is);

                //Librairie lib=getLib_from_json(xml);
                 plotConvert.getItems().add(createClass(doc));
                  

            }
            catch(Exception ex){
                Document doc = xmlString_to_doc(xml);
                DocumentTraversal traversal = (DocumentTraversal) doc;
                    TreeWalker walker = traversal.createTreeWalker(doc.getDocumentElement(),
                    NodeFilter.SHOW_ELEMENT, null, true);
                    traverseLevel(walker, "",plotConvert);
            }
            
            

        }
        }
        catch(Exception ex){
            
        }
    }
    
    public static void jsonConvert(String xml) throws JAXBException{
        try{
            Librairie lib=getLib_from_json(xml);
            }
        catch (Exception ex){
            
        }
    }
    public static Librairie getLib_from_json(String xml) throws JAXBException{
            JAXBContext jaxbContext = JAXBContext.newInstance(Librairie.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            Librairie lib = (Librairie) unmarshaller.unmarshal(reader);
        return lib;
    }
    public static Document CreateDomXML(File selectedFile) throws ParserConfigurationException, SAXException, IOException{
         //defining an API that provides a parser that produces DOM object trees from XML documents
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          //creation of a builder to parse the xml file
          DocumentBuilder db = dbf.newDocumentBuilder();
          return db.parse(selectedFile);
    }
    public static void refresh(ListView lv,ListView fn,ListView cv){
        lv.getItems().clear();
        fn.getItems().clear();
        cv.getItems().clear();
    } 
    public static void printXML(Document doc,ListView listView) throws TransformerException, JSONException{
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(doc), new StreamResult(out));
        listView.getItems().add(out.toString());
                
    } 
    
    public static String xml_to_json(String xml) throws JSONException, FileNotFoundException, IOException{
        JSONObject json = XML.toJSONObject(xml); // converts xml to json
         String jsonPrettyPrintString = json.toString(4); // json pretty print
         return jsonPrettyPrintString;
    }
    
    public static String json_to_xml(String json) throws JSONException{
            JSONObject jsonObject = new JSONObject(json);
            String xml=XML.toString(jsonObject);
            return xml;
    }
    
    private static final void traverseLevel(TreeWalker walker, String indent,ListView lv) {
        Node parend = walker.getCurrentNode();
        //lv.getItems().add(indent + "-" + ((Element) parend).getTagName());
        Element elem =(Element) parend;
        Integer longueur= elem.getChildNodes().getLength();
       if (longueur==1){
            lv.getItems().add(indent + "-" + elem.getTagName()+" : "+elem.getTextContent());

        }else{
            lv.getItems().add(indent + "-" + elem.getTagName());

        }
              
        for (Node n = walker.firstChild(); n != null; n = walker.nextSibling()) {
          traverseLevel(walker, indent + '\t',lv);
        }
        walker.setCurrentNode(parend);
      }
    
    
    public void compteur(TreeWalker walker,Integer i) {
       Node parend = walker.getCurrentNode();
        //lv.getItems().add(indent + "-" + ((Element) parend).getTagName());
        Element elem =(Element) parend;
      this.a+=Integer.toString(i)+"-"+elem.getTagName()+"\n";


        
              
        for (Node n = walker.firstChild(); n != null; n = walker.nextSibling()) {

          compteur(walker,i+1);
        }
        walker.setCurrentNode(parend);
       
      }
    
    public Boolean notcontainesOnce(String splitElem , List<String> listBal){
        int compt=0;
        for (String s:listBal){
            if (getNom(s).equals(splitElem)){
                compt++;
            }
            if (compt>1){
                return false;
            }
        }return true;
    }
    
    public String getSet(String t,String nom){
        String newNom="new"+capitalize(nom);
        return "public void set"+capitalize(nom)+"("+t+" "+newNom+"){\n\tthis."+nom+"="+newNom+";\n }\n"
                + "public "+t+" get"+capitalize(nom)+"(){\n\treturn this."+nom+";\n }\n";
    }
    
    public String countCurrency(List listBal , String elem,Document doc){
        String parentName=doc.getElementsByTagName(getNom(elem)).item(0).getParentNode().getNodeName();
         String newName= "new"+getNom(elem);
         if(Collections.frequency(listBal, elem)>1 && notcontainesOnce(parentName,listBal)){
         return "<list>";
         }
         else{
         return "";
         }
    }
    /**/
    
    public String attributeHasChilds(String elem,List<String> splited,Document doc,List l){
        if (doc.getElementsByTagName(splited.get(1)).item(0).getChildNodes().getLength()>1){
               this.gs+=getSet(capitalize(splited.get(1))+countCurrency(l,elem,doc),getNom(elem));

            return "\tprivate "+ capitalize(splited.get(1))+countCurrency(l,elem,doc)+" "+getNom(elem)+";\n";

        }
            this.gs+=getSet("String"+countCurrency(l,elem,doc),getNom(elem));

       return "\tprivate String"+countCurrency(l,elem,doc)+" "+splited.get(1)+";\n";
        
    }
    public String capitalize(String elem){
        return elem.substring(0,1).toUpperCase()+ elem.substring(1);
    }
    public List splited(String elem){
        return Arrays.asList(elem.split("-"));
    }
    public String getNum(String elem){
        return Arrays.asList(elem.split("-")).get(0);
    }
    public String getNom(String elem){
        return Arrays.asList(elem.split("-")).get(1);
    }

    public String createClass(Document doc){
        try{
     
        List exist = new ArrayList();
        List attExist=new ArrayList();
        String pubClass="public class ";
        String ch="";
        DocumentTraversal traversal = (DocumentTraversal) doc;
        TreeWalker walker = traversal.createTreeWalker(doc.getDocumentElement(),NodeFilter.SHOW_ELEMENT, null, true);
        this.a="";
        compteur(walker,1);

       List<String> listBal = Arrays.asList(this.a.split("\n"));
       
       for(String elem:listBal){
           this.gs="";
           if(!exist.contains(elem)){
               Node docItem = doc.getElementsByTagName(getNom(elem)).item(0);
               if(docItem.getChildNodes().getLength()>1){
                   ch+=pubClass+capitalize(getNom(elem))+"{\n";
                   NodeList childrens=docItem.getChildNodes();
                   for(int j=0;j<childrens.getLength();j++){
                       String nodeName=childrens.item(j).getNodeName();
                      if (!nodeName.equals("#text") && !attExist.contains(nodeName)){
                          
                          String split=Integer.toString(Integer.parseInt(getNum(elem))+1)+"-"+nodeName;
                           ch+=attributeHasChilds(split,splited(split),doc,listBal);
                           attExist.add(nodeName);
                      }
                   }
                  ch+="\n\n"+this.gs+"}\n\n\n\n";
                  exist.add(elem);
               }
           }
       }

        return ch;
        }
        catch(Exception ex){
            System.out.print("Error printing");
                    return null;

        }

    }
    
    
    public void createFiles(Document doc) throws IOException{
        List<String> classes=Arrays.asList(createClass(doc).split("\n\n\n\n"));
        String pckgName="package "+this.getClass().getCanonicalName().split("\\.")[0]+";";
        for(String elem:classes){
            String nameClass=elem.split("\n")[0].split("public class ")[1].replace("{","");
            String newFileCnt=pckgName+"\n"+elem;
            
            String Path=Paths.get(".").normalize().toAbsolutePath()+"/src/"+this.getClass().getCanonicalName().split("\\.")[0];
            try{
                                System.out.print(Path);

                Files.writeString(Paths.get(Path),newFileCnt);

            } catch(Exception ex){
                System.out.print("ok");
            }
        }
    }

    

   //convert xml test file to java class
    public static void xmlToObj_Test(File xml,Document doc,ListView listView) throws JAXBException, JSONException{
        try {
            // create an instance of `JAXBContext`
            JAXBContext context = JAXBContext.newInstance(Librairie.class);
            // create an instance of `Unmarshaller`
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // convert XML file to `Book` object
            Librairie lib = (Librairie) unmarshaller.unmarshal(xml);
            // adding the libary string to the list views
            listView.getItems().add(lib.toString());
             DocumentTraversal traversal = (DocumentTraversal) doc;
                    TreeWalker walker = traversal.createTreeWalker(doc.getDocumentElement(),
                    NodeFilter.SHOW_ELEMENT, null, true);

    
            
                    
        } catch (JAXBException ex) {
                    DocumentTraversal traversal = (DocumentTraversal) doc;
                    TreeWalker walker = traversal.createTreeWalker(doc.getDocumentElement(),
                    NodeFilter.SHOW_ELEMENT, null, true);
                    traverseLevel(walker, "",listView);
}
    }
    
    public static void json_Prettifier(String json,ListView listView){
        try{
            ObjectMapper mapper = new ObjectMapper();
            listView.getItems().add(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(json)));
            
                  
        }
        catch(Exception ex){
            System.out.print(ex.toString());
         }
    }
    
    @FXML
    void uploadFile(ActionEvent event) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, Exception {
        //refreshing and removing everithing in the list views
        refresh(listView,fileName,plotConvert);
        //removing the to xml and to json buttons
         tojson.setVisible(false);
         toxml.setVisible(false);
        //choosing a file
        FileChooser fc = new FileChooser();
        File selectedFile= fc.showOpenDialog(null);
        if (selectedFile!= null){
            //***Getting file type***
            //getting absolute path
            String selectedFilePath=selectedFile.getAbsolutePath();
            //Adding the path of the file to the gui
            fileName.getItems().add(selectedFilePath);
            //splitig the file path name (symbol=="/")
            String[] filePathList=selectedFilePath.split("\\.");
            
                
            try{
                //getting the extension
                String extension=filePathList[filePathList.length-1];
                //scanning the file
                Scanner fileContent = new Scanner(selectedFile);

                if(filePathList.length==1 || (!extension.equals("json") && !extension.contains("xml") && !extension.startsWith("xl"))){
                    //hiding the convert button
                    convert.setVisible(false);  
                    while(fileContent.hasNextLine()){
                        //ploting each line of the file on the gui
                        listView.getItems().add(fileContent.nextLine());
                    }
                }else 
                 {  
                    //showing the convert button
                    convert.setVisible(true);
                    
                    
                    if (extension.contains("xml")){
                    //showing the to json button
                    tojson.setVisible(true);
                    
                    //creating the dom xml
                    Document doc=CreateDomXML(selectedFile);
                    //printing the xml file in the list view
                    printXML(doc,listView);
               
                    }
                   
                    if(extension.equals("json")){
                     tojson.setVisible(false);
                     toxml.setVisible(true);
                    //converting the file content to string
                    String json = Files.readString(Paths.get(selectedFilePath));       
                     //ploting the file content in the list view
                     json_Prettifier(json,listView);
                    
                    }
                 }
                
                fileContent.close();
            }
            catch (FileNotFoundException e) {
                e.toString();
            }
        } else{
            System.out.println("Error");
        }
    }
    
}
